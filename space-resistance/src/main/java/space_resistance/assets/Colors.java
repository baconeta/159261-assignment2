package space_resistance.assets;

import java.awt.*;

public class Colors {
    // TODO: Remove any unused colors before submitting
    public static final Color MITE_GREEN = new Color(55, 66, 24);
    private static final Color GRASSHOPPER_GREEN = new Color(188, 196, 14);
    public static final Color SPACESHIP_PURPLE = new Color(84, 47, 80);
    public static final Color TARANTULA_GREY = new Color(97, 96, 101);
    public static final Color BEAU_BLUE = new Color(200, 218, 226);
    public static final Color DARK_VIOLET = new Color(114, 14, 196);
    public static final Color DENIM = new Color(14, 96, 196);
    public static final Color TERRIFYING_TEAL = new Color(79, 144, 166);


    // We centrally manage all text colors here, so we can swap out
    // a color once, and it will update everywhere.
    // Tweak these however you like! These are just placeholders.
    public static class Text {
        // Primary color for most UI text elements
        public static final Color PRIMARY = BEAU_BLUE;
        public static final Color SHIELD_ENABLED = GRASSHOPPER_GREEN;
        public static final Color PLAYER_DEAD = DARK_VIOLET;
        public static final Color PLAYER_ONE_SCORE = SPACESHIP_PURPLE;
        public static final Color PLAYER_TWO_SCORE = TERRIFYING_TEAL;

        // Add other categories of font colors here
    }

    // TODO: Remove unnecessary comments before submitting
    // We centrally manage all button colors here, so we can swap out
    // a color once, and it will update everywhere.
    // Tweak these however you like! These are just placeholders.
    public static class Button {
        // Primary color for button elements which are not selected/active
        public static final Color PRIMARY = BEAU_BLUE;

        // Color for selected/active button elements
        public static final Color FOCUSSED = GRASSHOPPER_GREEN;
    }
}
