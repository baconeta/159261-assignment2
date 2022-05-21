package space_resistance.actors.enemy;

import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.actors.bullet.PlayerBullet;
import space_resistance.assets.sprites.EnemyShip;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import space_resistance.player.Player;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Actor {
    private static final Random RANDOM = new Random();
    protected Dimension dimension;
    protected int health = 100;
    protected int scoreWorth;
    protected boolean contributed = false;
    protected EnemyType type;

    private boolean isDead;
    private final List<EnemyBullet> bullets = new ArrayList<>();

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
        // TODO: Can we make this work by creating bullets in short bursts given a time interval?
        if (bullets.size() < (Math.random() * 20)) {
            if (bullets.size() >= 0) {
                // Delay shots
//                if (bullets.get(bullets.size() - 1).timeExisted() > 0) {
                    var bullet = new EnemyBullet(type, new TPoint(this.origin.x, this.origin.y + 30));
                    bullets.add(bullet);
                    world.add(bullet);
//                }
            } else {
                var bullet = new EnemyBullet(type, new TPoint(this.origin.x, this.origin.y + 30));
                bullets.add(bullet);
                world.add(bullet);
            }
        } else {
            // Shoot in bursts so that player isn't bombarded with constant shots from the enemy ship
            if (bullets.get(bullets.size() - 1).timeExisted() > RANDOM.nextInt(2400 - 1700) + 1700) {
//                bullets.forEach(EnemyBullet::removeFromWorld);
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

    public void takeDamage(int damageToTake) {
        health -= damageToTake;
        if (health <= 0) {
            // Consider moving this into the game world
            this.removeFromWorld();
            bullets.clear();
        }
    }

    public void collision(PlayerBullet bullet, Player player, int scoreContribution) {
        takeDamage(bullet.damageToDeal());
        if (health <= 0 && !contributed){
            contributed = true;
            // TODO: move adding score into GameWorld, an Enemy shouldn't be calling public methods on a player
            player.increaseScore(scoreContribution);
        }
    }

    @Override
    public void destroy() {
        isDead = true;
        // FIXME: Temporary fix for invisible enemies being destroyed again
        velocity.setSpeed(10000);
        super.destroy();
    }
}
