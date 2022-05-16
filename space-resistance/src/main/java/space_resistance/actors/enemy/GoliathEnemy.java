package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;

public class GoliathEnemy extends Enemy {
    public GoliathEnemy(GameWorld world, TPoint origin) {
        super(world, origin);
    }

    @Override
    protected TGraphicCompound initSprite() {
        return null;
    }

    public static Enemy spawnAt(GameWorld world, TPoint origin) {
        return null;
    }
}
