package space_resistance.actors;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.animated_sprites.ExplosionSprite;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVelocity;

public class Explosion extends Actor {
    private final long startTime;

    // Keeps track of how long explosion has existed
    private long currentTime = 0;


    public Explosion(GameWorld world, TPoint origin, EnemyType type) {
        if (type == EnemyType.GOLIATH){
            SoundEffects.shared().goliathExplosionSound().play(5);
        } else {
            SoundEffects.shared().explosionSound().play(5);
        }
        this.world = world;

        graphic = ExplosionSprite.sprite();
        if (type == EnemyType.GOLIATH){
            graphic.setScale(0.3);
        } else {
            graphic.setScale(0.125);
        }
        ((ExplosionSprite) graphic).setSequenceEndCallback(this::onExplosionEnd);
        physics = initPhysics();
        startTime = System.currentTimeMillis();

        setOrigin(origin);
    }

    public Explosion(GameWorld world, TPoint origin) {
        SoundEffects.shared().explosionSound().play(5);
        this.world = world;

        graphic = ExplosionSprite.sprite();
        ((ExplosionSprite) graphic).setSequenceEndCallback(this::onExplosionEnd);
        physics = initPhysics();
        startTime = System.currentTimeMillis();

        setOrigin(origin);
    }

    public void update() {
        currentTime = System.currentTimeMillis();
    }

    private void onExplosionEnd(SpriteSequence sequence) {
        ((ExplosionSprite) graphic).resetAnimation();
        this.removeFromWorld();
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = true;
        boolean hasCollisions = false;
        CollisionRect collisionRect = new CollisionRect(origin, graphic().dimension());
        velocity = new TVelocity();

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public long timeExisted(){
        return currentTime - startTime;
    }
}
