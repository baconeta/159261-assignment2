package tengine.graphics;

import java.awt.*;

public class Bounds {
    protected Point origin;
    protected Dimension dimension;

    public Bounds() {
        this(new Point(0, 0), new Dimension(0, 0));
    }

    public Bounds(Point origin) {
        this(origin, new Dimension(0, 0));
    }

    public Bounds(Dimension dimension) {
        this(new Point(0, 0), dimension);
    }

    public Bounds(Point origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }
}
