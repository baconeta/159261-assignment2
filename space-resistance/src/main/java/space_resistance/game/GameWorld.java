package space_resistance.game;

import space_resistance.actors.Explosion;
import space_resistance.actors.bullet.Bullet;
import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.actors.bullet.PlayerBullet;
import space_resistance.actors.enemy.Enemy;
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

public class GameWorld extends World {
    private final Notifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;
    private EnemySpawningSystem enemySpawningSystem;

    private final HeadsUpDisplay hud;

    // Players
    private SpaceShip playerOne;
    private SpaceShip playerTwo = null;


    private final String BACKGROUND = "SpaceBackground.png";
    private static final Dimension DIMENSION = new Dimension(600, 800);
    ArrayList<Background> background = new ArrayList<Background>();

    private final TGraphicCompound container;
    //Test Enemy
    //private Enemy testEnemy;

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
        //Test Enemy
        //testEnemy = new Enemy(EnemyType.Grasshopper, this, new TPoint(0, 0), new Dimension(72,72), 300);

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
        //testEnemy.update();
        hud.update(gameState);
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

    // TODO: potentially buggy as playerOne also seems to be calling destroy on the other actor, need to replace calls
    //  to actor.destroy() with actor.removeFromWorld()
    public void handleCollisions(CollisionEvent event) {
        Actor a = event.actorA();
        Actor b = event.actorB();

        if (a == playerOne && (b instanceof Enemy || b instanceof EnemyBullet)) {
            playerOne.collision(b);
            b.removeFromWorld();
        } else if (b == playerOne && (a instanceof Enemy || a instanceof EnemyBullet)) {
            playerOne.collision(a);
            a.removeFromWorld();
        } else if (a instanceof Enemy && b instanceof PlayerBullet) {
            // TODO: Not sure we need this?
            ((Enemy) a).collision((PlayerBullet) b, gameState.playerOne(), ((Enemy) a).scoreValue());

            this.add(new Explosion(this, a.origin()));
            a.removeFromWorld();
            b.removeFromWorld();
        } else if (a instanceof PlayerBullet && b instanceof Enemy) {
            // TODO: Not sure we need this?
            ((Enemy) b).collision((PlayerBullet) a, gameState.playerOne(), ((Enemy) b).scoreValue());

            this.add(new Explosion(this, b.origin()));
            a.removeFromWorld();
            b.removeFromWorld();
        }
    }
}
