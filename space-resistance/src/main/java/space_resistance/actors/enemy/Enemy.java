package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;

public abstract class Enemy extends Actor {
    protected static final Dimension DIMENSION = new Dimension(72, 72);

    protected static String SHIP_SPRITE = null;

    protected final GameWorld world;

    protected int health = 100;
    protected int scoreWorth = 0;

    abstract protected TGraphicCompound initSprite();

    protected Enemy(GameWorld world, TPoint origin) {
        this.world = world;
        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(250, new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public void update() {
        if (health < 0){
            this.destroy();
        }
    }
}
