package space_resistance.game;

import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import space_resistance.ui.screens.gameover.GameOverScreen;
import space_resistance.ui.screens.gameplay.IntroStoryScreen;
import space_resistance.ui.screens.gameplay.PauseScreen;
import space_resistance.ui.screens.gameplay.PlayGameScreen;
import space_resistance.ui.screens.menu.MenuScreen;
import tengine.GameEngine;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

// Acts as a director, that mediates between the different screens (Menu, Game Play, Pause, and Game Over)
public class Game extends GameEngine {
    public static final Dimension WINDOW_DIMENSION = new Dimension(600, 800);
    private static final String TITLE = "Space Resistance by Team Pew Pew!";
    public static final boolean DEBUG_MODE = false;

    private Screen activeScreen = null;
    private PlayGameScreen activeGame = null;

    public static void main(String[] args) {
        createGame(new Game(), 60);
    }

    @Override
    public void init() {
        setWindowProperties(WINDOW_DIMENSION, TITLE);
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
        if (activeScreen != null) {
            activeScreen.handleKeyPressed(keyEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (activeScreen != null) {
            activeScreen.handleKeyReleased(keyEvent);
        }
    }

    @Override
    public void onCollision(CollisionEvent event) {
        if (activeScreen == activeGame) {
            activeGame.handleCollisionEvent(event);
        }
    }

    public void requestScreenChange(ScreenIdentifier newScreen) {
        if (activeScreen != null && activeScreen.screen() == newScreen) return;
        if (activeScreen != null) activeScreen.removeFromCanvas();

        switch(newScreen) {
            case SHOWING_MENU -> {
                if (activeGame != null) {
                    activeGame = null;
                }

                activeScreen = new MenuScreen(this::requestScreenChange);
            }
            case SHOWING_INTRO_SCREEN -> {
                if (activeGame != null) {
                    activeGame = null;
                }
                activeScreen = new IntroStoryScreen(this::requestScreenChange);
            }
            case PLAYING -> {
                if (activeGame == null) {
                    activeGame = new PlayGameScreen(this::requestScreenChange);
                }

                setPaused(false);
                activeScreen = activeGame;
            }
            case SHOWING_GAME_OVER -> {
                assert activeGame != null;
                activeScreen = new GameOverScreen(this::requestScreenChange, activeGame.gameState());
                activeGame = null;
            }
            case SHOWING_PAUSE -> {
                assert activeScreen != null;
                setPaused(true);
                activeScreen = new PauseScreen(this::requestScreenChange);
            }
        }

        activeScreen.addToCanvas(this);
    }
}

