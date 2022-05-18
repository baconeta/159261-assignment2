package space_resistance.actors.enemy;

import space_resistance.actors.Explosion;
import space_resistance.actors.bullet.Bullet;
import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.animated_sprites.PlayerThruster;
import space_resistance.assets.sprites.EnemyShip;
import space_resistance.assets.sprites.PlayerShip;
import space_resistance.game.GameWorld;
import space_resistance.player.Player;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.graphics.components.sprites.Sprite;
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
    protected boolean contributed = false;
    protected EnemyType type;

    private ArrayList<EnemyBullet> bullets = new ArrayList<EnemyBullet>();

    public Enemy(EnemyType type, GameWorld world, TPoint origin, Dimension dimension, int scoreWorth) {
        this.world = world;
        this.type = type;
        this.dimension = dimension;
        this.scoreWorth = scoreWorth;
        destroyWhenOffScreen = true;
        graphic = initSprite();
        setOrigin(origin);
    }
    private TGraphicCompound initSprite() {
        // Player Ship
        TGraphicCompound enemySprite = new TGraphicCompound(dimension);
        EnemyShip enemy = new EnemyShip(type, this.dimension);
        enemySprite.add(enemy);
        return enemySprite;
    }
    public void spawnInWorld() {
        physics = initPhysics();
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
        if (health <= 0) {
            this.destroy();
            return;
        }

        for (EnemyBullet bullet : bullets) {
            bullet.update();
        }
        if (bullets.size() < (Math.random() * 25)) {
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
                    > RANDOM.nextInt(2100 - 1300)
                    + 1300) { // Shoot in bursts so that player isn't bombarded with constant shots from
                // the enemy ship
                for (EnemyBullet bullet : bullets) {
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }

    public int scoreValue() { return scoreWorth; }

    public int healthRemaining() { return health; }

    public void takeDamage(int damageToDeal) {
        health -= damageToDeal;
        if (health <= 0) {
            this.destroy();
        }
    }
    public void collision(Bullet actorB, Player player, int scoreContribution){
        takeDamage(actorB.damageToDeal());
        if (health <= 0 && !contributed){
            contributed = true;
            player.increaseScore(scoreContribution);
        }
    }
    @Override
    public void destroy() {
        health = 0; // This can be temporary but helps the Spawner detect dead enemies
        Explosion e = new Explosion(world, this.origin);
        velocity.setSpeed(10000); // Temporary fix for invisible enemies being destroyed again
        super.destroy();
    }
}
