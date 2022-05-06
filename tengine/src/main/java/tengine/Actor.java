package tengine;

import tengine.graphics.components.TGraphicObject;
import tengine.physics.PhysicsComponent;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.world.World;

import java.awt.*;

public abstract class Actor {
    protected Point origin;
    protected PhysicsComponent physicsComponent;
    protected TGraphicObject graphicEntity;
    protected World world;

    public Actor() {
        origin = new Point();
        physicsComponent = new PhysicsComponent();
        graphicEntity = null;
        world = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (graphicEntity != null) {
            graphicEntity.setOrigin(origin);
        }

//        if (this.physicsComponent.collisionShape != null) {
//            physicsComponent.collisionShape.setOrigin(origin);
//        }
    }

    public Point origin() {
        return origin;
    }

    public int x() {
        return origin.x;
    }

    public int y() {
        return origin.y;
    }

    public CollisionShape bounds() {
        return physicsComponent.collisionShape;
    }

    public void removeFromWorld() {
        world.remove(this);
    }

    public void addToWorld(World world) {
        this.world = world;
    }

    public void destroy() {
        graphicEntity.removeFromParent();
        // TODO: Eventually include physicsComponent.removeFromSystem()
        world = null;
    }

    public TGraphicObject graphic() {
        return graphicEntity;
    }
}
