package space_resistance.actors.enemy;

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
}
