package space_resistance.actors.bullet;

import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.physics.TPhysicsComponent;

import java.awt.*;
import java.util.Random;

public abstract class Bullet extends Actor {
    private static final Random RANDOM = new Random();

    private final long startTime;

    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    public Bullet(TPoint origin) {
        // Variation in the shots
        origin.x += RANDOM.nextInt(1 + 1) - 1;
        startTime = System.currentTimeMillis();
        destroyWhenOffScreen = true;
    }

    public void update() {
        currentTime = System.currentTimeMillis();
    }

    protected abstract TGraphicCompound initSprite();

    protected abstract TPhysicsComponent initPhysics();

    protected abstract Dimension dimension();

    public long timeExisted(){
        return currentTime - startTime;
    }
}
