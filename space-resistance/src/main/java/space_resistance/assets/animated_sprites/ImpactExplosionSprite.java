package space_resistance.assets.animated_sprites;

import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.graphics.components.sprites.SpriteSequence;
import tengine.world.GridSquare;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

import static space_resistance.assets.animated_sprites.AnimationSequence.getGridSquares;

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

    private static ArrayList<GridSquare> generateSequence() {
        return getGridSquares(SEQUENCE_GRID);
    }
}
