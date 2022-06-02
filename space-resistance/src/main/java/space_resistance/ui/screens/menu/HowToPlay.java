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
        title.setOrigin(new TPoint(175, 120));

        initContent();

        Button close = new Button("Close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new TPoint(260, 650));

        addAll(title, close);
    }

    public void initContent() {
        TLabel howToPlay = bodyText("Single Player:\nDefeat as many enemies as possible while surviving\nthrough their attacks.");
        TLabel howToPlayMultiplayer = bodyText("Multiplayer:\nDefeat as many enemies as possible to get a higher score\nthan the opposing player while surviving enemy attacks.");
        howToPlay.setOrigin(new TPoint(20, 230));
        howToPlayMultiplayer.setOrigin(new TPoint(20, 300));
        howToPlay.setFontSize(14);
        howToPlayMultiplayer.setFontSize(14);

        TLabel onePlayer = bodyText("PLAYER 1");
        TLabel onePlayerKeys = bodyText("Use the arrow keys to move.  Press Enter to shoot.");
        onePlayer.setOrigin(new TPoint(255, 380));
        onePlayerKeys.setOrigin(new TPoint(25, 400));
        onePlayer.setColor(Colors.DARK_VIOLET);

        TLabel twoPlayer = bodyText("PLAYER 2");
        TLabel twoPlayerKeys = bodyText("Use the W-A-S-D keys to move. Press Shift to shoot.");
        twoPlayer.setOrigin(new TPoint(255, 450));
        twoPlayerKeys.setOrigin(new TPoint(30, 470));
        twoPlayer.setColor(Colors.TERRIFYING_TEAL);

        addAll(onePlayer, onePlayerKeys, twoPlayer, twoPlayerKeys, howToPlay, howToPlayMultiplayer);
    }

    private TLabel bodyText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().bodyFont());

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
