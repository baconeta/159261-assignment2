package space_resistance.actors.enemy;

import space_resistance.game.EnemySpawningSystem;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;

import java.awt.*;

public class GoliathEnemy extends Enemy {
  private final int maxTravelDistance = 150;
  EnemySpawningSystem enemySpawningSystem;
  
    public GoliathEnemy(EnemyType type, TPoint origin, Dimension dimension, int scoreWorth) {
        super(type, origin, dimension, scoreWorth);
    }

    public void spawnBoss(EnemySpawningSystem ess) {
        enemySpawningSystem = ess;
        super.spawnInWorld(ess.gameWorld);
    }

    @Override
    public void destroy() {
        enemySpawningSystem.bossDestroyed();
        super.destroy();
    }

    @Override
    public void update() {
        super.update();

        if (this.origin.y >= maxTravelDistance) {
            this.velocity.setSpeed(0);
            // TODO here change the boss to go from left to right instead of towards the player
        }
    }
}
