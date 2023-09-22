# Kalaha

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running Locally](#running-locally)
- [Creating a New Game](#creating-a-new-game)
- [Viewing Game Information](#viewing-game-information)
    - [Viewing a List of Games by ID](#viewing-a-list-of-games-by-id)
    - [Viewing a List of All Games](#viewing-a-list-of-all-games)
- [Making a Move](#making-a-move)
- [Error Handling](#error-handling)
- [Running Tests](#running-tests)

# [Introduction](#introduction)
Welcome to Kalaha, a web application for playing the Kalaha game. This README provides an overview of how to use the API to create and manage games, make moves, and handle errors.

## [Getting Started](#getting-started)

### **Prerequisites**

Before you begin, ensure you have the following prerequisites:

__Docker__ (for running a local MongoDB instance)
Gradle (for running the application)
curl or a REST API client of your choice for making HTTP requests

### [Installation](#installation)

1.Clone the repository to your local machine:


```bash
git clone https://github.com/thais/kalaha.git
cd kalaha
````

2.Run a local MongoDB instance using Docker:

```bash
docker-compose up -d mongo
```

3. Build the application:
```bash
./gradlew build
```

### [Running Locally](#running-locally)
To run the application locally, use the following command:
```bash
./gradlew bootRun
```
The application will be accessible at http://localhost:8080.


### [Creating a New Game](#creating-a-new-game)

To create a new game, make a POST request with player information as follows:

```bash
curl --location 'localhost:8080/games' \
--header 'Content-Type: application/json' \
--data '{"players": [{"id": 1010},{"id": 2020}]}'
```

In the response, you will receive the game's ID, status, creation date, player information, board layout, and the current player's turn:


```json
{
  "id": 1695348781675,
  "status": "CREATED",
  "createdAt": "2023-09-21T23:13:01.675460477",
  "players": [
    {
      "id": 1234,
      "name": "name"
    },
    {
      "id": 5678,
      "name": "name"
    }
  ],
  "board": " [6][6][6][6][6][6]\n(0)                  (0)\n   [6][6][6][6][6][6]",
  "turn": 1234
}
```
## [Viewing Game Information](#viewing-game-information)

### [Viewing a List of Games by ID](#viewing-a-list-of-games-by-id)
To view a specific game by its ID, use the following command:

```bash
curl --location 'localhost:8080/games/{game_id}'
```
Replace `{game_id}` with the actual game ID you want to retrieve.


### [Viewing a List of All Games](#viewing-a-list-of-all-games)
To view a list of all games, use the following command:

```bash
curl --location 'localhost:8080/games'
```

## [Making a Move](#making-a-move)
To make a move in the game, send a POST request with player and pit information:

```bash
curl --location 'localhost:8080/games/1695349175264/move' \
--header 'Content-Type: application/json' \
--data '{
    "player": "1010",
    "pit": 1
}
```

The response will include the updated game status, including the board layout and the next player's turn.

```json
{
    "id": 1695349175264,
    "status": "CREATED",
    "createdAt": "2023-09-21T23:19:35.264",
    "players": [
        {
            "id": 1010,
            "name": "name"
        },
        {
            "id": 2020,
            "name": "name"
        }
    ],
    "board": {
        "pits": [
            {
                "seeds": 0
            },
            {
                "seeds": 0
            },
            {
                "seeds": 7
            },
            {
                "seeds": 7
            },
            {
                "seeds": 7
            },
            {
                "seeds": 7
            },
            {
                "seeds": 7
            },
            {
                "seeds": 1
            },
            {
                "seeds": 6
            },
            {
                "seeds": 6
            },
            {
                "seeds": 6
            },
            {
                "seeds": 6
            },
            {
                "seeds": 6
            },
            {
                "seeds": 6
            }
        ]
    },
    "turn": 2020
}
```


## [Error Handling](#error-handling)

If there is an error, such as a duplicate move or an invalid request, you will receive an error response with a descriptive message and an error code. For example:

```bash
curl --location 'localhost:8080/games/1695349175264/move' \
--header 'Content-Type: application/json' \
--data '{
    "player": "1010",
    "pit": 1
    
}'
```

```json
    {
      "error": {
        "message": "Duplicate Move and It's not your turn",
        "code": 400
      }
    }
```

## [Running Tests](#running-tests)

To run tests for the application, use the following command:


```./gradlew test```