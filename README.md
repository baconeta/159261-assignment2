# Space Resistance

A classic Shoot 'em up game with a rogue-like level system, built in Java, made by Ali Soltanian Fard Jahromi, Josh 
Pearson, and Tessa Power.

## Requirements

- Java SE 17 or above
- IntelliJ (or Maven for command-line-only builds)

---

## How to Run Space Resistance

### Run with IntelliJ

This project was developed using the Maven build system. When first opening the project, you'll see the project
plugins and dependencies being downloaded. It may take a minute for the project to process and index all the build
files.

⚠️ Please do **not** delete the `.run` directory—it contains the "Run Game" configuration necessary for you to
build and run the game.

When the project has loaded, you will find the Run Game configuration in the menu bar

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

!["Play Game" single player view](docs/images/play-screen.png)

The rules are simple:

- Use the arrow keys to control the ship. In multiplayer mode, player two uses the W-A-S-D keys.
- Press the enter key to fire (shift for player two), and hold down the fire key for rapid fire.
- Dodge the enemy bullets and take down the enemies.
- Some enemies will drop items, such as health and shields; fly into them to pick them up.

### Space Resistance Features

- Level system getting gradually more difficult over time
- Multiple different enemies, including bosses!
- Boss fights with player-chasing AI
- Multiplayer mode for competitive gameplay
- Custom graphics, animations, and sound effects

### Future Improvements

- Implementation of a high-scores system
- Implementation of other objects (instead of only ships) flying towards the player
- Implementation of other weapons or ammo system

---

## Game Design

Space Resistance is a classic shoot’em-up style game with a rogue-like level system. The aim is to defeat as many 
enemy ships as possible and get the highest score by surviving as long as possible!

There are three different types of enemy ships, Mites, Grasshoppers, and Tarantulas:
<p float="left"> 
<img src="space-resistance/src/main/resources/MiteEnemy.png" alt="drawing" width="200"/>
<img src="space-resistance/src/main/resources/GrasshopperEnemy.png" alt="drawing" width="200"/>
<img src="space-resistance/src/main/resources/TarantulaEnemy.png" alt="drawing" width="200"/>
</p>


There's also a boss, known as the "Goliath":

<p float="left"> 
<img src="space-resistance/src/main/resources/GoliathBlack.png" alt="drawing" width="200"/>
<img src="space-resistance/src/main/resources/GoliathRed.png" alt="drawing" width="200"/>
<img src="space-resistance/src/main/resources/GoliathBlue.png" alt="drawing" width="200"/>
<img src="space-resistance/src/main/resources/GoliathOrange.png" alt="drawing" width="200"/>
</p>


As the player continues through the game, they will encounter enemies and a Goliath. After the Goliath is killed, the 
player advances to the next level. The next wave begins immediately, where the enemies will be tougher to beat. 
Players can watch out for pickup items on their mission, including shields and health. These are dropped randomly by 
enemies that are killed.

### Graphics

All game graphics and animations are original works of Ali Soltanian Fard Jahromi using Blender. The inspiration for the design of the 
graphics for each enemy ship was from real world insects, e.g. the Goliath boss was inspired by the Goliath beetle. 

A few nice touches to look out for:

- Subtle impact explosions as bullets hit an enemy ship
- Epic explosions as the enemies are finally destroyed!

### Audio

Ali Soltanian Fard Jahromi created the game audio by modifying and mixing sounds recorded by Josh. If you listen closely, you'll 
hear Josh shouting "pew pew!" as the player fires bullets!

Background Music by: [Steven Melin](https://stevenmelin.com)

---

## Architecture

### Overview

Space Resistance was developed with the minimum version of OpenJDK 17, and uses Maven to build the project including its
dependencies, and package it into an executable `.JAR`. The game engine used to develop Space Resistance is Tessa's 
external `TEngine` project, which has been included locally as a module to simplify the build process.

### Project Overview and Maven

The project is organised to work with the Maven build system—you can find all the source code in 
`./space-resistance/src/main/java/`.

Within the source files, there are two packages, `space-resistance` and `tengine`:

![`Packages` basic package diagram](docs/images/basic-package-diagram.PNG)

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

### The `EnemySpawningSystem`

The Enemy Spawning System was designed from the ground up to be flexible and as abstract as possible while 
fitting the constraints of the game. It is created with a STATE that handles the processing of everything within the 
system, including all enemies that it spawns into the world. This also passes update calls from the game world to each 
relevant actor inside the system (enemies and bosses). An EnemyWave object (part of system) is made up of some static 
constants for game design and a list of enemies which is generated when a new Wave is instantiated with difficulty and 
level variables.

### The `GameWorld`

The `GameWorld` coordinates the gameplay and manages the interactions between actors depending on the game 
configuration.

![`GameWorld` class overview](docs/images/gameworld.PNG)

### Screen Management

At a higher level than the `GameWorld` is the `PlayGameScreen`, which is, as the name suggests, the screen that is
loaded when the user starts playing the game. We also have the `MenuScreen`, which internally is made up of
smaller `Menu`s, and the `GameOverScreen`. To manage moving between all of these screens at yet another higher level,
we have the `Game` class, which extends the `TEngine` game engine, and is the entry point for the program. The 
`Game` is where we initially set up everything needed for Space Resistance, and then it manages loading and unloading
each of these screens. It then listens for callbacks from each screen to know when to transition and which screen 
to load next.

![`GameWorld` managing interactions](docs/images/screen-management.png)

The first screen loaded is the `MenuScreen`, which lets the player select the game configuration and makes
that available to the `PlayGameScreen` through `Settings`. While the `PlayGameScreen` is active, it updates the
`GameState` so that when the game is over and the `GameOverScreen` is loaded, it can be passed the `GameState` and
display the results.

---

## Attributions

- In-game music by [Steven Melin](https://stevenmelin.com), free for personal and commercial use.
- Revamped font by [Chequered Ink](https://www.dafont.com/revamped.font), free for personal and commercial use.

### Ali Soltanian Fard Jahromi: Lead Game and Level Designer

- Game art and animations, including Sprites and UI elements
- Player setup, including player controls, systems and gameplay design
- Firing systems for both the enemies and players, including bosses
- All background elements including scrolling system
- HUD system design and programming
- Explosions system for enemies and bosses
- Pausing system and pause menu design
- Created and added all sound effects
- Boundary system for player
- Enemy type and class system design and programming
- Level system implementation

### Joshua Pearson: Producer, Project Lead, and Development

- VCS, organisation, and production processes
- Enemy spawning system design and implementation
- Enemy wave and boss transitions design and implementation
- Collision management and optimisation
- Pickup system, shield system and functionality
- Boss AI system that tracks player movement
- Enemies and Boss design and implementation
- Multiplayer mode design and implementation
- Level system implementation
- Firing systems for enemies, including bosses

### Tessa Power: Engine, Tools, and Game Development

- Initial game structure (menu, screens, and transitions) design and implementation
- TEngine design and implementation including:
  - Broad-phase collision detection
  - Collision events notifier
  - Offset collision shapes
  - ECS for Actors to represent game objects
- HUD system implementation
- Flyweight pattern for asset optimisation
- Mediator pattern for screen systems and data handling
