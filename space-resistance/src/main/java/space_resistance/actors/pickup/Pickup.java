package space_resistance.actors.pickup;

import space_resistance.assets.sprites.PickupSprite;
import space_resistance.game.Game;
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
    public static final Dimension DIMENSION = new Dimension(32, 32);
    private static final int SPEED = 27;
    private static final TVector DIRECTION = new TVector(0, 1);

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
        velocity = new TVelocity(SPEED, DIRECTION);

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    private TGraphicObject initSprite() {
        // Pickup Sprite
        var sprite = new TGraphicCompound(dimension);
        sprite.add(PickupSprite.pickupFor(type));

        if (Game.DEBUG_MODE) {
            TRect debugRect = new TRect(dimension);
            debugRect.outlineColor = Color.RED;
            sprite.add(debugRect);
        }

        return sprite;
    }

    public PickupType type() {
        return type;
    }
}
