package space_resistance.assets.sprites;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class EnemyShot extends Sprite {
    private static final Dimension DIMENSION = new Dimension(12, 12);

    private static final InputStream MITE_ASSET = AssetLoader.load("MiteEnemyShot.png");
    private static final EnemyShot MITE_SHOT = new EnemyShot(EnemyType.MITE);

    private static final InputStream GRASSHOPPER_ASSET = AssetLoader.load("GrasshopperEnemyShot.png");
    private static final EnemyShot GRASSHOPPER_SHOT = new EnemyShot(EnemyType.GRASSHOPPER);

    private static final InputStream TARANTULA_ASSET = AssetLoader.load("TarantulaEnemyShot.png");
    private static final EnemyShot TARANTULA_SHOT = new EnemyShot(EnemyType.TARANTULA);

    private static final InputStream GOLIATH_ASSET = AssetLoader.load("GoliathShots.png"); // TODO temporary
    private static final EnemyShot GOLIATH_SHOT = new EnemyShot(EnemyType.GOLIATH);

    public static EnemyShot shotFor(EnemyType type) {
        return switch(type) {
            case MITE -> MITE_SHOT;
            case GRASSHOPPER -> GRASSHOPPER_SHOT;
            case TARANTULA -> TARANTULA_SHOT;
            case GOLIATH -> GOLIATH_SHOT;
        };
    }

    private EnemyShot(EnemyType type) {
        super(shotSpriteFor(type), DIMENSION);
    }

    private static InputStream shotSpriteFor(EnemyType type) {
        return switch (type) {
            case MITE        -> MITE_ASSET;
            case GRASSHOPPER -> GRASSHOPPER_ASSET;
            case TARANTULA   -> TARANTULA_ASSET;
            case GOLIATH -> GOLIATH_ASSET;
        };
    }
}
