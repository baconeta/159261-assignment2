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
    private final Dimension dimension;
    private final PickupType type;

    public Pickup(PickupType type, GameWorld world, TPoint origin, Dimension dimension) {
        this.world = world;
        this.type = type;
        this.dimension = dimension;
        destroyWhenOffScreen = true;
        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(0, new TVector(0, 0));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    private TGraphicObject initSprite() {
        // Pickup Sprite
        TGraphicCompound pickup = new TGraphicCompound(dimension);
        PickupSprite pickupSprite = new PickupSprite(type, this.dimension);
        pickup.add(pickupSprite);
        if (Game.DEBUG_MODE) {
            pickup.add(
                    new TRect(new Dimension((int) (dimension.width * 0.5), (int) (dimension.height * 0.5))));
        }
        return pickup;
    }

    public PickupType pickupType() { return type; }
}
