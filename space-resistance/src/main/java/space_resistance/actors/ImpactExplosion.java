package space_resistance.actors;

import space_resistance.assets.animated_sprites.ImpactExplosionSprite;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVelocity;

public class ImpactExplosion extends Actor {
    public ImpactExplosion(GameWorld world, TPoint origin) {
        this.world = world;
        graphic = ImpactExplosionSprite.sprite();

        ((ImpactExplosionSprite) graphic).setSequenceEndCallback(this::onExplosionEnd);
        physics = initPhysics();

        setOrigin(origin);
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
}
