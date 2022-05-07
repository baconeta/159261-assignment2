package space_resistance.settings;

// This is just an example. It presumes there is a menu button
// that toggles the game mode, for instance between easy and hard.
public enum GameMode {
    NORMAL, HARD;

    public GameMode toggle() {
        return switch(this) {
            case NORMAL -> HARD;
            case HARD -> NORMAL;
        };
    }
}
