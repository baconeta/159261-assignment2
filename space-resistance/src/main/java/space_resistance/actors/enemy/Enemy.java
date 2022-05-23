package space_resistance.actors.enemy;

import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.assets.sprites.EnemyShip;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;
import java.util.Random;

public class Enemy extends Actor {
    private static final Random RANDOM = new Random();

    protected Dimension dimension;
    protected int health = 100;
    protected int scoreWorth;
    protected EnemyType type;

    // Bullet system
    private int bulletsThisBarrage = 0;
    private static final int BULLETS_PER_BARRAGE_MIN = 5;
    private static final int BULLETS_PER_BARRAGE_MAX = 20;
    private static final int BARRAGE_CD_MIN = 200;
    private static final int BARRAGE_CD_MAX = 1500;
    private static final int TIME_BETWEEN_BULLETS = 50;
    private int barrageCooldown;
    private long lastBarrageTime;
    private long lastBulletFired;

    public Enemy(EnemyType type, TPoint origin, Dimension dimension, int scoreWorth) {
        this.type = type;
        this.dimension = dimension;
        this.scoreWorth = scoreWorth;

        destroyWhenOffScreen = true;

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound enemySprite = new TGraphicCompound(dimension);
        EnemyShip enemy = EnemyShip.shipFor(type);
        enemySprite.add(enemy);
        if (Game.DEBUG_MODE) {
            enemySprite.add(new TRect(new Dimension((int) (dimension.width * 0.5), (int) (dimension.height * 0.5))));
        }
        return enemySprite;
    }

    public void spawnInWorld(GameWorld world) {
        world.add(this);
    }

    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(50, new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (bulletsThisBarrage <= 0) {
            bulletsThisBarrage = RANDOM.nextInt(BULLETS_PER_BARRAGE_MIN, BULLETS_PER_BARRAGE_MAX);
            barrageCooldown = RANDOM.nextInt(BARRAGE_CD_MIN, BARRAGE_CD_MAX);
            lastBarrageTime = currentTime;
        }

        if (currentTime > lastBarrageTime + barrageCooldown) {
            // we can start next barrage of bullets
            if (currentTime > lastBulletFired + TIME_BETWEEN_BULLETS) {
                var bullet = new EnemyBullet(type, new TPoint(this.origin.x + 30, this.origin.y + 60));
                world.add(bullet);
                bulletsThisBarrage -= 1;
                lastBulletFired = currentTime;
            }
        }
    }

    public int scoreValue() {
        return scoreWorth;
    }

    public void setMaxHealth(int maxHealth) {
        health = maxHealth;
    }

    // lets this enemy take damage and returns whether the enemy died ?
    public boolean takeDamage(int damageToTake) {
        health -= damageToTake;
        return health <= 0;
    }

    @Override
    public void destroy() {
        // FIXME: Temporary fix for invisible enemies being destroyed again
        velocity.setSpeed(10000);
        super.destroy();
    }
}
