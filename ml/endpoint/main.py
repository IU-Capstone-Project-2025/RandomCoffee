from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Dict, Optional, Tuple
from ml.match_making.main import GreedyMatcher 


app = FastAPI()

# Define request/response schemas
class User(BaseModel):
    user_id: str
    bio: str
    tags: List[str]

class HistoryEvent(BaseModel):
    user_id1: str
    user_id2: str
    timestamp: str 

class MatchRequest(BaseModel):
    users_data: List[User]
    history: Optional[List[HistoryEvent]] = []

class MatchResult(BaseModel):
    pairs: List[Tuple[str, Optional[str]]]

matcher = GreedyMatcher()

@app.post("/match", response_model=MatchResult)
def match_users(request: MatchRequest):
    # Convert pydantic models to dicts for matcher
    users_data = [user.model_dump() for user in request.users_data]
    history = [event.model_dump() for event in request.history]
    try:
        pairs = matcher.match(users_data, history)
        return {"pairs": pairs}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.get("/")
def read_root():
    return {"status": "healthy"}
