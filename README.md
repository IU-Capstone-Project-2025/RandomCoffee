# RandomCoffee

Telegram Mini App that fosters meaningful connections through casual meetups. Users register, set preferences, and select tags—suggested by a machine learning algorithm—based on their interests or professional background. Each week, the system matches users with others who share similar tags and encourages them to meet.

## Local deploy

To deploy from local repository, do the following:

- Clone the repository
- Enter project root folder
- Rename ```backend/bot-service/src/main/resources/ex-application-secrets.yaml``` to ```application-secrets.yaml``` and change all variables to real ones
- Execute ```docker compose -f compose-dev.yaml up -d --build```

## Remote deploy

To deploy from just ```compose.yaml``` without cloning repository, do the following:

- Download ```compose.yaml```
- Execute ```docker compose up -d```
