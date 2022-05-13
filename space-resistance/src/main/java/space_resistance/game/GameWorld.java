package space_resistance.game;

import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.FontBook;
import space_resistance.assets.sprites.Background;
import space_resistance.settings.MultiplayerMode;
import space_resistance.utils.Notifier;
import space_resistance.ui.screens.gameplay.HeadsUpDisplay;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;
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

    // Enemies
    //private GrassHopperEnemy testEnemy = null;
    //private MiteEnemy testEnemy2 = null;
    //private TarantulaEnemy testEnemy3 = null;

    private final String BACKGROUND = "SpaceBackground.png";
    private static final Dimension DIMENSION = new Dimension(600, 800);
    ArrayList<Background> background = new ArrayList<Background>();

    private final TGraphicCompound container;

    public GameWorld(Dimension dimension, Notifier gameOverNotifier, GameState gameState) {
        super(dimension);
        // Space Background
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
        background.add(new Background(AssetLoader.load(BACKGROUND), DIMENSION));
        background.add(new Background(AssetLoader.load(BACKGROUND), DIMENSION));
        background.get(1).setOrigin(new Point(0, -800));
        for (Background b : background){
            container.add(b);
        }
        canvas.add(container);
        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        initPlayers();
        //testEnemy  = GrassHopperEnemy.spawnAt(this, new Point(20, 50));
        //testEnemy2 = MiteEnemy.spawnAt(this, new Point(150, 50));
        //testEnemy3 = TarantulaEnemy.spawnAt(this, new Point(150, 50));
        TLabel placeholderLabel = new TLabel("Gameplay Screen");
        placeholderLabel.setFont(FontBook.shared().defaultFont());
        placeholderLabel.setColor(Color.BLACK);
        placeholderLabel.setOrigin(new Point(20, 20));

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(),  gameState);

        // Display graphics and actors by adding them to the canvas.

        canvas.addAll(placeholderLabel, hud);

        // Enemy Spawning System set up and binding to canvas
        enemySpawningSystem = new EnemySpawningSystem(this);
    }

    private void initPlayers() {
        playerOne = SpaceShip.spawnAt(this, new Point(300, 600), gameState.playerOne());

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = SpaceShip.spawnAt(this, new Point(300, 300), gameState.playerTwo());
        }
    }

    public void update() {
        for (int i = 0; i < background.size(); i ++){
            background.get(i).setOrigin(new Point(background.get(i).origin().x, background.get(i).origin().y + 1));
            if (background.get(0).origin().y == 800){
                background.get(0).setOrigin(new Point(0, 0));
                background.get(1).setOrigin(new Point(0, -800));
            }
        }
        playerOne.update();
        // Test enemies
        // testEnemy.update();
        // testEnemy2.update();
        // testEnemy3.update();
        if (gameState.playerOne().healthRemaining() == 0) {
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
}
