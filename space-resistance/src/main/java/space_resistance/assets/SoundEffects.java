package space_resistance.assets;

import tengine.audio.AudioClip;

public class SoundEffects {
    // TODO: replace these with Ali's sound effects
    private static final String GAME_OVER = "game-over.wav";
    private static final AudioClip gameOver = new AudioClip(AssetLoader.load(GAME_OVER));

    private static final String BACKGROUND_MUSIC = "background.wav";
    private static final AudioClip background = new AudioClip(AssetLoader.load(BACKGROUND_MUSIC));

    private static final String MENU_MOVE = "menu.wav";
    private static final AudioClip menuMove = new AudioClip(AssetLoader.load(MENU_MOVE));

    private static final String MENU_SELECT = "menu-select.wav";
    private static final AudioClip menuSelect = new AudioClip(AssetLoader.load(MENU_SELECT));

    private static SoundEffects singleton = null;

    public static SoundEffects shared() {
        if (singleton == null) {
            singleton = new SoundEffects();
        }

        return singleton;
    }

    private SoundEffects() {}

    public AudioClip backgroundMusic() {
        return background;
    }

    public AudioClip gameOver() {
        return gameOver;
    }

    public AudioClip menuMove() {
        return menuMove;
    }

    public AudioClip menuSelect() {
        return menuSelect;
    }
}
