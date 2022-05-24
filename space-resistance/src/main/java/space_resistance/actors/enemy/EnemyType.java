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

    public static int enemyHealth(EnemyType type, int level) {
        return switch (type) {
            case MITE -> 50 + (level * 25);
            case GRASSHOPPER -> 100 + (level * 40);
            case TARANTULA -> 200 + (level * 60);
            case GOLIATH -> 1000 + (level * 500);
        };
    }

    public static int enemySpeed(EnemyType type, int level) {
        return switch (type) {
            case MITE -> 25 + (level * 5);
            case GRASSHOPPER -> 30 + (level * 10);
            case TARANTULA -> 35 + (level * 10);
            case GOLIATH -> 40;
        };
    }

    public static int bulletSpeed(EnemyType type) {
        return switch (type) {
            case MITE -> 400;
            case GRASSHOPPER -> 450;
            case TARANTULA -> 500;
            case GOLIATH -> 600;
        };
    }

    public static int bulletDamage(EnemyType type) {
        return switch (type) {
            case MITE -> 1;
            case GRASSHOPPER -> 2;
            case TARANTULA -> 2;
            case GOLIATH -> 3;
        };
    }
}
