# RandomCoffee

Telegram Mini App that fosters meaningful connections through casual meetups. Users register, set preferences, and select tags—suggested by a machine learning algorithm—based on their interests or professional background. Each week, the system matches users with others who share similar tags and encourages them to meet.

## Remote deploy

To deploy from just ```compose.yaml``` without cloning repository, do the following:

- Download ```compose.yaml```
- Add ```.env``` to the same folder
- Execute ```docker compose up -d```

## Local deploy

To deploy from local repository, do the following:

- Clone the repository
- Enter project root folder
- Add ```.env``` to the root folder
- Execute ```docker compose -f compose-dev.yaml up -d --build```

## Opening Mini App

To open mini app locally from your Telegram app, you need to login into the Telegram Test Environment, because clients in production environment don't support mini apps deployed without SSL certificate.

The guide:

- Google how to login into Telegram Test Environment on your platform. For example:
    - For iOS and macOS you need to tap Settings 10 times -> Tap on "Accounts" -> "Login to another account" -> "Test"
    - For Windows you need to go to the Settings -> Right click with Shift+Alt on Add Account -> Select Test Server.
- If you want to open mini app from computer, do following with running docker container:
    - Open @random_coffee_test_bot
    - Tap on "Open App" button in bot's profile
- If you want to open mini app from mobile, do following (with running docker container):
    - Open @BotFather and create a new bot
    - Get your computer's IP address in local network
    - Enter bot edit mode, tap on "Bot Settings" -> "Configure Mini App" -> "Enable Mini App" and paste your computer's IP address in format ```http://<IP_ADDRESS>:3000```
    - Open your bot and tap on "Open App" button in bot's profile