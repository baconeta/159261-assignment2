package tengine.graphics.components.sprites;

import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;
import tengine.world.GridSquare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.function.Consumer;

public class AnimatedSprite extends TGraphicObject {
    protected Image image;
    protected double elapsedSecs;
    protected int fps;
    protected SpriteSequence currentSequence;
    protected int currentFrame;
    protected Consumer<SpriteSequence> sequenceEnd = null;

    protected boolean pausedAnimation = false;

    protected AnimatedSprite(InputStream is, Dimension frameDimension, int fps, SpriteSequence currentSequence) {
        super(frameDimension);
        image = ImageLoader.loadImage(is);
        this.fps = fps;
        this.currentSequence = currentSequence;
        currentFrame = 0;
        elapsedSecs = 0;
    }

    @Override
    public void update(double dtSecs) {
        if (!pausedAnimation){
            elapsedSecs += dtSecs;

            if (currentSequence.loops() || currentFrame != currentSequence.lastFrame()) {
                int framesToSkip = (int) (elapsedSecs / (1.0 / fps));
                if (!currentSequence.loops()) {
                    framesToSkip = Math.min(framesToSkip, currentSequence.lastFrame() - currentFrame);
                }
                currentFrame = (currentFrame + framesToSkip) % currentSequence.numFrames();
            }

            // TODO: only notify once if not looping
            if (currentFrame == currentSequence.lastFrame() && sequenceEnd != null) {
                sequenceEnd.accept(currentSequence);
            }

            elapsedSecs %= (1.0 / fps);
        }
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        GridSquare gridSquare = currentSequence.frames().get(currentFrame);
        int x = gridSquare.col() * dimension.width;
        int y = gridSquare.row() * dimension.height;

        Image frame = subImage(image, new TPoint(x, y), dimension);

        ctx.drawImage(frame, dimension);
    }

    public void setSequenceEndCallback(Consumer<SpriteSequence> onSequenceEnd) {
        sequenceEnd = onSequenceEnd;
    }

    Image subImage(Image source, TPoint point, Dimension dimension) {
        if (source == null) {
            System.err.println("Error: cannot extract a sub image from a null image.");

            return null;
        }

        BufferedImage buffered = (BufferedImage) source;

        return buffered.getSubimage((int)point.x, (int)point.y, dimension.width, dimension.height);
    }

    public void setPaused(boolean paused){
        pausedAnimation = paused;
    }

    // TODO: Add to engine
    public void resetAnimation() {
        currentFrame = 0;
    }
}
