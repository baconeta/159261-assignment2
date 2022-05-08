package space_resistance.ui.screens.menu;

import space_resistance.game.Game;
import space_resistance.settings.MultiplayerMode;
import space_resistance.settings.Settings;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.graphics.components.TGraphicCompound;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class MenuScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final Game engine;

    private final TGraphicCompound container;

    // The main menu is made up of smaller menus. We must keep track of the currently active menu, so we can swap out
    // content easily and forward key events to the right place.
    private Menu activeMenu;

    private final Menu mainMenu;
    private final Menu howToPlay;
    private final Menu credits;

    public MenuScreen(Game game, Consumer<ScreenIdentifier> screenChangeCallback) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Menus
        mainMenu = new MainMenu(this::onSubmenuSelection);
        howToPlay = new HowToPlay(this::onSubmenuSelection);
        credits = new Credits(this::onSubmenuSelection);

        // Graphic
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
        activeMenu = mainMenu;
        container.add(activeMenu);
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        activeMenu.handleKeyEvent(keyEvent);
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {

    }

    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(container);
    }

    @Override
    public void removeFromCanvas() {
        container.removeFromParent();
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.SHOWING_MENU;
    }

    @Override
    public void update(double dtMillis) {
        container.update(dtMillis);
    }

    private void onSubmenuSelection(SubmenuOption submenuOption) {
        activeMenu.removeFromParent();

        switch(submenuOption) {
            case ONE_PLAYER -> {
                Settings.shared().setPlayerMode(MultiplayerMode.SINGLE_PLAYER);
                screenChangeCallback.accept(ScreenIdentifier.PLAYING);
            }
            case CREDITS -> {
                activeMenu = credits;
                container.add(credits);
            }
            case HOW_TO_PLAY -> {
                activeMenu = howToPlay;
                container.add(howToPlay);
            }
            case CLOSE -> {
                activeMenu = mainMenu;
                container.add(mainMenu);
            }
        }
    }
}
