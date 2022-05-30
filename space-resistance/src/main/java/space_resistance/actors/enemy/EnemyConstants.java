package space_resistance.actors.enemy;

import tengine.geom.TPoint;

import java.awt.*;

public class EnemyConstants {
    public static Dimension dimension(EnemyType type) {
        return switch (type) {
            case MITE, GRASSHOPPER, TARANTULA -> new Dimension(72, 72);
            case GOLIATH                      -> new Dimension(144, 144);
        };
    }

    public static Dimension collisionShapeDimension(EnemyType type) {
        var spriteDim = dimension(type);
        return switch (type) {
            case MITE        -> new Dimension((int) (spriteDim.width * 0.7), (int) (spriteDim.height * 0.3));
            case GRASSHOPPER -> new Dimension((int) (spriteDim.width * 0.7), (int)(spriteDim.height * 0.4));
            case TARANTULA   -> new Dimension((int) (spriteDim.width * 0.6), (int) (spriteDim.height * 0.6));
            case GOLIATH     -> new Dimension((int) (spriteDim.width * 0.7), (int) (spriteDim.height * 0.6));
        };
    }

    public static int scoreValue(EnemyType type) {
        return switch (type) {
            case MITE        -> 100;
            case GRASSHOPPER -> 300;
            case TARANTULA   -> 500;
            case GOLIATH     -> 1000;
        };
    }

    public static int enemyHealth(EnemyType type, int level) {
        return switch (type) {
            case MITE        -> 50 + (level * 25);
            case GRASSHOPPER -> 100 + (level * 40);
            case TARANTULA   -> 200 + (level * 60);
            case GOLIATH     -> 1000 + (level * 500);
        };
    }

    public static int enemySpeed(EnemyType type, int level) {
        return switch (type) {
            case MITE        -> 25 + (level * 5);
            case GRASSHOPPER -> 30 + (level * 10);
            case TARANTULA   -> 35 + (level * 10);
            case GOLIATH     -> 35 + (level * 5);
        };
    }

    public static int bulletSpeed(EnemyType type) {
        return switch (type) {
            case MITE        -> 400;
            case GRASSHOPPER -> 450;
            case TARANTULA   -> 500;
            case GOLIATH     -> 600;
        };
    }

    public static int bulletDamage(EnemyType type) {
        return switch (type) {
            case MITE                   -> 1;
            case GRASSHOPPER, TARANTULA -> 2;
            case GOLIATH                -> 3;
        };
    }
}
