package tengine.graphics.components;

import tengine.geom.TPoint;
import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.transforms.TRotation;
import tengine.graphics.transforms.TScale;
import tengine.graphics.transforms.TTranslation;

import java.awt.*;

abstract public class TGraphicObject {
    protected Dimension dimension;
    protected final TRotation rotation;
    protected final TTranslation translation;
    protected final TScale scale;
    protected TGraphicCompound parent;

    public TGraphicObject(Dimension dimension) {
        this.dimension = dimension;
        rotation = TRotation.identity();
        translation = TTranslation.identity();
        scale = TScale.identity();
        parent = null;
    }

    public Dimension dimension() {
        return new Dimension((int) (scale.xScaleFactor * dimension.width),
                (int) (scale.yScaleFactor * dimension.height));
    }

    public int width() {
        return (int) (scale.xScaleFactor * dimension.width);
    }

    public int height() {
        return (int) (scale.yScaleFactor * dimension.height);
    }

    public void setOrigin(TPoint origin) {
        translation.dx = (int) origin.x;
        translation.dy = (int) origin.y;
    }

    public TPoint origin() {
        return new TPoint(translation.dx, translation.dy);
    }

    public int x() {
        return translation.dx;
    }

    public int y() {
        return translation.dy;
    }

    public TPoint midPoint() {
        return new TPoint(dimension.width / 2.0, dimension.height / 2.0);
    }

    public void setRotation(double thetaDegrees) {
        rotation.thetaDegrees = thetaDegrees;
    }

    public void setRotation(double thetaDegrees, TPoint origin) {
        rotation.thetaDegrees = thetaDegrees;
        rotation.origin = origin;
    }

    public void setScale(double scaleFactor) {
        scale.xScaleFactor = scaleFactor;
        scale.yScaleFactor = scaleFactor;
    }

    public void setParent(TGraphicCompound parent) {
        this.parent = parent;
    }

    public TGraphicCompound parent() {
        return parent;
    }

    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    public void paint(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        draw(ctx);

        ctx.popTransform();
    }

    protected abstract void draw(GraphicsCtx ctx);

    public void update(double dtMillis) {
        // No-op
    }

    public TScale scale() {
        return scale;
    }
}
