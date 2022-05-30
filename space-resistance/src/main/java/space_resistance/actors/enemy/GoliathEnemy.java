package space_resistance.actors.enemy;

import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.game.EnemySpawningSystem;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;

import java.awt.*;

public class GoliathEnemy extends Enemy {
    private static final int MAX_TRAVEL_DISTANCE = 150;
    private static final int BORDER_SPACING = 5;

    private EnemySpawningSystem enemySpawningSystem;

    public GoliathEnemy(EnemyType type, TPoint origin, int level) {
        super(type, origin, level);
    }

    public void spawnBoss(EnemySpawningSystem ess) {
        enemySpawningSystem = ess;
        super.spawnInWorld(ess.gameWorld());
    }

    @Override
    public void destroy() {
        enemySpawningSystem.levelUp();
        super.destroy();
    }

    @Override
    public void update() {
        if (world == null) { return; }
        super.update();

        if (origin.y >= MAX_TRAVEL_DISTANCE && velocity.speed() > 0) {
            velocity.setDirectionY(0);
            findPlayerInWorld();
        }
    }

    private void findPlayerInWorld() {
        SpaceShip target = ((GameWorld) world).bossTarget();
        if (target == null) {
            return;
        }

        if (this.origin.x + BORDER_SPACING + this.graphic.width() >= world.canvas().width()
                || (this.origin.x <= BORDER_SPACING)) {
            velocity.invertXDirection();
            return;
        }

        chaseTarget(target);
    }

    private void chaseTarget(SpaceShip target) {
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
