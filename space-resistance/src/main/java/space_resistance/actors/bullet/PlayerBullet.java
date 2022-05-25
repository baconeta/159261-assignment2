package space_resistance.actors.bullet;

import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.DefaultShot;
import space_resistance.game.Game;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;

public class PlayerBullet extends Bullet {
    private static final Dimension DIMENSION = new Dimension(5, 13);
    private static final int DAMAGE = 5;

    public PlayerBullet(GameWorld world, TPoint origin) {
        super(origin);
        SoundEffects.shared().defaultPlayerShootingSound().play(5);

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    @Override
    protected TGraphicCompound initSprite() {
        var sprite = new TGraphicCompound(dimension());
        sprite.add(DefaultShot.fetchSprite());
        if (Game.DEBUG_MODE) { sprite.add(new TRect(dimension())); }

        return sprite;
    }

    @Override
    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(500, new TVector(0, -1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    @Override
    protected Dimension dimension() {
        return DIMENSION;
    }

    public int damageToDeal() { return DAMAGE; }
}
