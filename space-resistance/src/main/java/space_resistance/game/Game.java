package space_resistance.game;

import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import space_resistance.ui.screens.gameover.GameOverScreen;
import space_resistance.ui.screens.gameplay.PlayGameScreen;
import space_resistance.ui.screens.menu.MenuScreen;
import tengine.GameEngine;
import tengine.geom.TPoint;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(600, 800);
    public static final TPoint WINDOW_CENTER = new TPoint(WINDOW_DIMENSION.width / 2, WINDOW_DIMENSION.height / 2);
    private static final String TITLE = "Space Resistance by Team Pew Pew!";
    public static boolean DEBUG_MODE = false;

    private Screen activeScreen;

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
        activeScreen.handleCollisionEvent(event);
    }

    public void requestScreenChange(ScreenIdentifier newScreen) {
        if (activeScreen != null && activeScreen.screen() == newScreen) return;
        if (activeScreen != null) activeScreen.removeFromCanvas();

        switch(newScreen) {
            case SHOWING_MENU -> activeScreen = new MenuScreen(this, this::requestScreenChange);
            case PLAYING -> activeScreen = new PlayGameScreen(this, this::requestScreenChange);
            case SHOWING_GAME_OVER -> {
                assert activeScreen != null;
                activeScreen = new GameOverScreen(this, this::requestScreenChange, ((PlayGameScreen) activeScreen).gameState());
            }
        }

        activeScreen.addToCanvas();
    }
}

