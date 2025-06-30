from sentence_transformers import SentenceTransformer
import numpy as np
from typing import List, Dict, Tuple, Optional, DefaultDict
import random
from datetime import datetime, timedelta
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
from sklearn.cluster import KMeans
from math import exp
from copy import deepcopy
from collections import defaultdict
from sklearn.metrics import pairwise_distances
import re
import nltk
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from nltk.tokenize import word_tokenize
nltk.download('punkt')
nltk.download('stopwords')
nltk.download('punkt_tab')


# Preprocess bio
stemmer = PorterStemmer()
stop_words = set(stopwords.words('english'))

def preprocess_text(text: str, stem_words: bool = True, remove_stopwords: bool = True) -> str:
    
    text = text.lower()
    text = re.sub(r'\n+', ' ', text)
    text = re.sub(r'[^\w\s]', '', text)  
    text = re.sub(r'\d+', '', text)  
    text = re.sub(r'\s+', ' ', text).strip() 
    
    words = word_tokenize(text)
    
    if remove_stopwords:
        words = [word for word in words if word not in stop_words]
    
    if stem_words:
        words = [stemmer.stem(word) for word in words]
    
    return ' '.join(words)


# Calculate tag similarities
def jaccard_distance(tags1: List[str], tags2: List[str]) -> float:
    set1, set2 = set(tags1), set(tags2)
    
    intersection = len(set1 & set2)
    union = len(set1 | set2)

    return 1 - intersection / union if union != 0 else 1.0

def semantic_tag_distance(tags1: List[str], tags2: List[str], embedder: SentenceTransformer) -> float:
    text1 = " ".join(tags1)
    text2 = " ".join(tags2)

    emb1 = embedder.encode(text1)
    emb2 = embedder.encode(text2)
    if hasattr(emb1, "cpu"):
        emb1 = emb1.cpu().numpy()
    if hasattr(emb2, "cpu"):
        emb2 = emb2.cpu().numpy()
    
    return 1 - cosine_similarity(emb1.reshape(1, -1), emb2.reshape(1, -1))[0][0]



# The main algorithm
class GreedyMatcher:
    def __init__(
        self,
        coef_a: float = 0.6,
        coef_b: float = 0.4,
        penalty_multiplier: float = 0.85,
        tag_distance_func: str = "jaccard",
        embedding_model: str = "all-MiniLM-L6-v2"
    ):
        self.coef_a = coef_a
        self.coef_b = coef_b
        self.penalty = penalty_multiplier
        self.tag_distance = {
            "jaccard": jaccard_distance,
            "semantic": lambda t1, t2: semantic_tag_distance(t1, t2, self.embedder)
        }.get(tag_distance_func, jaccard_distance)

        self.embedder = SentenceTransformer(embedding_model)

    def get_weeks_since_last_meeting(self, user_id1: str, user_id2: str, history: List[Dict]) -> float:
        for event in history:
            if {event["user_id1"], event["user_id2"]} == {user_id1, user_id2}:
                last_meeting = datetime.strptime(event["timestamp"], "%Y-%m-%d")
                return (datetime.now() - last_meeting).days / 7
        return float("inf") 

    def compute_pair_score(self, user1: Dict, user2: Dict, history: List[Dict]) -> float:
        bio_sim = cosine_similarity([user1["bio_embedding"]], [user2["bio_embedding"]])[0][0]
        
        tag_dist = self.tag_distance(user1["tags"], user2["tags"])
        
        base_score = self.coef_a * bio_sim + self.coef_b * (1 - tag_dist)
        
        weeks_passed = self.get_weeks_since_last_meeting(user1["user_id"], user2["user_id"],history)
        penalty = self.penalty ** weeks_passed if weeks_passed != float("inf") else 0

        return base_score * (1 - penalty)

    def match(self, users_data: List[Dict], history: List[Dict] = None) -> List[Tuple[str, Optional[str]]]:
        if history is None:
            history = []
        
        for user in users_data:
            embedding = self.embedder.encode(user["bio"])
            if hasattr(embedding, "cpu"):
                embedding = embedding.cpu().numpy()
            user["bio_embedding"] = embedding
        
        # list of all pairs of users with their scores
        pairs = []
        n = len(users_data)
        for i in range(n):
            for j in range(i + 1, n):
                user1 = users_data[i]
                user2 = users_data[j]
                score = self.compute_pair_score(user1, user2, history)
                pairs.append((score, i, j))
        
        pairs.sort(reverse=True, key=lambda x: x[0])

        # greedy matching
        matched_pairs = []
        total_score = 0.0
        pair_count = 0
        used_indices = set()
        
        for score, i, j in pairs:
            if i not in used_indices and j not in used_indices:
                matched_pairs.append((users_data[i]["user_id"], users_data[j]["user_id"]))
                total_score += score
                pair_count += 1
                used_indices.update([i, j])
                if len(used_indices) == n:
                    break
        
        all_indices = set(range(n))
        leftover_indices = all_indices - used_indices
        for idx in leftover_indices:
            matched_pairs.append((users_data[idx]["user_id"], None)) # "Sori brat" case
        
        print(f"Average score: {total_score / pair_count if pair_count > 0 else 0.0}")
        return matched_pairs