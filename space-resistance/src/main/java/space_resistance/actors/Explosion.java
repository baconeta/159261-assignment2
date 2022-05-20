package space_resistance.actors;

import space_resistance.assets.animated_sprites.ExplosionSprite;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;
import tengine.world.World;

import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

public class Explosion extends Actor {
    private final long startTime;

    // Keeps track of how long explosion has existed
    private long currentTime = 0;

    public Explosion(GameWorld world, TPoint origin) {
        // SoundEffects.shared().explosionSound.play
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
