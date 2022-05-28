package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.ui.components.Button;
import tengine.geom.TPoint;
import tengine.graphics.components.text.TLabel;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class HowToPlay extends SubMenu {
    public HowToPlay(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("How to play");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new TPoint(175, 180));

        initContent();

        Button close = new Button("Close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new TPoint(275, 490));

        addAll(title, close);
    }

    public void initContent() {
        TLabel howToPlay                 = defaultText("Single Player: Defeat as many enemies as possible while surviving their attacks.");
        TLabel howToPlayMultiplayer      = defaultText("Multiplayer: Defeat as many enemies as possible to get a higher score than the");
        TLabel howToPlayMultiplayerLine2 = defaultText("                   opposing player and surviving enemy attacks.");
        howToPlay.setOrigin(new TPoint(80, 220));
        howToPlayMultiplayer.setOrigin(new TPoint(80, 240));
        howToPlayMultiplayerLine2.setOrigin(new TPoint(80, 260));

        TLabel onePlayer = bodyText("PLAYER 1");
        TLabel onePlayerKeys = bodyText("Use the arrow keys to move.  Press Enter to shoot.");
        onePlayer.setOrigin(new TPoint(255, 300));
        onePlayerKeys.setOrigin(new TPoint(25, 320));

        TLabel twoPlayer = bodyText("PLAYER 2");
        TLabel twoPlayerKeys = bodyText("Use the W-A-S-D keys to move. Press Shift to fire.");
        twoPlayer.setOrigin(new TPoint(255, 350));
        twoPlayerKeys.setOrigin(new TPoint(30, 370));

        addAll(onePlayer, onePlayerKeys, twoPlayer, twoPlayerKeys, howToPlay, howToPlayMultiplayer, howToPlayMultiplayerLine2);
    }

    private TLabel bodyText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().bodyFont());

        return line;
    }

    private TLabel defaultText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().defaultFont());

        return line;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            SoundEffects.shared().menuSelect().play();
            submenuSelectionNotifier.accept(SubmenuOption.CLOSE);
        }
    }
}
