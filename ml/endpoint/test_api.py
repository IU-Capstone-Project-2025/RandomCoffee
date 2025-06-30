#!/usr/bin/env python3
"""
Test script for the updated ML Matching API endpoints
"""

import requests
import json
from datetime import datetime

# API base URL
BASE_URL = "http://localhost:8000"

def test_health_check():
    """Test the health check endpoint"""
    print("🏥 Testing health check...")
    response = requests.get(f"{BASE_URL}/")
    print(f"Status: {response.status_code}")
    print(f"Response: {response.json()}")
    print()

def test_matchmaking_endpoint():
    """Test the /matchmaking endpoint with GET method"""
    print("🎯 Testing /matchmaking endpoint...")
    
    # Create request payload matching the updated ml.yaml
    payload = {
        "usersData": [
            {
                "peerId": 1,
                "bio": "I love hiking and reading.",
                "tags": ["hiking", "reading", "nature"]
            },
            {
                "peerId": 2,
                "bio": "Enjoys painting and music.",
                "tags": ["painting", "music", "art"]
            },
            {
                "peerId": 3,
                "bio": "Avid runner and foodie.",
                "tags": ["running", "food", "travel"]
            }
        ]
    }
    
    # Use GET method with query parameters or request body
    # Note: GET with request body is unusual but matches your spec
    response = requests.get(f"{BASE_URL}/matchmaking", json=payload)
    print(f"Status: {response.status_code}")
    print(f"Response: {response.json()}")
    print()

def test_error_handling():
    """Test error handling with invalid data"""
    print("⚠️ Testing error handling...")
    
    # Test with invalid data (missing required fields)
    invalid_payload = {
        "usersData": [
            {
                "peerId": 1,
                "bio": "I love hiking and reading."
                # Missing tags field
            }
        ],
        "history": []
    }
    
    response = requests.get(f"{BASE_URL}/matchmaking", json=invalid_payload)
    print(f"Status: {response.status_code}")
    print(f"Response: {response.json()}")
    print()

def main():
    """Run all tests"""
    print("🧪 Starting API tests...\n")
    
    try:
        test_health_check()
        test_matchmaking_endpoint()
        test_error_handling()
        
        print("✅ All tests completed!")
        
    except requests.exceptions.ConnectionError:
        raise ConnectionError("Could not connect to the API. Make sure the server is running on http://localhost:8000")
    except Exception as e:
        raise Exception(f"❌ Test failed with error: {e}")

if __name__ == "__main__":
    main() 