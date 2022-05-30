package space_resistance.assets.sprites;

import space_resistance.assets.AssetLoader;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class IntroStory extends TGraphicCompound {
  private static final InputStream STORY_ASSET = AssetLoader.load("IntroStoryScreen.png");
  private static final Dimension STORY_SCREEN_DIMENSION = new Dimension(600, 800);
  private static final IntroStory INTRO_STORY = new IntroStory();

  boolean isStatic = false;
  Sprite introStory;

  public static IntroStory getInstance() {
    return INTRO_STORY;
  }

  private IntroStory() {
    super(STORY_SCREEN_DIMENSION);

    introStory = new Sprite(STORY_ASSET, STORY_SCREEN_DIMENSION);
    introStory.setOrigin(new TPoint(0, 0));
    add(introStory);
  }

  public void setIsStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }

  // update method
  @Override
  public void update(double dtMillis) {
  }
}
