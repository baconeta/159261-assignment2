package space_resistance.settings;

import com.google.inject.Singleton;
import space_resistance.game.GameConfig;

@Singleton
public class Settings {
    private static Settings singleton = null;

    private static MultiplayerMode multiplayerMode;
    private static GameMode gameMode;

    public static Settings shared() {
        if (singleton == null) {
            singleton = new Settings();
        }

        return singleton;
    }

    private Settings()  {
        gameMode = GameMode.NORMAL;
        multiplayerMode = MultiplayerMode.SINGLE_PLAYER;
    }

    public GameConfig config() {
        return new GameConfig(Settings.multiplayerMode, Settings.gameMode);
    }

    public void setPlayerMode(MultiplayerMode multiplayerMode) {
        Settings.multiplayerMode = multiplayerMode;
    }
}
