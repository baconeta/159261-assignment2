package space_resistance.actors.bullet;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.DefaultShot;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.graphics.components.sprites.Sprite;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;
import java.util.Random;

public abstract class Bullet extends Actor {
    private static final Random RANDOM = new Random();

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    // TODO: Consider creating an ActorType that can be passed in with the constructor and merging Enemy and Player
    //  bullet into this class
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
