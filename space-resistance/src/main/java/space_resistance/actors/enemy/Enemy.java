package space_resistance.actors.enemy;

import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.assets.sprites.EnemyShip;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Actor {
    protected Dimension dimension = new Dimension(72, 72);

    protected static String SHIP_SPRITE = null;

    protected final GameWorld world;

    protected int health = 100;
    protected int scoreWorth = 0;

    protected EnemyType type;

    private ArrayList<EnemyBullet> bullets = new ArrayList<EnemyBullet>();

    public Enemy(EnemyType type, GameWorld world, TPoint origin, Dimension dimension, int scoreWorth) {
        this.world = world;
        this.type = type;
        this.dimension = dimension;
        this.scoreWorth = scoreWorth;
        graphic = new EnemyShip(type, this.dimension);
        physics = initPhysics();
        setOrigin(origin);
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
        if (health < 0){
            this.destroy();
        }
        for (EnemyBullet bullet : bullets) {
            bullet.update();
        }
        if (bullets.size() < (Math.random() * 40 + 20)) {
            if (bullets.size() >= 1) {
                // Delay shots
                if (bullets.get(bullets.size() - 1).timeExisted() > 0) {
                    bullets.add(new EnemyBullet(type, this.world, new TPoint(this.origin.x, this.origin.y + 30)));
                }
            } else {
                bullets.add(new EnemyBullet(type, this.world, new TPoint(this.origin.x, this.origin.y + 30)));
            }
        } else {
            final Random RANDOM = new Random();
            if (bullets.get(bullets.size() - 1).timeExisted()
                    > RANDOM.nextInt(1200 - 300)
                    + 300) { // Shoot in bursts so that player isn't bombarded with constant shots from
                // the enemy ship
                for (EnemyBullet bullet : bullets) {
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }
}
