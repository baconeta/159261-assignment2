package space_resistance.assets.sprites;

import space_resistance.actors.pickup.PickupType;
import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class PickupSprite extends Sprite {
    private static final String[] PICKUP_SPRITES = {"health_temp.png", "shield_temp.png", "missiles_temp.png"}; // TODO add images for these
    public PickupSprite(PickupType pickupType, Dimension dimension) {
        super(getPickupSprite(pickupType), dimension);
    }
    private static InputStream getPickupSprite(PickupType pickupType){
        switch (pickupType){
            case Health -> {
                return AssetLoader.load(PICKUP_SPRITES[0]);
            }
            case Shield -> {
                return AssetLoader.load(PICKUP_SPRITES[1]);
            }
            case Missiles -> {
                return AssetLoader.load(PICKUP_SPRITES[2]);
            }
        }
        return null;
    }
}
