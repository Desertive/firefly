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
After you have initialized connection to the socket.io server, you can call it with the events below.

## HEALTH_CHECK
Health check event is purely for testing and debugging purposes.

## ACTION
The actual event for executing the flow. Action body can have following actions:

    {
        "options": { // Optional section. You can set the desired options or leave them as they are.
            "resetAnimation": false, // Defaults to false.
                                     // true = Starts the frame loop from index zero
                                     // false = Continues the frame loop from the previous index number
            "resetColors": false, // Defaults to false.
                                  // true = Reset current color state so that null colors are presented as blacks
                                  // false = If there are null colors, use pervious frame's colors in those indexes
            "runOnce": false, // Defaults to false.
                              // true = Run animation once and after that keep the last frame's state
                              // false = Loop animation
                              // If set to true, resetAnimation will also be automatically set to true
        },
        "sections": [
            {
                "start": 0, // Starting index of led strip
                "end": 3, // Ending index of led strip
                "type": "STATIC", // Action type
                "colors": [ // Colors which are looped through the section
                    { // In this example we will show white and black alternately
                        "r": 255,
                        "g": 255,
                        "b": 255
                    },
                    {
                        "r": 0,
                        "g": 0,
                        "b": 0
                    }
                ]
            },
            {
                "start": 4,
                "end": 7,
                "type": "BLINK", // Whole section blink example
                "colors": [ // Colors which are presented in blink animation
                    { // In this example we will show white, black and grey one at a time with smooth transitions
                        "r": 255,
                        "g": 255,
                        "b": 255
                    },
                    {
                        "r": 0,
                        "g": 0,
                        "b": 0
                    },
                    {
                        "r": 100,
                        "g": 100,
                        "b": 100
                    }
                ],
                "properties": {
                    "transition": 20, // Transition time from one color to another in frames
                }
            },
            {
                "start": 8,
                "end": 15,
                "type": "RANDOM_BLINK", // Random blink example
                "colors": [
                    { // In this example we will request white and blue random blinking
                        "r": 255,
                        "g": 255,
                        "b": 255
                    },
                    {
                        "r": 0,
                        "g": 0,
                        "b": 200
                    }
                ],
                "properties": {
                    "blinkers": 3, // Count of blinking lights which are randomly placed
                    "transition": 20, // Transition time from one color to another in frames
                    "steps": 20, // Optional. How many times blinkers are randomly placed before loop.
                    "r": 0,
                    "g": 0, // Fill color for non-blinking leds
                    "b": 0
                }
            },
            {
                "start": 16,
                "end": 23,
                "type": "WAVE", // Wave example
                "colors": [
                    { // In this example we will request waving between white and red
                        "r": 255,
                        "g": 255,
                        "b": 255
                    },
                    {
                        "r": 255,
                        "g": 0,
                        "b": 0
                    }
                ],
                "properties": {
                    "speed": 1, // Speed of color in frames for color hopping to the next led
                    "length": 20 // Count of leds for requested color and easing leds before next color
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

:white_check_mark: Timer engine

:white_check_mark: Response stream for receivers

# Kudos
- Image design by [Flaticon](https://www.flaticon.com/)
- My wife for keeping children entertained
