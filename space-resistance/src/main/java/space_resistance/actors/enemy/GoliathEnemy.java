package space_resistance.actors.enemy;

import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.game.EnemySpawningSystem;
import tengine.Actor;
import tengine.geom.TPoint;

import java.awt.*;

public class GoliathEnemy extends Enemy {
    private static final int maxTravelDistance = 150;
    EnemySpawningSystem enemySpawningSystem;

    public GoliathEnemy(EnemyType type, TPoint origin, Dimension dimension, int level) {
        super(type, origin, dimension, level);
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

        if (origin.y >= maxTravelDistance && velocity.speed() > 0) {
            velocity.setDirectionY(0);
            findPlayerInWorld();
        }
    }

    private void findPlayerInWorld() {
        for (Actor a : world.actors()) {
            if ((a instanceof SpaceShip)) {
                if (a.origin().x - this.origin.x > 7) {
                    velocity.setDirectionX(1);
                } else if ((this.origin.x - a.origin().x > 7)) {
                    velocity.setDirectionX(-1);
                } else {
                    velocity.setDirectionX(0);
                }
            }
        }
    }
}
