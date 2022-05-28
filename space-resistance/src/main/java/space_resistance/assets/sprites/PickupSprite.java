package space_resistance.assets.sprites;

import space_resistance.actors.pickup.Pickup;
import space_resistance.actors.pickup.PickupType;
import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class PickupSprite extends Sprite {
    private static final InputStream HEALTH_ASSET = AssetLoader.load("HealthPickup.png");
    private static final PickupSprite HEALTH = new PickupSprite(PickupType.HEALTH, Pickup.DIMENSION);

    private static final InputStream SHIELD_ASSET = AssetLoader.load("ShieldPickup.png");
    private static final PickupSprite SHIELD = new PickupSprite(PickupType.SHIELD, Pickup.DIMENSION);

    private static final InputStream MISSILE_ASSET = AssetLoader.load("MissilePickup.png");
    private static final PickupSprite MISSILE = new PickupSprite(PickupType.MISSILE, Pickup.DIMENSION);

    public static PickupSprite pickupFor(PickupType type) {
        return switch(type) {
            case HEALTH -> HEALTH;
            case SHIELD -> SHIELD;
            case MISSILE -> MISSILE;
        };
    }

    private PickupSprite(PickupType type, Dimension dimension) {
        super(pickupSpriteFor(type), dimension);
    }

    private static InputStream pickupSpriteFor(PickupType type) {
        return switch (type) {
            case HEALTH -> HEALTH_ASSET;
            case SHIELD -> SHIELD_ASSET;
            case MISSILE -> MISSILE_ASSET;
        };
    }
}
