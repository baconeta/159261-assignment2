package space_resistance.ui.screens.menu;

import tengine.graphics.components.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public abstract class SubMenu extends TGraphicCompound {
    protected final Consumer<SubmenuOption> submenuSelectionNotifier;

    public SubMenu(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(new Dimension());

        this.submenuSelectionNotifier = submenuSelectionNotifier;
    }

    abstract public void handleKeyEvent(KeyEvent keyEvent);
}
