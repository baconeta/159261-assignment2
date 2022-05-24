package space_resistance.ui.screens.gameover;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.Background;
import space_resistance.game.Game;
import space_resistance.game.GameState;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class GameOverScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final Game engine;
    private final TGraphicCompound graphic;

    private final ButtonGroup buttonGroup;
    private final Button playAgain;
    private final Button quit;
    private final String BACKGROUND = "SpaceBackground.png";
    private static final Dimension DIMENSION = new Dimension(600, 800);
    Background background = new Background(AssetLoader.load(BACKGROUND), DIMENSION);
    public GameOverScreen(Game game, Consumer<ScreenIdentifier> screenChangeCallback, GameState gameState) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("Game over!");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().bodyFont());
        title.setOrigin(new TPoint(250, 300));

        // Score
        TLabel score = new TLabel("Score: " + gameState.maxScore());
        score.setColor(Colors.Text.PRIMARY);
        score.setFont(FontBook.shared().bodyFont());
        score.setOrigin(new TPoint(250, 340));

        switch(gameState.gameConfig().multiplayerMode()) {
            case SINGLE_PLAYER -> {
                // Display one player game over screen
            }
            case MULTIPLAYER -> gameState.winner().ifPresentOrElse(winner -> {
                // Display two player game over screen
            },
            () -> {
                title.setText("It's a draw!");
                title.setOrigin(new TPoint(140, 180));
            });
        }

        // Buttons
        playAgain = new Button("PLAY AGAIN");
        playAgain.setOrigin(new TPoint(80, 490));

        quit = new Button("QUIT TO MENU");
        quit.setOrigin(new TPoint(470, 490));

        buttonGroup = new ButtonGroup(playAgain, quit);

        // Graphic
        graphic = new TGraphicCompound(Game.WINDOW_DIMENSION);
        graphic.add(background);
        graphic.addAll(title, score, playAgain, quit);
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> buttonGroup.previous();
            case KeyEvent.VK_RIGHT -> buttonGroup.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                if (buttonGroup.getFocussed() == playAgain) {
                    screenChangeCallback.accept(ScreenIdentifier.PLAYING);
                } else if (buttonGroup.getFocussed() == quit){
                    screenChangeCallback.accept(ScreenIdentifier.SHOWING_MENU);
                }
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {

    }

    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(graphic);
    }

    @Override
    public void removeFromCanvas() {
        graphic.removeFromParent();
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.SHOWING_GAME_OVER;
    }

    @Override
    public void update(double dtMillis) {}

    @Override
    public void handleCollisionEvent(CollisionEvent event) {}
}
