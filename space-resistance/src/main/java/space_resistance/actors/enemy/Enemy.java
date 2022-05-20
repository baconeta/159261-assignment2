package space_resistance.actors.enemy;

import space_resistance.actors.Explosion;
import space_resistance.actors.bullet.Bullet;
import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.assets.sprites.EnemyShip;
import space_resistance.assets.sprites.PlayerShip;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import space_resistance.player.Player;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
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
    private static final Random RANDOM = new Random();
    protected final GameWorld world;
    protected Dimension dimension;
    protected int health = 100;
    protected int scoreWorth;
    protected boolean contributed = false;
    protected EnemyType type;

    private boolean isDead;

    private ArrayList<EnemyBullet> bullets = new ArrayList<>();

    public Enemy(
            EnemyType type, GameWorld world, TPoint origin, Dimension dimension, int scoreWorth) {
        this.world = world;
        this.type = type;
        this.dimension = dimension;
        this.scoreWorth = scoreWorth;
        destroyWhenOffScreen = true;
        graphic = initSprite();
        setOrigin(origin);
    }

    private TGraphicCompound initSprite() {
        // Enemy Ship
        TGraphicCompound enemySprite = new TGraphicCompound(dimension);
        EnemyShip enemy = new EnemyShip(type, this.dimension);
        enemySprite.add(enemy);
        if (Game.DEBUG_MODE) {
            enemySprite.add(new TRect(new Dimension((int) (dimension.width * 0.5), (int) (dimension.height * 0.5))));
        }
        return enemySprite;
    }

    public void spawnInWorld() {
        physics = initPhysics();
        world.add(this);
        isDead = false;
    }

    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(50, new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public void update() {
        if (health <= 0 || isDead) {
            return;
        }

        for (EnemyBullet bullet : bullets) {
            bullet.update();
        }
        if (bullets.size() < (Math.random() * 20)) {
            if (bullets.size() >= 1) {
                // Delay shots
                if (bullets.get(bullets.size() - 1).timeExisted() > 0) {
                    bullets.add(
                            new EnemyBullet(type, this.world, new TPoint(this.origin.x, this.origin.y + 30)));
                }
            } else {
                bullets.add(
                        new EnemyBullet(type, this.world, new TPoint(this.origin.x, this.origin.y + 30)));
            }
        } else {
            if (bullets.get(bullets.size() - 1).timeExisted()
                    > RANDOM.nextInt(2400 - 1700)
                    + 1700) { // Shoot in bursts so that player isn't bombarded with constant shots from
                // the enemy ship
                for (EnemyBullet bullet : bullets) {
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }

    public int scoreValue() {
        return scoreWorth;
    }

    public void setMaxHealth(int maxHealth) {
        health = maxHealth;
    }

    public boolean isDead() {
        return isDead;
    }

    public void takeDamage(int damageToDeal) {
        health -= damageToDeal;
        if (health <= 0) {
            Explosion e = new Explosion(world, this.origin);
            isDead = true;
            this.removeFromWorld();
        }
    }

    public void collision(Bullet actorB, Player player, int scoreContribution) {
        if (isDead) { return; }
        takeDamage(actorB.damageToDeal());
        if (health <= 0 && !contributed) {
            contributed = true;
            player.increaseScore(scoreContribution);
        }
    }
}
