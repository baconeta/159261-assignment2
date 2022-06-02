package space_resistance.assets;

import java.awt.*;

public class Colors {
    // Colours chosen using a complementary colour picker only and matched with graphic colours
    private static final Color GRASSHOPPER_GREEN = new Color(188, 196, 14);
    public static final Color SPACESHIP_PURPLE = new Color(84, 47, 80);
    public static final Color BEAU_BLUE = new Color(200, 218, 226);
    public static final Color DARK_VIOLET = new Color(114, 14, 196);
    public static final Color DENIM = new Color(14, 96, 196);
    public static final Color TERRIFYING_TEAL = new Color(79, 144, 166);
    public static final Color VIOLENT_PINK = new Color(239, 0, 255);


    public static class Text {
        public static final Color PRIMARY = BEAU_BLUE;
        public static final Color DEV_NAMES = DENIM;
        public static final Color SHIELD_ENABLED = GRASSHOPPER_GREEN;
        public static final Color PLAYER_DEAD = SPACESHIP_PURPLE;
        public static final Color PLAYER_ONE_SCORE = DARK_VIOLET;
        public static final Color PLAYER_TWO_SCORE = TERRIFYING_TEAL;
    }

    public static class Button {
        // Primary color for button elements which are not selected/active
        public static final Color PRIMARY = BEAU_BLUE;

        // Color for selected/active button elements
        public static final Color FOCUSSED = GRASSHOPPER_GREEN;
    }
}
