package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import tengine.geom.TPoint;
import tengine.graphics.components.text.TLabel;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class MainMenu extends SubMenu {
    private final ButtonGroup buttons;
    private final Button onePlayer;
    private final Button twoPlayer;
    private final Button howToPlay;
    private final Button credits;

    public MainMenu(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);
        TLabel title = new TLabel("Space Resistance");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());

        title.setOrigin(new TPoint(100, 230));

        onePlayer = new Button("SINGLE PLAYER");
        onePlayer.setOrigin(new TPoint(200, 350));

        twoPlayer = new Button("MULTIPLAYER");
        twoPlayer.setOrigin(new TPoint(210, 400));

        howToPlay = new Button("HOW TO PLAY");
        howToPlay.setOrigin(new TPoint(213, 450));

        credits = new Button("CREDITS");
        credits.setOrigin(new TPoint(234, 500));

        // The button group manages which button is currently selected and moving between buttons
        buttons = new ButtonGroup(onePlayer, twoPlayer, howToPlay, credits);

        addAll(title, onePlayer, twoPlayer, howToPlay, credits);
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> buttons.previous();
            case KeyEvent.VK_DOWN -> buttons.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                Button focussed = buttons.getFocussed();
                if (focussed.equals(onePlayer)) {
                   submenuSelectionNotifier.accept(SubmenuOption.ONE_PLAYER);
                } else if (focussed.equals(twoPlayer)) {
                    submenuSelectionNotifier.accept(SubmenuOption.TWO_PLAYER);
                } else if (focussed.equals(howToPlay)) {
                    submenuSelectionNotifier.accept(SubmenuOption.HOW_TO_PLAY);
                } else if (focussed.equals(credits)) {
                    submenuSelectionNotifier.accept(SubmenuOption.CREDITS);
                }
            }
        }
    }

    @Override
    public void update(double dtMillis) {
        super.update(dtMillis);
    }
}
