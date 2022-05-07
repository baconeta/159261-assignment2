package space_resistance.ui.screens.menu;

import tengine.graphics.components.TGraphicCompound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public abstract class Menu extends TGraphicCompound {
    final Consumer<SubmenuOption> submenuSelectionNotifier;

    public Menu(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(new Dimension());

        this.submenuSelectionNotifier = submenuSelectionNotifier;
    }

    abstract public void handleKeyEvent(KeyEvent keyEvent);
}
