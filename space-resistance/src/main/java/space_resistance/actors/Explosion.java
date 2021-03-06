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
    private static final int VOLUME = 5;
    private static final double SCALE = 0.125;
    private static final double GOLIATH_SCALE = 0.3;

    public Explosion(GameWorld world, TPoint origin, EnemyType type) {
        this.world = world;

        if (type == EnemyType.GOLIATH) {
            SoundEffects.shared().goliathExplosionSound().play(VOLUME);
        } else {
            SoundEffects.shared().explosionSound().play(VOLUME);
        }

        graphic = ExplosionSprite.sprite();
        graphic.setScale((type == EnemyType.GOLIATH) ? GOLIATH_SCALE : SCALE);

        physics = initPhysics();
        ((ExplosionSprite) graphic).setSequenceEndCallback(this::onExplosionEnd);

        setOrigin(origin);
    }

    private void onExplosionEnd(SpriteSequence sequence) {
        ((ExplosionSprite) graphic).resetAnimation();
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
