package space_resistance.game;

import space_resistance.actors.Explosion;
import space_resistance.actors.ImpactExplosion;
import space_resistance.actors.bullet.Bullet;
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
    private final Notifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;
    private final EnemySpawningSystem enemySpawningSystem;
    private static final Random RANDOM = new Random();

    private final HeadsUpDisplay hud;

    // Players
    private SpaceShip playerOne;
    private SpaceShip playerTwo = null;

    // Pickups
    private static final Dimension pickupDimension = new Dimension(32, 32);
    private static final int chanceHealth = 5;
    private static final int chanceShield = 3;
    private static final int chanceMissiles = 2;

    private static final String BACKGROUND = "SpaceBackground.png";
    private static final Dimension DIMENSION = new Dimension(600, 800);
    ArrayList<Background> background = new ArrayList<>();

    private final TGraphicCompound container;

    public GameWorld(Dimension dimension, Notifier gameOverNotifier, GameState gameState) {
        super(dimension);

        // Space Background
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
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

        for (Actor a: actors) {
            if (a instanceof Bullet) { ((Bullet) a).update(); }
            if (a instanceof Enemy) {
                ((Enemy) a).update();
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
        } else if (a instanceof Enemy && b instanceof PlayerBullet) {
            if (((Enemy) a).takeDamage(((PlayerBullet) b).damageToDeal())) {
                this.add(new Explosion(this, a.origin(), ((Enemy)a).type));
                trySpawnPickup(new TPoint(a.origin().x + a.graphic().width() * 0.25,
                        a.origin().y + a.graphic().height() * 0.25));
                gameState.playerOne().increaseScore(((Enemy) a).scoreValue());
                a.removeFromWorld();
            }
            this.add(new ImpactExplosion(this, new TPoint(b.origin().x - 15, b.origin().y - 20)));
            b.removeFromWorld();
        } else if (a instanceof PlayerBullet && b instanceof Enemy) {
            if (((Enemy) b).takeDamage(((PlayerBullet) a).damageToDeal())) {
                this.add(new Explosion(this, b.origin(),((Enemy)b).type));
                gameState.playerOne().increaseScore(((Enemy) b).scoreValue());
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
            this.add(new Pickup(PickupType.Missiles, this, locationToSpawn, pickupDimension));
        }
        else if (spawnValue <= chanceMissiles+chanceShield) {
            this.add(new Pickup(PickupType.Shield, this, locationToSpawn, pickupDimension));
        } else if (spawnValue <= chanceHealth+chanceShield+chanceMissiles) {
            this.add(new Pickup(PickupType.Health, this, locationToSpawn, pickupDimension));
        }
    }
}
