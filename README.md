# Firefly

![Firefly](img.png)

[![Build Status](https://travis-ci.org/Desertive/firefly.png?branch=master)](https://travis-ci.org/Desertive/firefly)

Firefly is a Java-based light engine for LED strips. It's mainly designed to work with Valo system, which is author's hobby project.

The idea of the engine is to decouple web server, calculation engine, timer engine and data emitters to clear and manageable totality.

# Quickstart

Run application `./gradlew bootRun`

> You can test the connection with any socket.io tester. Connect to localhost:8080 and send a message with event name HEALTH_CHECK. After that you should be able to see **Up and running** message in the application console.

Run unit tests `./gradlew test`

Build executable JAR `./gradlew build`

# Socket.io events
After you have initalized connection to socket.io server, you can call it with events below.

## HEALTH_CHECK
Health check event is purely for testing and debugging purposes.

## ACTION
The actual event for executing the flow. Action body can have following actions:

    {
        "ledStripSections": [
            {
                "start": 0, // Starting index of led strip
                "end": 3, // Ending index of led strip
                "type": "STATIC", // Action type (STATIC, BLINK)
                "properties": { // Properties depending of action types
                    "r": 255,
                    "g": 255,
                    "b": 255
                }
            },
            {
                "start": 4,
                "end": 7,
                "type": "BLINK",
                "properties": {
                    "r": 255,
                    "g": 255,
                    "b": 255,
                    "interval": 60 // Value as frames
                }
            }
        ]
    }

You can have as many sections as you like. One section might have blue static light, other could have some smooth white blinking etc.

# Progress
:white_check_mark: Socket.io server

:white_check_mark: Event listeners

:white_check_mark: Request validation

:white_check_mark: Initalization of core structure

:white_check_mark: Calculation flow for STATIC action type

:white_check_mark: Calculation flow for BLINK action type

:black_square_button: Timer engine

:black_square_button: Response stream for receivers

# Kudos
- Image design by [Flaticon](https://www.flaticon.com/)
- My wife for keeping children entertained
