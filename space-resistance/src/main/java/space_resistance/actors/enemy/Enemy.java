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
    protected int health;
    protected int scoreWorth;
    protected final int level;
    public EnemyType type;

    // Bullet system
    private int bulletsThisBarrage = 0;
    private int bulletsPerBarrageMin = 3;
    private int bulletsPerBarrageMax = 8;
    private static final int BARRAGE_CD_MIN = 400;
    private static final int BARRAGE_CD_MAX = 1600;
    private static final int BASE_TIME_BETWEEN_BULLETS = 100;
    private int barrageCooldown;
    private long lastBarrageTime;
    private long lastBulletFired;

    public Enemy(EnemyType type, TPoint origin, Dimension dimension, int level) {
        this.type = type;
        this.dimension = dimension;
        this.scoreWorth = EnemyType.scoreValue(type);
        this.level = level;
        this.health = EnemyType.enemyHealth(type, level);

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
        velocity = new TVelocity(EnemyType.enemySpeed(type, level), new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (bulletsThisBarrage <= 0) {
            bulletsThisBarrage = RANDOM.nextInt(bulletsPerBarrageMin, bulletsPerBarrageMax);
            barrageCooldown = RANDOM.nextInt(BARRAGE_CD_MIN, BARRAGE_CD_MAX);
            lastBarrageTime = currentTime;
        }

        if (currentTime > lastBarrageTime + barrageCooldown) {
            // we can start next barrage of bullets
            if (currentTime > lastBulletFired + (BASE_TIME_BETWEEN_BULLETS - level * 2L)) {
                TPoint bulletOffset = EnemyType.enemyBulletSpawnOffset(type);
                var bullet = new EnemyBullet(type, new TPoint(
                        this.origin.x + bulletOffset.x,
                        this.origin.y + bulletOffset.y));
                world.add(bullet);
                bulletsThisBarrage -= 1;
                lastBulletFired = currentTime;
            }
        }
    }

    public int scoreValue() {
        // Ensures we only add the score one time regardless of number of collisions before destruction frame
        int scoreToAdd = scoreWorth;
        scoreWorth = 0;
        return scoreToAdd;
    }

    // lets this enemy take damage and returns whether the enemy died ?
    public boolean takeDamage(int damageToTake) {
        health -= damageToTake;
        return health <= 0;
    }

    @Override
    public void destroy() {
        super.destroy();
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
}