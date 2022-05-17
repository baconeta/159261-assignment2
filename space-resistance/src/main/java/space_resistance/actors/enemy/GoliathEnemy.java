package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;

import java.awt.*;

public class GoliathEnemy extends Enemy {
    public GoliathEnemy(EnemyType type, GameWorld world, TPoint origin, Dimension dimension, int scoreWorth) {
        super(type, world, origin, dimension, scoreWorth);
    }
}
