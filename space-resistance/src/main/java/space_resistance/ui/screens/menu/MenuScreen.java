package space_resistance.ui.screens.menu;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.Background;
import space_resistance.game.Game;
import space_resistance.settings.MultiplayerMode;
import space_resistance.settings.Settings;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.graphics.components.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class MenuScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final String BACKGROUND = "SpaceBackground.png";
    private static final Dimension DIMENSION = new Dimension(600, 800);
    Background background = new Background(AssetLoader.load(BACKGROUND), DIMENSION);

    private final TGraphicCompound container;

    // The main menu is made up of submenus. We must keep track of the currently active submenu, so we can swap out
    // content easily and forward key events to the right place.
    private SubMenu activeSubMenu;

    private final SubMenu mainMenu;
    private final SubMenu howToPlay;
    private final SubMenu credits;

    public MenuScreen(Consumer<ScreenIdentifier> screenChangeCallback) {
        this.screenChangeCallback = screenChangeCallback;

        // Menus
        mainMenu = new MainMenu(this::onSubmenuSelection);
        howToPlay = new HowToPlay(this::onSubmenuSelection);
        credits = new Credits(this::onSubmenuSelection);

        // Graphic
        container = new TGraphicCompound(Game.WINDOW_DIMENSION);
        activeSubMenu = mainMenu;
        container.add(background);
        container.add(activeSubMenu);
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        activeSubMenu.handleKeyEvent(keyEvent);
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        // no-op
    }

    @Override
    public void addToCanvas(Game game) {
        game.graphicsEngine().add(container);
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
        activeSubMenu.removeFromParent();

        switch(submenuOption) {
            case ONE_PLAYER -> {
                Settings.shared().setPlayerMode(MultiplayerMode.SINGLE_PLAYER);
                screenChangeCallback.accept(ScreenIdentifier.PLAYING);
            }
            case CREDITS -> {
                activeSubMenu = credits;
                container.add(credits);
            }
            case HOW_TO_PLAY -> {
                activeSubMenu = howToPlay;
                container.add(howToPlay);
            }
            case CLOSE -> {
                activeSubMenu = mainMenu;
                container.add(mainMenu);
            }
        }
    }
}
