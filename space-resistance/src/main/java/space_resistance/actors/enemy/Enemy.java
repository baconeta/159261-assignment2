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
    private final TVector DIRECTION = new TVector(0, 1);

    // Bullet System Static Constants
    private static final int BARRAGE_CD_MIN = 400;
    private static final int BARRAGE_CD_MAX = 1600;
    private static final int BASE_TIME_BETWEEN_BULLETS = 100;

    protected Dimension dimension;
    protected int health;
    protected int scoreWorth;
    protected final int level;
    public EnemyType type;

    // Bullet System
    private int bulletsThisBarrage = 0;
    private int bulletsPerBarrageMin = 3;
    private int bulletsPerBarrageMax = 8;
    private int barrageCoolDown;
    private long lastBarrageTime;
    private long lastBulletFired;

    public Enemy(EnemyType type, TPoint origin, int level) {
        this.type = type;
        this.level = level;
        this.dimension = EnemyConstants.dimension(type);
        this.scoreWorth = EnemyConstants.scoreValue(type);
        this.health = EnemyConstants.initialHealth(type, level);

        destroyWhenOffScreen = true;

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    private TGraphicCompound initSprite() {
        var sprite = new TGraphicCompound(dimension);
        var ship = EnemyShip.shipFor(type);
        sprite.add(ship);

        // TODO: Remove before submitting, replace this whole method with simple call to EnemyShip.shipFor()
        if (Game.DEBUG_MODE) {
            TRect debugRect = new TRect(EnemyConstants.collisionShapeDimension(type));
            var collisionOffset = new TPoint(
                    (dimension.width - debugRect.dimension().width) * 0.5,
                    (dimension.height - debugRect.dimension().height) * 0.5
            );
            debugRect.setOrigin(collisionOffset);
            debugRect.outlineColor = Color.RED;
            sprite.add(debugRect);
        }

        return sprite;
    }

    public void spawnInWorld(GameWorld world) {
        world.add(this);
    }

    protected TPhysicsComponent initPhysics() {
        // Movement
        boolean isStatic = false;
        velocity = new TVelocity(EnemyConstants.speed(type, level), DIRECTION);

        // Collisions
        boolean hasCollisions = true;
        var collisionDimension = EnemyConstants.collisionShapeDimension(type);
        var collisionOffset = new TPoint(
                (dimension.width - collisionDimension.width) * 0.5,
                (dimension.height - collisionDimension.height) * 0.5
        );
        var collisionRect = new CollisionRect(collisionOffset, collisionDimension);

        var physics = new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
        physics.setCollisionShapeOffset(collisionOffset);

        return physics;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (bulletsThisBarrage <= 0) {
            bulletsThisBarrage = RANDOM.nextInt(bulletsPerBarrageMin, bulletsPerBarrageMax);
            barrageCoolDown = RANDOM.nextInt(BARRAGE_CD_MIN, BARRAGE_CD_MAX);
            lastBarrageTime = currentTime;
        }

        if (currentTime > lastBarrageTime + barrageCoolDown) {
            // we can start next barrage of bullets
            if (currentTime > lastBulletFired + (BASE_TIME_BETWEEN_BULLETS - level * 2L)) {
                TPoint bulletOffset = EnemyConstants.bulletSpawnOffset(type);
                var bullet = new EnemyBullet(
                    type,
                    new TPoint(this.origin.x + bulletOffset.x, this.origin.y + bulletOffset.y)
                );
                world.add(bullet);

                bulletsThisBarrage -= 1;
                lastBulletFired = currentTime;
            }
        }
    }

    public int scoreValue() {
        // Ensure we only add the score one time regardless of number of collisions before destruction frame
        int scoreToAdd = scoreWorth;
        scoreWorth = 0;

        return scoreToAdd;
    }

    public boolean takeDamage(int damageToTake) {
        health -= damageToTake;

        return health <= 0;
    }

    public void setBulletsPerBarrageMin(int bulletsPerBarrageMin){
        this.bulletsPerBarrageMin = bulletsPerBarrageMin;
    }

    public void setBulletsPerBarrageMax(int bulletsPerBarrageMax){
        this.bulletsPerBarrageMax = bulletsPerBarrageMax;
    }

    public int bulletsPerBarrageMin(){
        return bulletsPerBarrageMin;
    }

    public int bulletsPerBarrageMax(){
        return bulletsPerBarrageMax;
    }

    public EnemyType type() {
        return type;
    }

    public boolean dead() {
        return pendingKill;
    }
}
