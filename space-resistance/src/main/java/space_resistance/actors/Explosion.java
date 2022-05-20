package space_resistance.actors;

import space_resistance.assets.SoundEffects;
import space_resistance.assets.animated_sprites.ExplosionSprite;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;
import java.util.Random;
public class Explosion extends Actor {
    private static final Dimension DIMENSION = new Dimension(64, 64);
    private static final Random RANDOM = new Random();

    private final GameWorld world;

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    private final int damageToDeal = 10;

    public Explosion(GameWorld world, TPoint origin) {
        SoundEffects.shared().explosionSound().play(5);
        this.world = world;
        graphic = initSprite();
        physics = initPhysics();
        startTime = System.currentTimeMillis();
        destroyWhenOffScreen = true;

        setOrigin(origin);
        world.add(this);

    }

    public void update() {
        currentTime = System.currentTimeMillis();
        if (timeExisted() / 100 > 5){
            this.destroy();
            this.removeFromWorld();
        }
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound explosion = new TGraphicCompound(DIMENSION);
        AnimatedSprite explosionSprite = new ExplosionSprite();
        explosion.add(explosionSprite);
        return explosion;
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(0, new TVector(0, 0));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public long timeExisted(){
        return currentTime - startTime;
    }
}
