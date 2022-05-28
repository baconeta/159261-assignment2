package space_resistance.assets;

import java.awt.*;
import java.io.IOException;

public final class FontBook {
    private static final String FONT_NAME = "Revamped.otf";
    private static final String DEFAULT = "Arial";

    private static FontBook singleton = null;

    private Font gameFont;

    public static FontBook shared() {
        if (singleton == null) {
            singleton = new FontBook();
        }

        return singleton;
    }

    private FontBook() {
        loadFonts();
    }

    private void loadFonts() {
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, AssetLoader.load(FONT_NAME));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Font defaultFont() {
        return new Font(DEFAULT, Font.PLAIN, 12);
    }

    public Font titleFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 32);
    }

    public Font levelLabelFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 30);
    }

    public Font pauseFont() {
        return new Font(gameFont.getFontName(), Font.ITALIC, 16);
    }

    public Font hudFont() {
        return new Font(gameFont.getFontName(), Font.BOLD, 20);
    }

    public Font buttonFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 18);
    }

    public Font bodyFont() {
        return new Font(gameFont.getFontName(), Font.PLAIN, 16);
    }
}
