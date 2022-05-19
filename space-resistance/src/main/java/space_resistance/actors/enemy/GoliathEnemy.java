package space_resistance.actors.enemy;

import space_resistance.game.EnemySpawningSystem;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;

import java.awt.*;

public class GoliathEnemy extends Enemy {
    EnemySpawningSystem enemySpawningSystem;

    public GoliathEnemy(
            EnemyType type, GameWorld world, TPoint origin, Dimension dimension, int scoreWorth) {
        super(type, world, origin, dimension, scoreWorth);
    }

    public void spawnBoss(EnemySpawningSystem ess) {
        enemySpawningSystem = ess;
        super.spawnInWorld();
    }

    @Override
    public void destroy() {
        enemySpawningSystem.bossDestroyed();
        super.destroy();
    }
}
