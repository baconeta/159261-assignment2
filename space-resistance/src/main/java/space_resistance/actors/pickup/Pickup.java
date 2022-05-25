package space_resistance.actors.pickup;

import space_resistance.assets.sprites.PickupSprite;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.TGraphicObject;
import tengine.graphics.components.shapes.TRect;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;

public class Pickup extends Actor {
    // TODO: consider moving to this a utility class
    public static final Dimension DIMENSION = new Dimension(32, 32);
    private final Dimension dimension;
    private final PickupType type;

    public Pickup(PickupType type, TPoint origin) {
        this.type = type;
        this.dimension = DIMENSION;
        destroyWhenOffScreen = true;

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(27, new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    // TODO: Convert this to a simple sprite instead of a TGraphicCompound (unnecessary bloat) before submitting
    private TGraphicObject initSprite() {
        // Pickup Sprite
        TGraphicCompound pickup = new TGraphicCompound(dimension);
        pickup.add(PickupSprite.pickupFor(type));

        // TODO: Remove this before submitting
        if (Game.DEBUG_MODE) {
            pickup.add(new TRect(new Dimension(dimension.width, dimension.height)));
        }

        return pickup;
    }

    public PickupType type() {
        return type;
    }
}
