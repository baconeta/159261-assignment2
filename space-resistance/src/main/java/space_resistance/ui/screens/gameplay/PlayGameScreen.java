package space_resistance.ui.screens.gameplay;

import space_resistance.assets.SoundEffects;
import space_resistance.game.Game;
import space_resistance.game.GameState;
import space_resistance.game.GameWorld;
import space_resistance.settings.Settings;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class PlayGameScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final GameWorld world;
    private final GameState gameState;
    public static boolean isPaused = false;


    public PlayGameScreen(Consumer<ScreenIdentifier> screenChangeCallback) {
        SoundEffects.shared().backgroundMusic().playOnLoop();
        this.screenChangeCallback = screenChangeCallback;
        gameState = new GameState(Settings.shared().config());

        world = new GameWorld(Game.WINDOW_DIMENSION, this::onGameOverNotified, gameState);
    }

    public void onGameOverNotified() {
        screenChangeCallback.accept(ScreenIdentifier.SHOWING_GAME_OVER);
    }


    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            SoundEffects.shared().backgroundMusic().stopPlayingLoop();
            screenChangeCallback.accept(ScreenIdentifier.SHOWING_PAUSE);
        } else {
            world.handleKeyPressed(keyEvent);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent keyEvent) {
        world.handleKeyReleased(keyEvent);
    }

    @Override
    public void addToCanvas(Game game) {
        game.loadWorld(world);
    }

    @Override
    public void removeFromCanvas() {
        world.canvas().removeFromParent();
    }

    @Override
    public void update(double dtMillis) {
        if (!isPaused) {
            world.update();
        }
    }

    public void handleCollisionEvent(CollisionEvent event) {
        world.handleCollisions(event);
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.PLAYING;
    }

    public GameState gameState() {
        return gameState;
    }
}
