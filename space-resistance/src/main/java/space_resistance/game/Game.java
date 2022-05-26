package space_resistance.game;

import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import space_resistance.ui.screens.gameover.GameOverScreen;
import space_resistance.ui.screens.gameplay.PauseScreen;
import space_resistance.ui.screens.gameplay.PlayGameScreen;
import space_resistance.ui.screens.menu.MenuScreen;
import tengine.GameEngine;
import tengine.geom.TPoint;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(600, 800);
    // TODO: remove if still unused before submitting
    public static final TPoint WINDOW_CENTER = new TPoint(WINDOW_DIMENSION.width / 2, WINDOW_DIMENSION.height / 2);
    private static final String TITLE = "Space Resistance by Team Pew Pew!";
    public static boolean DEBUG_MODE = false;

    public Screen activeScreen;

    public static void main(String[] args) {
        createGame(new Game(), 60);
    }

    @Override
    public void init() {
        setWindowProperties(WINDOW_DIMENSION, TITLE);
        activeScreen = null;
        requestScreenChange(ScreenIdentifier.SHOWING_MENU);
    }

    @Override
    public void update(double dtMillis) {
        super.update(dtMillis);
        if (activeScreen != null) {
            activeScreen.update(dtMillis);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        activeScreen.handleKeyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        activeScreen.handleKeyReleased(keyEvent);
    }

    @Override
    public void onCollision(CollisionEvent event) {
        if (activeScreen instanceof PlayGameScreen playGameScreen) {
            playGameScreen.handleCollisionEvent(event);
        }
    }

    public void requestScreenChange(ScreenIdentifier newScreen) {
        if (activeScreen != null && activeScreen.screen() == newScreen) return;
        if (activeScreen != null) activeScreen.removeFromCanvas();

        switch(newScreen) {
            case SHOWING_MENU -> activeScreen = new MenuScreen(this::requestScreenChange);
            case PLAYING -> {
                if (activeScreen instanceof PauseScreen) {
                    activeScreen = new PlayGameScreen(this::requestScreenChange, ((PauseScreen) activeScreen).gameWorld());
                } else {
                    activeScreen = new PlayGameScreen(this::requestScreenChange, null);
                }
            }
            case SHOWING_GAME_OVER -> {
                assert activeScreen != null;
                activeScreen = new GameOverScreen(this::requestScreenChange, ((PlayGameScreen) activeScreen).gameState());
            }
            case SHOWING_PAUSE -> {
                assert activeScreen != null;
                activeScreen = new PauseScreen(this::requestScreenChange,
                    ((PlayGameScreen) activeScreen).gameWorld());
            }
        }

        activeScreen.addToCanvas(this);
    }
}

