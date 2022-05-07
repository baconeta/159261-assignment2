package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.ui.components.Button;
import tengine.graphics.entities.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class HowToPlay extends Menu {
    public HowToPlay(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("How to play");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new Point(140, 180));

        initContent();

        Button close = new Button("close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new Point(220, 490));

        addAll(title, close);
    }

    public void initContent() {
        TLabel onePlayer = bodyText("PLAYER 1");
        TLabel onePlayerKeys = bodyText("Use the arrow keys to move, press enter to fire");
        onePlayer.setOrigin(new Point(210, 300));
        onePlayerKeys.setOrigin(new Point(90, 320));

        TLabel twoPlayer = bodyText("PLAYER 2");
        TLabel twoPlayerKeys = bodyText("Use the W-A-S-D keys to move, press shift to fire");
        twoPlayer.setOrigin(new Point(210, 350));
        twoPlayerKeys.setOrigin(new Point(80, 370));

        addAll(onePlayer, onePlayerKeys, twoPlayer, twoPlayerKeys);
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
