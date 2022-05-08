package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.settings.Settings;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import tengine.graphics.components.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class MainMenu extends Menu {
    private final ButtonGroup buttons;
    private final Button onePlayer;
    private final Button howToPlay;
    private final Button credits;

    public MainMenu(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);
        TLabel title = new TLabel("Space Resistance");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());

        // The origin of text is unfortunately manual as we cannot query
        // the size of the text beforehand to properly align it
        title.setOrigin(new Point(100, 180));

        onePlayer = new Button("ONE PLAYER");
        onePlayer.setOrigin(new Point(240, 300));

        howToPlay = new Button("HOW TO PLAY");
        howToPlay.setOrigin(new Point(240, 350));

        credits = new Button("CREDITS");
        credits.setOrigin(new Point(250, 400));

        // The button group manages which button is currently selected and moving between buttons
        buttons = new ButtonGroup(onePlayer, howToPlay, credits/*, twoPlayer, highscores*/);

        addAll(title, onePlayer, howToPlay, credits/*, twoPlayer, highscores*/);
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
