package space_resistance.game;

import space_resistance.actors.Explosion;
import space_resistance.actors.ImpactExplosion;
import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.actors.bullet.PlayerBullet;
import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.pickup.Pickup;
import space_resistance.actors.pickup.PickupType;
import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.Background;
import space_resistance.settings.MultiplayerMode;
import space_resistance.ui.screens.gameplay.HeadsUpDisplay;
import space_resistance.utils.Notifier;
import tengine.Actor;
import tengine.audio.AudioClip;
import tengine.geom.TPoint;
import tengine.physics.collisions.events.CollisionEvent;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameWorld extends World {
    private static final AudioClip BACKGROUND_MUSIC = SoundEffects.shared().backgroundMusic();
    private static final Random RANDOM = new Random();
    private static final TPoint PLAYER_ONE_SPAWN_POS = new TPoint(270, 600);
    private static final TPoint PLAYER_TWO_SPAWN_POS = new TPoint(420, 600);
    private static final TPoint PLAYER_ONE_SPAWN_POS_MP = new TPoint(120, 600);

    // Pickups
    private static final double chanceHealth = 0.05;
    private static final double chanceShield = 0.04;
    private static final double chanceMissiles = 0.03;

    private final Notifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;
    private final EnemySpawningSystem enemySpawningSystem;

    private final HeadsUpDisplay hud;

    // Players
    private SpaceShip playerOne;
    private SpaceShip playerTwo = null;

    // Boss targeting
    private SpaceShip bossTarget = null;
    private static final double BOSS_TARGET_RESET_SECS = 2.5;
    private double resetTimeLeft;

    public GameWorld(Dimension dimension, Notifier gameOverNotifier, GameState gameState) {
        super(dimension);
        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        // Background: must come first for it to be drawn underneath everything else
        Background background = Background.getInstance();
        background.setIsStatic(false);
        canvas.add(background);

        initPlayers();

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(),  gameState);
        canvas.addAll(hud);

        // Enemy Spawning System set up and binding to canvas
        enemySpawningSystem = new EnemySpawningSystem(this);
    }

    private void initPlayers() {
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerOne = SpaceShip.spawnAt(this, PLAYER_ONE_SPAWN_POS_MP, gameState.playerOne());
            playerTwo = SpaceShip.spawnAt(this, PLAYER_TWO_SPAWN_POS, gameState.playerTwo());
        } else {
            playerOne = SpaceShip.spawnAt(this, PLAYER_ONE_SPAWN_POS, gameState.playerOne());
        }
    }

    public void update(double dt) {
        // Ugly hack to fix the audio stopping mid-game
        if (!BACKGROUND_MUSIC.getLoopClip().isRunning()) {
            BACKGROUND_MUSIC.playOnLoop();
        }

        hud.update(dt);
        playerUpdates();
        handleBossTargeting(dt);
        enemySpawningSystem.update();
    }

    private void playerUpdates() {
        playerOne.update();

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.update();
            if (gameState.playerTwo().healthRemaining() <= 0) {
                playerTwo.getPlayer().playerDied();
                playerTwo.markPendingKill();
                if (playerOne.getPlayer().dead()) {
                    setGameOver();
                }
            }
            if (gameState.playerOne().healthRemaining() <= 0) {
                playerOne.getPlayer().playerDied();
                playerOne.markPendingKill();
                if (playerTwo.getPlayer().dead()) {
                    setGameOver();
                }
            }
        } else if (gameConfig.multiplayerMode() == MultiplayerMode.SINGLE_PLAYER) {
            if (gameState.playerOne().healthRemaining() <= 0) {
                setGameOver();
            }
        }
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

        if (a instanceof SpaceShip player && (b instanceof Enemy || b instanceof EnemyBullet || b instanceof Pickup)) {
            player.collision(b);
            b.markPendingKill();
        } else if (b instanceof SpaceShip player && (a instanceof Enemy || a instanceof EnemyBullet || a instanceof Pickup)) {
            player.collision(a);
            a.markPendingKill();
        } else if (a instanceof Enemy enemy && b instanceof PlayerBullet playerBullet) {
            handleEnemyDamage(playerBullet, enemy);
        } else if (a instanceof PlayerBullet playerBullet && b instanceof Enemy enemy) {
            handleEnemyDamage(playerBullet, enemy);
        }
    }

    private void handleEnemyDamage(PlayerBullet playerBullet, Enemy enemy) {
        boolean isEnemyDead = enemy.takeDamage(playerBullet.damageToDeal());
        if (isEnemyDead) {
            add(new Explosion(this, enemy.origin(), enemy.type()));

            playerBullet.instigator().increaseScore(enemy.scoreValue());

            TPoint possiblePickupLocation = new TPoint(
                enemy.origin().x + enemy.graphic().width() * 0.25,
                enemy.origin().y + enemy.graphic().height() * 0.25);
            trySpawnPickup(possiblePickupLocation);

            enemy.markPendingKill();
        }

        add(new ImpactExplosion(this, new TPoint(playerBullet.origin().x - 15, playerBullet.origin().y - 20)));

        playerBullet.markPendingKill();
    }

    private void trySpawnPickup(TPoint locationToSpawn) {
        double spawnValue = RANDOM.nextDouble();
        if (spawnValue <= chanceMissiles) {
            this.add(new Pickup(PickupType.MISSILE, locationToSpawn));
        }
        else if (spawnValue <= chanceMissiles+chanceShield) {
            this.add(new Pickup(PickupType.SHIELD, locationToSpawn));
        } else if (spawnValue <= chanceHealth+chanceShield+chanceMissiles) {
            this.add(new Pickup(PickupType.HEALTH, locationToSpawn));
        }
    }

    public SpaceShip bossTarget() {
        if (gameConfig.multiplayerMode() == MultiplayerMode.SINGLE_PLAYER) {
            return playerOne;
        } else {
            if (bossTarget == null) {
                bossTarget = getNewBossTarget();
            }
            return bossTarget;
        }
    }

    private SpaceShip getNewBossTarget() {
        resetTimeLeft = BOSS_TARGET_RESET_SECS;
        if (RANDOM.nextInt(2) == 0) {
            return playerOne;
        }
        return playerTwo;
    }

    private void handleBossTargeting(double dt) {
        resetTimeLeft -= dt;
        if (bossTarget != null && resetTimeLeft <= 0.0) {
            bossTarget = null;
        }
    }
}
