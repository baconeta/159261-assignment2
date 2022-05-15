package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;

import java.awt.*;

public class GoliathEnemy extends Enemy {

    @Override
    public Enemy spawnAt(GameWorld world, Point origin) {
        return null;
    }

    public GoliathEnemy(GameWorld world, Point origin){
        super(world, origin);
    }
}
