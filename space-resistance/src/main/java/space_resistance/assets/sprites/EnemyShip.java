package space_resistance.assets.sprites;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class EnemyShip extends Sprite {
    private static final Dimension DIMENSION = new Dimension(72, 72);

    private static final InputStream MITE_ASSET = AssetLoader.load("MiteEnemy.png");
    private static final EnemyShip MITE_SHIP = new EnemyShip(EnemyType.MITE, DIMENSION);

    private static final InputStream GRASSHOPPER_ASSET = AssetLoader.load("GrasshopperEnemy.png");
    private static final EnemyShip GRASSHOPPER_SHIP = new EnemyShip(EnemyType.GRASSHOPPER, DIMENSION);

    private static final InputStream TARANTULA_ASSET = AssetLoader.load("TarantulaEnemy.png");
    private static final EnemyShip TARANTULA_SHIP = new EnemyShip(EnemyType.TARANTULA, DIMENSION);

    private static final InputStream GOLIATH_ASSET = AssetLoader.load("GoliathBlue.png");
    private static final EnemyShip GOLIATH_SHIP = new EnemyShip(EnemyType.GOLIATH, DIMENSION); // TODO make bigger

    public static EnemyShip shipFor(EnemyType type) {
        return switch(type) {
            case MITE -> MITE_SHIP;
            case GRASSHOPPER -> GRASSHOPPER_SHIP;
            case TARANTULA -> TARANTULA_SHIP;
            case GOLIATH -> GOLIATH_SHIP;
        };
    }

    private EnemyShip(EnemyType type, Dimension dimension) {
        super(shipSpriteFor(type), dimension);
    }

    private static InputStream shipSpriteFor(EnemyType type) {
        return switch (type) {
            case MITE -> MITE_ASSET;
            case GRASSHOPPER -> GRASSHOPPER_ASSET;
            case TARANTULA -> TARANTULA_ASSET;
            case GOLIATH -> GOLIATH_ASSET;
        };
    }
}
