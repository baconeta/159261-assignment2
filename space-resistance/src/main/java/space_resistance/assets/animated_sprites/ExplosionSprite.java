package space_resistance.assets.animated_sprites;

import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.world.GridSquare;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

public class ExplosionSprite extends AnimatedSprite {
    public static final int DEFAULT_FPS = 60;
    private static final double SCALE = 0.125;
    private static final Dimension SEQUENCE_GRID = new Dimension(8, 4);
    private static final Dimension FRAME_DIMENSION_PIXELS = new Dimension(512, 512);
    private static final InputStream EXPLOSION_ASSET = AssetLoader.load("Explosion.png");

    private static final SpriteSequence SEQUENCE = new SpriteSequence("", generateSequence(), false);
    private static final ExplosionSprite EXPLOSION = new ExplosionSprite(EXPLOSION_ASSET, FRAME_DIMENSION_PIXELS, DEFAULT_FPS, SEQUENCE);
    static {
        EXPLOSION.setScale(SCALE);
    }

    public static ExplosionSprite sprite() {
        return EXPLOSION;
    }

    private ExplosionSprite(InputStream is, Dimension frameDimension, int fps, SpriteSequence currentSequence) {
        super(is, frameDimension, fps, currentSequence);
    }

    // TODO: Maybe extract out into package-private helper class
    private static ArrayList<GridSquare> generateSequence() {
        int numFrames = SEQUENCE_GRID.width * SEQUENCE_GRID.height;
        ArrayList<GridSquare> sequence = new ArrayList<>(numFrames);
        for (int i = 0; i < numFrames; ++i) {
            sequence.add(new GridSquare(i / SEQUENCE_GRID.width, i % SEQUENCE_GRID.width));
        }

        return sequence;
    }
}
