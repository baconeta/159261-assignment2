package space_resistance.game;

import space_resistance.actors.Explosion;
import space_resistance.actors.ImpactExplosion;
import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.actors.bullet.PlayerBullet;
import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.pickup.Pickup;
import space_resistance.actors.pickup.PickupType;
import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.Background;
import space_resistance.settings.MultiplayerMode;
import space_resistance.ui.screens.gameplay.HeadsUpDisplay;
import space_resistance.utils.Notifier;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.physics.collisions.events.CollisionEvent;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld extends World {
    private static final Dimension DIMENSION = new Dimension(600, 800);
    private static final String BACKGROUND = "SpaceBackground.png";
    private static final Random RANDOM = new Random();

    // Pickups
    private static final double chanceHealth = 0.05;
    private static final double chanceShield = 0.03;
    private static final double chanceMissiles = 0.02;

    // Background container
    private final TGraphicCompound container;

    private final Notifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;
    private final EnemySpawningSystem enemySpawningSystem;

    private final HeadsUpDisplay hud;

    // Players
    private SpaceShip playerOne;
    private SpaceShip playerTwo = null;

    // Pickups
    private static final Dimension pickupDimension = new Dimension(32, 32);

    private ArrayList<Background> background = new ArrayList<>();

    public GameWorld(Dimension dimension, Notifier gameOverNotifier, GameState gameState) {
        super(dimension);

        // Space Background
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
        // TODO: Include this in flyweight
        background.add(new Background(AssetLoader.load(BACKGROUND), DIMENSION));
        background.add(new Background(AssetLoader.load(BACKGROUND), DIMENSION));
        background.get(1).setOrigin(new TPoint(0, -800));
        for (Background b : background){
            container.add(b);
        }
        canvas.add(container);

        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        initPlayers();

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(),  gameState);

        // Display graphics and actors by adding them to the canvas.
        canvas.addAll(hud);

        // Enemy Spawning System set up and binding to canvas
        enemySpawningSystem = new EnemySpawningSystem(this);
    }

    private void initPlayers() {
        playerOne = SpaceShip.spawnAt(this, new TPoint(300, 600), gameState.playerOne());

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = SpaceShip.spawnAt(this, new TPoint(300, 300), gameState.playerTwo());
        }
    }

    public void update() {
        hud.update(gameState);

        // TODO: Potentially buggy, check for optimization
        for (int i = 0; i < background.size(); i ++){
            background.get(i).setOrigin(new TPoint(background.get(i).origin().x, background.get(i).origin().y + 1));
            if (background.get(0).origin().y == 800){
                background.get(0).setOrigin(new TPoint(0, 0));
                background.get(1).setOrigin(new TPoint(0, -800));
            }
        }

        playerOne.update();
        if (gameState.playerOne().healthRemaining() <= 0) {
            setGameOver();
        }

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.update();
            if (gameState.playerTwo().healthRemaining() == 0) {
                setGameOver();
            }
        }

        for (Actor a : actors) {
            // TODO: Instead of iterating over all actors, have the enemySpawningSystem call update on its list
            //  of enemies
            if (a instanceof Enemy enemy) {
                enemy.update();
            }
        }

        enemySpawningSystem.update();
    }

    private void setGameOver() {
        gameOverNotifier.sendNotify();
    }

    // Dispatch relevant key events to the appropriate actors
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (playerOne.handleKeyPressed(keyEvent)) return;

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.handleKeyPressed(keyEvent);
        }
    }

    public void handleKeyReleased(KeyEvent keyEvent) {
        if (playerOne.handleKeyReleased(keyEvent)) return;

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.handleKeyReleased(keyEvent);
        }
    }

    public GameConfig gameConfig() {
        return gameConfig;
    }

    public void handleCollisions(CollisionEvent event) {
        Actor a = event.actorA();
        Actor b = event.actorB();

        if (a == playerOne && (b instanceof Enemy || b instanceof EnemyBullet || b instanceof Pickup)) {
            playerOne.collision(b);
            b.removeFromWorld();
        } else if (b == playerOne && (a instanceof Enemy || a instanceof EnemyBullet || a instanceof Pickup)) {
            playerOne.collision(a);
            a.removeFromWorld();
        } else if (a instanceof Enemy enemy && b instanceof PlayerBullet pBullet) {
            if (enemy.takeDamage(pBullet.damageToDeal())) {
                this.add(new Explosion(this, a.origin(), (enemy.type)));
                trySpawnPickup(new TPoint(a.origin().x + a.graphic().width() * 0.25,
                        a.origin().y + a.graphic().height() * 0.25));
                gameState.playerOne().increaseScore(enemy.scoreValue());
                a.removeFromWorld();
            }
            this.add(new ImpactExplosion(this, new TPoint(b.origin().x - 15, b.origin().y - 20)));
            b.removeFromWorld();
        } else if (a instanceof PlayerBullet pBullet && b instanceof Enemy enemy) {
            if (enemy.takeDamage(pBullet.damageToDeal())) {
                this.add(new Explosion(this, b.origin(), enemy.type));
                gameState.playerOne().increaseScore(enemy.scoreValue());
                trySpawnPickup(new TPoint(b.origin().x + b.graphic().width() * 0.25,
                        b.origin().y + b.graphic().height() * 0.25));
                b.removeFromWorld();
            }
            this.add(new ImpactExplosion(this, new TPoint(a.origin().x - 15, a.origin().y - 20)));
            a.removeFromWorld();
        }
    }

    private void trySpawnPickup(TPoint locationToSpawn) {
        int spawnValue = RANDOM.nextInt(1, 101);
        if (spawnValue <= chanceMissiles) {
            this.add(new Pickup(PickupType.MISSILE, locationToSpawn));
        }
        else if (spawnValue <= chanceMissiles+chanceShield) {
            this.add(new Pickup(PickupType.SHIELD, locationToSpawn));
        } else if (spawnValue <= chanceHealth+chanceShield+chanceMissiles) {
            this.add(new Pickup(PickupType.HEALTH, locationToSpawn));
        }
    }

    public SpaceShip getPlayer() {
        if (gameConfig.multiplayerMode() == MultiplayerMode.SINGLE_PLAYER) {
            return playerOne;
        } else {
            if (RANDOM.nextInt(2) == 0) { return playerOne; }
            return playerTwo;
        }
    }
}
