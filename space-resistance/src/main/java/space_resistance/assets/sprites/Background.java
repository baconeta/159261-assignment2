package space_resistance.assets.sprites;

import space_resistance.assets.AssetLoader;
import space_resistance.game.Game;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class Background extends TGraphicCompound {
  private static final InputStream BACKGROUND_ASSET = AssetLoader.load("SpaceBackground_600x1600.png");
  private static final Dimension BACKGROUND_DIMENSION = new Dimension(600, 1600);
  private static final Background BACKGROUND = new Background(true);

  boolean isStatic;
  Sprite background;

  public static Background staticBackground() {
    BACKGROUND.isStatic = true;

    return BACKGROUND;
  }

  public static Background movingBackground() {
    BACKGROUND.isStatic = false;

    return BACKGROUND;
  }

  private Background(boolean isStatic) {
    super(BACKGROUND_DIMENSION);
    this.isStatic = isStatic;

    background = new Sprite(BACKGROUND_ASSET, BACKGROUND_DIMENSION);
    background.setOrigin(new TPoint(0, -Game.WINDOW_DIMENSION.height));
    add(background);
  }

  // update method
  @Override
  public void update(double dtMillis) {
    if (!isStatic) {
      background.setOrigin((background.origin().y == 0)
        ? new TPoint(0, -Game.WINDOW_DIMENSION.height)
        : background.origin().translate(0, 1)
      );
    }
  }
}
