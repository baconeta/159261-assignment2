package tengine.graphics.components.text;

import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

public class TLabel extends TGraphicObject {
    private static final int DEFAULT_SIZE = 12;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, DEFAULT_SIZE);

    private String text;
    private Font font = DEFAULT_FONT;
    private Color color = DEFAULT_COLOR;
    private int lineHeight = (int) (DEFAULT_SIZE * 1.1);

    public TLabel(String text) {
        this(text, new TPoint(0, 0));
    }

    public TLabel(String text, TPoint origin) {
        super(new Dimension());
        setText(text);
        setOrigin(origin);
    }

    public String text() {
        return text;
    }

    public void setText(String text) {
        // TODO: calculate and update dimension based on width and height of text
        //   Font.getStringBounds(String str, FontRenderContext frc) would let us easily
        //   the get the bounds of the text and set the TLabel dimension. To get the FontRenderContext
        //   we need to be able to call Graphics2D.getFontRenderContext() on the current context.
        this.text = text;
    }

    public Font font() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int fontSize() {
        return font.getSize();
    }

    public void setFontSize(int fontSize) {
        font = new Font(font.getFontName(), font.getStyle(), fontSize);
        lineHeight = (int) (fontSize * 1.1);
    }

    public Color color() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        int textY = 0;
        int i = 0;
        for (String line : text.split("\n")) {
            if (i++ == 0) {
                ctx.drawText(new Point(0, textY), line, font, color);
            } else {
                ctx.drawText(new Point(0, textY += lineHeight), line, font, color);
            }
        }
    }

    @Override
    public final Dimension dimension() {
        throw new RuntimeException("TLabel dimension not supported");
    }

    @Override
    public final int width() {
        throw new RuntimeException("TLabel width not supported");
    }

    @Override
    public final int height() {
        throw new RuntimeException("TLabel height not supported");
    }
}
