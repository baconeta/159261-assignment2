package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;
import tengine.geom.TPoint;

public class GoliathEnemy extends Enemy {

    public GoliathEnemy(GameWorld world, TPoint origin) {
        super(world, origin);
    }

    @Override
    public Enemy spawnAt(GameWorld world, TPoint origin) {
        return null;
    }
}
