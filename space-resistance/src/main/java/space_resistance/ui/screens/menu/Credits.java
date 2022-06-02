package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.ui.components.Button;
import tengine.geom.TPoint;
import tengine.graphics.components.text.TLabel;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class Credits extends SubMenu {
    public Credits(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("credits");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new TPoint(220, 120));

        initContent();

        Button close = new Button("Close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new TPoint(260, 550));

        addAll(title, close);
    }

    public void initContent() {
        TLabel creditsAli = namesText("Ali Soltanian Fard Jahromi:");
        TLabel creditsAliTitle = bodyText("Lead Game and Level Designer");
        TLabel creditsJosh = namesText("Joshua Pearson:");
        TLabel creditsJoshTitle = bodyText("Producer and Project Lead");
        TLabel creditsTessa = namesText("Tessa Power:");
        TLabel creditsTessaTitle = bodyText("Engine and Tools Lead Developer");
        creditsAli.setOrigin(new TPoint(148, 180));
        creditsAliTitle.setOrigin(new TPoint(135, 200));
        creditsJosh.setOrigin(new TPoint(203, 260));
        creditsJoshTitle.setOrigin(new TPoint(155, 280));
        creditsTessa.setOrigin(new TPoint(225, 340));
        creditsTessaTitle.setOrigin(new TPoint(125, 360));

        TLabel fontAttr = bodyText("Revamped Font by Chequered Ink");
        fontAttr.setOrigin(new TPoint(135, 420));

        TLabel assetsText = bodyText("All audio and graphics by Ali");
        assetsText.setOrigin(new TPoint(150, 480));

        addAll(fontAttr, creditsAli, creditsAliTitle, creditsJosh, creditsJoshTitle, creditsTessa, creditsTessaTitle, assetsText);
    }

    private TLabel bodyText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().bodyFont());

        return line;
    }

    private TLabel namesText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.DEV_NAMES);
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
