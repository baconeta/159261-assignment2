package space_resistance.assets.sprites;

import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class PlayerShip extends Sprite {
    private static final Dimension DIMENSION = new Dimension(64, 64);
    private static final InputStream SHIP_ASSET = AssetLoader.load("Player.png");
    private static final PlayerShip PLAYER_SHIP = new PlayerShip(SHIP_ASSET, DIMENSION);

    public static PlayerShip shipSprite() {
        return PLAYER_SHIP;
    }

    private PlayerShip(InputStream is, Dimension dimension) {
        super(is, dimension);
    }
}
