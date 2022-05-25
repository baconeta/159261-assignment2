package space_resistance.assets.sprites;

import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

// TODO: consider removing this, it is not adding any extra clarity than simply using a sprite
public class Background extends Sprite {
  public Background(InputStream is, Dimension dimension) {
    super(is, dimension);
  }
}
