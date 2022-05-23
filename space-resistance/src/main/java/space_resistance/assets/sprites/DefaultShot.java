package space_resistance.assets.sprites;

import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class DefaultShot extends Sprite {
    private static final Dimension DIMENSION = new Dimension(64, 64);

    private static final InputStream DEFAULT_ASSET = AssetLoader.load("PlayerDefaultShots.png");
    private static final DefaultShot DEFAULT_SHOT = new DefaultShot(DEFAULT_ASSET, DIMENSION);

    public static DefaultShot fetchSprite() {
        return DEFAULT_SHOT;
    }

    private DefaultShot(InputStream is, Dimension dimension) {
        super(is, dimension);
    }
}
