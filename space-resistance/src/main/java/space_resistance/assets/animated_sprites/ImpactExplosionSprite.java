package space_resistance.assets.animated_sprites;

import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.world.GridSquare;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

public class ImpactExplosionSprite extends AnimatedSprite {
    public static final int DEFAULT_FPS = 30;
    private static final double SCALE = 0.08;
    private static final Dimension SEQUENCE_GRID = new Dimension(3, 2);
    private static final Dimension FRAME_DIMENSION_PIXELS = new Dimension(512, 512);
    private static final InputStream IMPACT_EXPLOSION_ASSET = AssetLoader.load("ImpactExplosion.png");

    private static final SpriteSequence SEQUENCE = new SpriteSequence("", generateSequence(), false);
    private static final ImpactExplosionSprite IMPACT_EXPLOSION = new ImpactExplosionSprite(IMPACT_EXPLOSION_ASSET, FRAME_DIMENSION_PIXELS, DEFAULT_FPS, SEQUENCE);
    static {
        IMPACT_EXPLOSION.setScale(SCALE);
    }

    public static ImpactExplosionSprite sprite() {
        return IMPACT_EXPLOSION;
    }

    private ImpactExplosionSprite(InputStream is, Dimension frameDimension, int fps, SpriteSequence currentSequence) {
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
