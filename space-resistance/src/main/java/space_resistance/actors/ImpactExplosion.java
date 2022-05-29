package space_resistance.actors;

import space_resistance.assets.animated_sprites.ImpactExplosionSprite;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVelocity;

// TODO: maybe rename this to PlayerExplosion?
public class ImpactExplosion extends Actor {
    private final long startTime;

    // Keeps track of how long explosion has existed
    private long currentTime = 0;

    public ImpactExplosion(GameWorld world, TPoint origin) {
        this.world = world;
        graphic = ImpactExplosionSprite.sprite();

        ((ImpactExplosionSprite) graphic).setSequenceEndCallback(this::onExplosionEnd);
        physics = initPhysics();

        // TODO: Remove if still unused
        startTime = System.currentTimeMillis();

        setOrigin(origin);
    }

    // TODO: check what this is used for, if unused remove
    public void update() {
        currentTime = System.currentTimeMillis();
    }

    private void onExplosionEnd(SpriteSequence sequence) {
        ((ImpactExplosionSprite) graphic).resetAnimation();
        this.markPendingKill();
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = true;
        boolean hasCollisions = false;
        CollisionRect collisionRect = new CollisionRect(origin, graphic().dimension());
        velocity = new TVelocity();

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    // TODO: Remove if still unused
    public long timeExisted(){
        return currentTime - startTime;
    }
}
