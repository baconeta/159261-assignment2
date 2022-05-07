package space_resistance.ui.components;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import tengine.graphics.entities.text.TLabel;

public class Button extends TLabel {
    public enum State {
        FOCUSSED, UNFOCUSED
    }

    public Button(String text) {
        super(text);
        setState(State.UNFOCUSED);
        setFont(FontBook.shared().defaultFont());
    }

    public void setState(State state) {
        setColor(
            switch(state) {
                case FOCUSSED -> Colors.Button.FOCUSSED;
                case UNFOCUSED -> Colors.Button.PRIMARY;
            }
        );
    }
}
