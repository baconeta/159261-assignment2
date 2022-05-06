package space_resistance.settings;

// We use an enum to ensure that the GameConfig has a valid number of players.
// This will support setting the multiplayer mode, for instance, when the user
// selects the two-player button from the main menu in future iterations.
public enum MultiplayerMode {
    SINGLE_PLAYER, MULTIPLAYER
}
