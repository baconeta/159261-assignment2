package space_resistance.ui.screens.gameover;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.game.Game;
import space_resistance.game.GameState;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

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

    public GameOverScreen(Game game, Consumer<ScreenIdentifier> screenChangeCallback, GameState gameState) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("Game over!");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().defaultFont());

        // Score
        TLabel score = new TLabel("Score: " + gameState.maxScore());
        score.setColor(Colors.Text.PRIMARY);
        score.setFont(FontBook.shared().defaultFont());
        score.setOrigin(new Point(95, 300));

        switch(gameState.gameConfig().multiplayerMode()) {
            case SINGLE_PLAYER -> {
                // Display one player game over screen
            }
            case MULTIPLAYER -> gameState.winner().ifPresentOrElse(winner -> {
                // Display two player game over screen
            },
            () -> {
                title.setText("It's a draw!");
                title.setOrigin(new Point(140, 180));
            });
        }

        // Buttons
        playAgain = new Button("Play again");
        playAgain.setOrigin(new Point(80, 490));

        quit = new Button("Quit to menu");
        quit.setOrigin(new Point(290, 490));

        buttonGroup = new ButtonGroup(playAgain, quit);

        // Graphic
        graphic = new TGraphicCompound(Game.WINDOW_DIMENSION);

        graphic.addAll(title, score, playAgain, quit);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
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
}
