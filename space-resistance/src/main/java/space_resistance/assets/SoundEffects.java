package space_resistance.assets;

import tengine.audio.AudioClip;

public class SoundEffects {
    private static final String GAME_OVER = "game-over.wav";
    private static final AudioClip gameOver = new AudioClip(AssetLoader.load(GAME_OVER));

    private static final String BACKGROUND_MUSIC = "background.wav";
    private static final AudioClip background = new AudioClip(AssetLoader.load(BACKGROUND_MUSIC));

    private static final String MENU_MOVE = "menu.wav";
    private static final AudioClip menuMove = new AudioClip(AssetLoader.load(MENU_MOVE));

    private static final String MENU_SELECT = "menu-select.wav";
    private static final AudioClip menuSelect = new AudioClip(AssetLoader.load(MENU_SELECT));

    private static final String DEFAULT_SHOT = "PlayerShootingSound.wav";
    private static final AudioClip playerShootingSound = new AudioClip(AssetLoader.load(DEFAULT_SHOT));

    private static final String EXPLOSION_SOUND = "ExplosionSoundEffect.wav";
    private static final AudioClip explosionSound = new AudioClip(AssetLoader.load(EXPLOSION_SOUND));

    private static final String GOLIATH_EXPLOSION_SOUND = "GoliathExplosionSoundEffect.wav";
    private static final AudioClip goliathExplosionSound = new AudioClip(AssetLoader.load(GOLIATH_EXPLOSION_SOUND));

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

    public AudioClip shoot() {
        return playerShootingSound;
    }

    public AudioClip explosionSound() {
        return explosionSound;
    }

    public AudioClip goliathExplosionSound() {
        return goliathExplosionSound;
    }
}
