package space_resistance.game;

import space_resistance.actors.Explosion;
import space_resistance.actors.ImpactExplosion;
import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.actors.bullet.PlayerBullet;
import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.pickup.Pickup;
import space_resistance.actors.pickup.PickupType;
import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.sprites.Background;
import space_resistance.settings.MultiplayerMode;
import space_resistance.ui.screens.gameplay.HeadsUpDisplay;
import space_resistance.utils.Notifier;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.physics.collisions.events.CollisionEvent;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameWorld extends World {
    private static final Random RANDOM = new Random();
    private static final TPoint PLAYER_ONE_SPAWN_POS = new TPoint(300, 600);
    private static final TPoint PLAYER_TWO_SPAWN_POS = new TPoint(300, 300);

    // Pickups
    private static final double chanceHealth = 0.05;
    private static final double chanceShield = 0.03;
    private static final double chanceMissiles = 0.02;

    private final Notifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;
    private final EnemySpawningSystem enemySpawningSystem;

    private final HeadsUpDisplay hud;

    // Players
    private SpaceShip playerOne;
    private SpaceShip playerTwo = null;

    public GameWorld(Dimension dimension, Notifier gameOverNotifier, GameState gameState) {
        super(dimension);
        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        // Background: must come first for it to be drawn underneath everything else
        canvas.add(Background.movingBackground());

        initPlayers();

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(),  gameState);
        canvas.addAll(hud);

        // Enemy Spawning System set up and binding to canvas
        enemySpawningSystem = new EnemySpawningSystem(this);
    }

    private void initPlayers() {
        playerOne = SpaceShip.spawnAt(this, PLAYER_ONE_SPAWN_POS, gameState.playerOne());

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = SpaceShip.spawnAt(this, PLAYER_TWO_SPAWN_POS, gameState.playerTwo());
        }
    }

    public void update() {
        hud.update(gameState);

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
            //  of enemies?
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
        } else if (a instanceof Enemy enemy && b instanceof PlayerBullet playerBullet) {
            // TODO: Fix duplicated code blocks...
            boolean isEnemyDead = enemy.takeDamage(playerBullet.damageToDeal());
            if (isEnemyDead) {
                add(new Explosion(this, enemy.origin(), enemy.type()));

                gameState.playerOne().increaseScore(enemy.scoreValue());

                TPoint possiblePickupLocation = new TPoint(
                    enemy.origin().x + enemy.graphic().width() * 0.25,
                    enemy.origin().y + enemy.graphic().height() * 0.25);
                trySpawnPickup(possiblePickupLocation);

                enemy.removeFromWorld();
            }

            add(new ImpactExplosion(this, new TPoint(playerBullet.origin().x - 15, playerBullet.origin().y - 20)));

            playerBullet.removeFromWorld();
        } else if (a instanceof PlayerBullet playerBullet && b instanceof Enemy enemy) {
            boolean isEnemyDead = enemy.takeDamage(playerBullet.damageToDeal());
            if (isEnemyDead) {
                add(new Explosion(this, enemy.origin(), enemy.type()));

                gameState.playerOne().increaseScore(enemy.scoreValue());

                TPoint possiblePickupLocation = new TPoint(
                    enemy.origin().x + enemy.graphic().width() * 0.25,
                    enemy.origin().y + enemy.graphic().height() * 0.25);
                trySpawnPickup(possiblePickupLocation);

                enemy.removeFromWorld();
            }

            add(new ImpactExplosion(this, new TPoint(playerBullet.origin().x - 15, playerBullet.origin().y - 20)));

            playerBullet.removeFromWorld();
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
