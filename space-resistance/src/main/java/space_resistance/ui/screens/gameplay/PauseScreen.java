package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.Background;
import space_resistance.game.Game;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class PauseScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final TGraphicCompound graphic;

    private final ButtonGroup buttonGroup;
    private final Button resume;
    private final Button quit;

    public PauseScreen(Consumer<ScreenIdentifier> screenChangeCallback) {
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("Game Paused");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().bodyFont());
        title.setOrigin(new TPoint(240, 300));

        // Buttons
        resume = new Button("RESUME");
        resume.setOrigin(new TPoint(80, 490));

        quit = new Button("QUIT TO MENU");
        quit.setOrigin(new TPoint(470, 490));

        buttonGroup = new ButtonGroup(resume, quit);

        // Graphic
        graphic = new TGraphicCompound(Game.WINDOW_DIMENSION);
        graphic.addAll(Background.staticBackground(), title, resume, quit);
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> buttonGroup.previous();
            case KeyEvent.VK_RIGHT -> buttonGroup.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                if (buttonGroup.getFocussed() == resume) {
                    SoundEffects.shared().backgroundMusic().playOnLoop();
                    screenChangeCallback.accept(ScreenIdentifier.PLAYING);
                } else if (buttonGroup.getFocussed() == quit) {
                    screenChangeCallback.accept(ScreenIdentifier.SHOWING_MENU);
                }
            }
        }
    }

    @Override
    public void addToCanvas(Game game) {
        game.graphicsEngine().add(graphic);
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
    public void handleKeyReleased(KeyEvent event) {
        // No-op
    }

    @Override
    public void update(double dtMillis) {
        // No-op
    }
}
