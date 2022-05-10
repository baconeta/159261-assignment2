package space_resistance.game;

import space_resistance.actors.enemy.GrassHopperEnemy;
import space_resistance.actors.enemy.MiteEnemy;
import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.FontBook;
import space_resistance.settings.MultiplayerMode;
import space_resistance.utils.Notifier;
import space_resistance.ui.screens.gameplay.HeadsUpDisplay;
import tengine.graphics.components.text.TLabel;
import tengine.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameWorld extends World {
    private final Notifier gameOverNotifier;
    private final GameState gameState;
    private final GameConfig gameConfig;

    private final HeadsUpDisplay hud;

    // Players
    private SpaceShip playerOne;
    private SpaceShip playerTwo = null;

    // Enemies
    /* Test enemies
    private GrassHopperEnemy testEnemy = null;
    private MiteEnemy testEnemy2 = null;
     */

    public GameWorld(Dimension dimension, Notifier gameOverNotifier, GameState gameState) {
        super(dimension);

        this.gameOverNotifier = gameOverNotifier;
        this.gameState = gameState;
        gameConfig = gameState.gameConfig();

        initPlayers();
        /* Test Enemies
        testEnemy  = GrassHopperEnemy.spawnAt(this, new Point(20, 50));
        testEnemy2 = MiteEnemy.spawnAt(this, new Point(150, 50));
         */
        TLabel placeholderLabel = new TLabel("Gameplay Screen");
        placeholderLabel.setFont(FontBook.shared().defaultFont());
        placeholderLabel.setColor(Color.BLACK);
        placeholderLabel.setOrigin(new Point(20, 20));

        // HUD
        hud = new HeadsUpDisplay(canvas.dimension(),  gameState);

        // Display graphics and actors by adding them to the canvas.
        canvas.addAll(placeholderLabel, hud);
    }

    private void initPlayers() {
        playerOne = SpaceShip.spawnAt(this, new Point(300, 600), gameState.playerOne());

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = SpaceShip.spawnAt(this, new Point(300, 300), gameState.playerTwo());
        }
    }

    public void update() {
        playerOne.update();
        /* Test enemies
        testEnemy.update();
        testEnemy2.update();
         */
        if (gameState.playerOne().healthRemaining() == 0) {
            setGameOver();
        }

        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo.update();
            if (gameState.playerTwo().healthRemaining() == 0) {
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
}
