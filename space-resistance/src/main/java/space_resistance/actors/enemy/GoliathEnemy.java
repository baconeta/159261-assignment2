package space_resistance.actors.enemy;

import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.game.EnemySpawningSystem;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;

import java.awt.*;

public class GoliathEnemy extends Enemy {
  private static final int MAX_TRAVEL_DISTANCE = 150;

  private EnemySpawningSystem enemySpawningSystem;

    public GoliathEnemy(EnemyType type, TPoint origin, Dimension dimension, int level) {
        super(type, origin, dimension, level);
    }

    public void spawnBoss(EnemySpawningSystem ess) {
        enemySpawningSystem = ess;
        super.spawnInWorld(ess.gameWorld());
    }

    @Override
    public void destroy() {
        enemySpawningSystem.bossDestroyed();
        super.destroy();
    }

    @Override
    public void update() {
        super.update();

        if (origin.y >= MAX_TRAVEL_DISTANCE && velocity.speed() > 0) {
            velocity.setDirectionY(0);
            findPlayerInWorld();
        }
    }

    private void findPlayerInWorld() {
        SpaceShip target = ((GameWorld) world).getPlayer();
        if (target == null) { return; }

        double targetMid = target.graphic().width() * 0.5;
        double bossMid = this.graphic().width() * 0.5;

        if ((target.origin().x + targetMid) - (this.origin.x + bossMid) > 10) {
            velocity.setDirectionX(1);
        } else if (((this.origin.x + bossMid) - (target.origin().x + targetMid) > 10)) {
            velocity.setDirectionX(-1);
        } else {
            velocity.setDirectionX(0);
        }
    }
}
