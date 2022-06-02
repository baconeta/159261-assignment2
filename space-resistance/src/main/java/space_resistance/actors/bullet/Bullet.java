package space_resistance.actors.bullet;

import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.physics.TPhysicsComponent;

import java.awt.*;
import java.util.Random;

public abstract class Bullet extends Actor {
    private static final Random RANDOM = new Random();

    public Bullet(TPoint origin) {
        // Variation in the shots
        origin.x += RANDOM.nextInt(1 + 1) - 1;
        destroyWhenOffScreen = true;
    }

    protected abstract TGraphicCompound initSprite();

    protected abstract TPhysicsComponent initPhysics();

    protected abstract Dimension dimension();
}
