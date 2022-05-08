package space_resistance.ui.screens;

import java.awt.event.KeyEvent;

public interface Screen {
    void handleKeyPressed(KeyEvent event);
    void handleKeyReleased(KeyEvent event);
    void addToCanvas();
    void removeFromCanvas();
    ScreenIdentifier screen();
    void update(double dtMillis);
}
