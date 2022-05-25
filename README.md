# Space Resistance

A classic Shoot 'em up game, built in Java using the TEngine designed almost entirely by Tessa.

## Game Design

Space Resistance is a classic shoot’em up game where the aim is to defeat as many enemy ships as possible.  The game is played in a 600px x 800px window.  Game score increases as they destroy enemy ships.  Moving up levels is achieved through destroying a certain number of enemy ships and defeating the boss.
The player can move up, down, left, right and diagonally to position itself on the screen.  There are three different types of enemy ships in addition to the boss:
- Mite (worth 100 points)
- Grasshopper (worth 300 points)
- Tarantula (worth 500 points)

The boss, known as the Goliath, is worth 1000 points.

As a player continues through the game, they will encounter a randomly generated set of enemies and a boss for each level in the game. After the boss is killed, the game gets more difficult and the next wave begins.
The aim as a player is to achieve a high score, by surviving as long as possible. 

There are pickup elements to help increase the players' survivability, including shields and health restoration modules. These are spawned randomly as enemies are killed

### Game Graphics

All game graphics and animations are original works of Ali Soltanian Fard Jahromi.  The inspiration for the design of the graphics for each enemy ship was from real world insects.  Hence, mite ships are inspired from mites, grasshoppers from grasshoppers, tarantula ships from tarantulas and the goliath ships from goliath beetles.
Game graphics and animations were created with Blender (an open-source 3D computer graphics software).

Visual cues were considered for letting the player know that each bullet has hit an enemy ship.  Animation and code was added so minor explosions are displayed when bullets impact the enemy ships which are different to the explosions for enemy ships being destroyed.

### Game Audio

Most of the game audio was created by Ali Soltanian Fard Jahromi by modifying audios recorded by the developers creating sounds using their mouths.  The sounds were modified to give the sound effects.


## Requirements

- Java SE 17 or above
- IntelliJ (or Maven for command-line-only builds)

---

## How to Run Space Resistance

### Run with IntelliJ

This project was developed using the Maven build system. When first opening the project, you'll see the project
plugins and dependencies being downloaded. It may take a minute for the project to process and index all the build
files.

⚠️ Please do **not** delete the `.idea` directory—it contains the "Run Game" configuration necessary for you to
build and run the game.

When the project has loaded, you will find the Run Game configuration in the menu bar

- [ ] Replace this with an image showing the current project

!["Run Game" run configuration](docs/images/run-game-config.png)

### Run from Commandline

To build from the command-line only, you can use Maven, if you have it installed, and then run the `.JAR`
directly. Enter the following commands in the project root:

```shell
mvn install
java -jar space-resistance/target/space-resistance.jar
```

---

## Playing Space Resistance

A window should appear for you to play the game:

- [ ] Insert screenshot

The rules are simple:

- [ ] Fill this out 

### Space Resistance Features

- [ ] Fill this out 

### Future Improvements

- [ ] Fill this out 

---

## Architecture

### Overview

Space Resistance was developed with the minimum version of OpenJDK 17, and uses Maven to build the project including its
dependencies, and package it into an executable `.JAR`. The game engine used to develop Space Resistance is Tessa's 
external `TEngine` project, which has been included locally as a module to simplify the build process.

### Project Overview and Maven

The project is organised to work with the Maven build system—you can find all the source code in 
`./space-resistance/src/main/java/`.

Within the source files, there are the following packages:

- [ ] Add package diagram

### `TEngine`

The `TEngine` is Tessa's personal rebuild of the Massey GameEngine, loosely based on the ECS software architecture 
pattern. It's a work in progress, so the following is only a brief summary:

- Graphics Engine: Supported ✅
    - Drawing primitives: ovals, rectangles
    - Compound primitive containers
    - Text
    - Sprites
    - Animated Sprites & Sprite Sequences
    - Transforms
- Physics Engine: Supported ✅️
- Audio: Supported ✅
- Actors & Actor Management: Supported ✅
- World Management: Supported ✅

### The `GameWorld`

The `GameWorld` coordinates the gameplay and manages the interactions between actors depending on the game 
configuration.

- [ ] Insert image of `GameWorld` class overview

- [ ] Insert image of sequence diagram for `GameWorld` managing interactions

### Screen Management

At a higher level than the `GameWorld` is the `PlayGameScreen`, which is, as the name suggests, the screen that is
loaded when the user starts playing the game. We also have the `MenuScreen`, which internally is made up of
smaller `Menu`s, and the `GameOverScreen`. To manage moving between all of these screens at a higher level, we have the
`Game` class, which extends the `TEngine` game engine, and is the entry point for the program. The `Game` is where we
initially set up everything needed for Space Resistance, and then it manages loading and unloading each of these 
screens. It then listens for callbacks from each screen to know when to transition and which screen to load next.

![`GameWorld` managing interactions](docs/images/screen-management.png)

The first screen loaded is the `MenuScreen`, which lets the player select the game configuration and makes
that available to the `PlayGameScreen` through `Settings`. While the `PlayGameScreen` is loaded, it updates the
`GameState` so that when the game is over and the `GameOverScreen` is loaded, it can be passed the `GameState` and
display the results.



## Attributions

### Ali Soltanian Fard Jahromi:
#### Lead Game and Level Designer
- Game art and animations, including Sprites and UI elements
- Player setup, including player controls, systems and gameplay design
- Firing systems for both the enemies and players, including bosses
- All background elements including scrolling system
- HUD system design and programming
- Explosions system for enemies and bosses
- Pausing system and pause menu design
- Created and added all sound effects and music
- World handling for out of screen actors and boundary system for player
- Enemy type and class system design and programming

### Joshua Pearson:
#### Producer and Project Lead
-  VCS, organisation and production processes
- Enemy Spawning system
- Enemy Wave design and functionality including boss transitions
- Class refactoring and consistency 
- Collision management and optimisation (game level)
- Pickup system, shield system and functionality
- Simple boss AI system
- Enemy type and class system programming

### Tessa Power:
#### Engine and Tools Developer Lead
- Completely designed ECS Game Engine from the ground up including elements such as:
  1. Physics engine with momentum and collisions system
  2. Actor system for game world objects requiring components and control
  3. Abstracted Graphical system used for visual elements, animations and Actors.
  4. Flyweight design for asset optimisation
- Global system code control and quality checks and fixes
- Complete game backend optimisation
- Game foundation and system set up
