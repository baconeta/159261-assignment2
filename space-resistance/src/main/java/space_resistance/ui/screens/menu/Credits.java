package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.ui.components.Button;
import tengine.graphics.components.text.TLabel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class Credits extends Menu {
    public Credits(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("credits");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new Point(200, 180));

        initContent();

        Button close = new Button("close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new Point(290, 490));

        addAll(title, close);
    }

    public void initContent() {
        // Our text drawing functions don't currently support
        // drawing multiline strings, hence the following ugliness.
        TLabel originalArtAndSounds1 = bodyText("Original artwork and sound effects");
        TLabel originalArtAndSounds2 = bodyText("by Ali Soltanian Fard Jahromi");
        originalArtAndSounds1.setOrigin(new Point(105, 280));
        originalArtAndSounds2.setOrigin(new Point(140, 300));

        TLabel fontAttr = bodyText("Revamped Font by Chequered Ink");
        fontAttr.setOrigin(new Point(125, 330));

        addAll(originalArtAndSounds1, originalArtAndSounds2, fontAttr);
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
