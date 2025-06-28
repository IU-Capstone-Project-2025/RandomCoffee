from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
from datetime import datetime
from match_making.main import GreedyMatcher

app = FastAPI()

# Request/Response models based on ml.yaml
class MatchHistoryDto(BaseModel):
    peerId1: int
    peerId2: int
    timestamp: Optional[datetime] = None

class ProfileRequest(BaseModel):
    peerId: int
    bio: str
    tags: Optional[List[str]] = None

class MatchmakingRequest(BaseModel):
    usersData: List[ProfileRequest]
    history: Optional[List[MatchHistoryDto]] = None

class ErrorResponse(BaseModel):
    timestamp: datetime
    status: int
    error: str
    path: str

matcher = GreedyMatcher()

@app.get("/matchmaking", response_model=List[MatchHistoryDto], status_code=201)
def matchmaking(request: MatchmakingRequest):
    """
    Matchmaking endpoint that creates matches between users based on their profiles and history.
    """
    try:
        # Convert pydantic models to dicts for matcher
        users_data = []
        for user in request.usersData:
            user_dict = {
                "user_id": user.peerId,
                "bio": user.bio,
                "tags": user.tags or []
            }
            users_data.append(user_dict)
        
        history = []
        if request.history:
            for event in request.history:
                history_dict = {
                    "user_id1": event.peerId1,
                    "user_id2": event.peerId2,
                    "timestamp": event.timestamp.strftime("%Y-%m-%dT%H:%M:%S.%f%z")
                }
                history.append(history_dict)
        
        # Get matches from matcher
        pairs = matcher.match(users_data, history)
        
        # Convert pairs to MatchHistoryDto format
        matches = []
        for pair in pairs:
            if pair[1] is not None:  # Only add valid pairs
                match = MatchHistoryDto(
                    peerId1=int(pair[0]),
                    peerId2=int(pair[1])
                )
                matches.append(match)

        return matches
        
    except ValueError as e:
        # Bad request - 400
        error_response = ErrorResponse(
            timestamp=datetime.now(),
            status=400,
            error=str(e),
            path="/matchmaking"
        )
        raise HTTPException(status_code=400, detail=error_response.model_dump())
    except Exception as e:
        # Unexpected error - 500
        error_response = ErrorResponse(
            timestamp=datetime.now(),
            status=500,
            error=str(e),
            path="/matchmaking"
        )
        raise HTTPException(status_code=500, detail=error_response.model_dump())

@app.get("/")
def read_root():
    return {"status": "healthy"}
