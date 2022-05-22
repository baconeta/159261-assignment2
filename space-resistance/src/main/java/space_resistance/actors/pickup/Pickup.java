package space_resistance.actors.pickup;

import space_resistance.assets.sprites.PickupSprite;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.TGraphicObject;
import tengine.graphics.components.shapes.TRect;

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
        setOrigin(origin);
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
}
