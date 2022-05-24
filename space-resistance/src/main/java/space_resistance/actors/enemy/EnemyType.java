package space_resistance.actors.enemy;

import tengine.geom.TPoint;

import java.awt.*;

public enum EnemyType {
    MITE, GRASSHOPPER, TARANTULA, GOLIATH;

    public static Dimension enemyBulletDimension(EnemyType type) {
        return switch (type) {
            case MITE -> new Dimension(4, 40);
            case GRASSHOPPER -> new Dimension(45, 40);
            case TARANTULA -> new Dimension(20, 40);
            case GOLIATH -> new Dimension(60, 40);
        };
    }

    public static TPoint enemyBulletSpawnOffset(EnemyType type) {
        return switch (type) {
            case MITE -> new TPoint(35, 55);
            case GRASSHOPPER -> new TPoint(15, 40);
            case TARANTULA -> new TPoint(26, 60);
            case GOLIATH -> new TPoint(45, 130);
        };
    }

    public static int scoreValue(EnemyType type) {
        return switch (type) {
            case MITE -> 100;
            case GRASSHOPPER -> 300;
            case TARANTULA -> 500;
            case GOLIATH -> 1000;
        };
    }
}
