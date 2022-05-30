package space_resistance.assets.sprites;

import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class DefaultShot extends Sprite {
    private static final Dimension DIMENSION = new Dimension(5, 13);

    private static final InputStream DEFAULT_ASSET = AssetLoader.load("PlayerDefaultShot.png");
    private static final DefaultShot DEFAULT_SHOT = new DefaultShot();

    public static DefaultShot fetchSprite() {
        return DEFAULT_SHOT;
    }

    private DefaultShot() {
        super(DEFAULT_ASSET, DIMENSION);
    }
}
