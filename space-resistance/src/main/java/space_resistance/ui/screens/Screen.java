package space_resistance.ui.screens;

import space_resistance.game.Game;

import java.awt.event.KeyEvent;

public interface Screen {
    void handleKeyPressed(KeyEvent event);
    void handleKeyReleased(KeyEvent event);
    void addToCanvas(Game game);
    void removeFromCanvas();
    ScreenIdentifier screen();
    void update(double dtMillis);
}
