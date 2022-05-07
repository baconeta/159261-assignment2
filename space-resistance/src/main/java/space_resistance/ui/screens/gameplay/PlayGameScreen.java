package space_resistance.ui.screens.gameplay;

import space_resistance.assets.SoundEffects;
import space_resistance.game.Game;
import space_resistance.game.GameState;
import space_resistance.game.GameWorld;
import space_resistance.settings.Settings;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class PlayGameScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final Game engine;
    private final GameWorld world;
    private final GameState gameState;
    private boolean paused;

    public PlayGameScreen(Game game, Consumer<ScreenIdentifier> screenChangeCallback) {
        SoundEffects.shared().backgroundMusic().playOnLoop();
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;
        paused = false;
        gameState = new GameState(Settings.shared().config());
        world = new GameWorld(
                Game.WINDOW_DIMENSION,
                this::onGameOverNotified,
                gameState);
    }

    public void onGameOverNotified() {
        screenChangeCallback.accept(ScreenIdentifier.SHOWING_GAME_OVER);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        } else if (!paused) {
            world.handleKeyEvent(keyEvent);
        }
    }

    @Override
    public void addToCanvas() {
        engine.loadWorld(world);
    }

    @Override
    public void removeFromCanvas() {
        engine.unloadWorld(world);
    }

    @Override
    public void update(double dtMillis) {
        if (!paused) {
            world.update();
        }
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.PLAYING;
    }

    public GameState gameState() {
        return gameState;
    }
}
