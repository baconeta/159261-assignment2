package space_resistance.ui.screens.gameplay;

import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.IntroStory;
import space_resistance.game.Game;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class IntroStoryScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final TGraphicCompound graphic;

    private final ButtonGroup buttonGroup;
    private final Button continueToGame;

    private final IntroStory introStoryAsset = IntroStory.getInstance();

    public IntroStoryScreen(Consumer<ScreenIdentifier> screenChangeCallback) {
        this.screenChangeCallback = screenChangeCallback;

        // Background
        introStoryAsset.setIsStatic(true);

        // Buttons
        continueToGame = new Button("PRESS ENTER TO CONTINUE");
        continueToGame.setOrigin(new TPoint(220, 690));

        buttonGroup = new ButtonGroup(continueToGame);

        // Graphic
        graphic = new TGraphicCompound(Game.WINDOW_DIMENSION);
        graphic.addAll(introStoryAsset, continueToGame);
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> buttonGroup.previous();
            case KeyEvent.VK_RIGHT -> buttonGroup.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                SoundEffects.shared().backgroundMusic().playOnLoop();
                introStoryAsset.setIsStatic(false);
                screenChangeCallback.accept(ScreenIdentifier.PLAYING);
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
