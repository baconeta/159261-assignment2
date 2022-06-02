package space_resistance.settings;

// Support for future updates of game difficulty. It presumes there is a menu button
// that toggles the game mode, for instance between normal and hard.
public enum GameMode {
    NORMAL, HARD;

    public GameMode toggle() {
        return switch(this) {
            case NORMAL -> HARD;
            case HARD -> NORMAL;
        };
    }
}
