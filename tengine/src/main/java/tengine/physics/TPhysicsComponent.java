package tengine.physics;

import tengine.Actor;
import tengine.geom.TPoint;
import tengine.physics.collisions.shapes.CollisionRect;

public class TPhysicsComponent {
    private final Actor actor;

    private TPoint collisionShapeOffset = new TPoint();
    private CollisionRect collisionShape;
    private boolean isStatic;
    private boolean hasCollisions;

    public TPhysicsComponent(Actor actor) {
        this(actor, true, null, false);
    }

    public TPhysicsComponent(Actor actor, boolean isStatic, CollisionRect collisionShape, boolean hasCollisions) {
        this.actor = actor;
        this.isStatic = isStatic;
        this.collisionShape = collisionShape;
        this.hasCollisions = hasCollisions;
    }

    public void update(PhysicsEngine system, double dtMillis) {
        if (!isStatic) {
            // Actor will take care of updating the origin of the graphic and collision shape
            actor.setOrigin(actor.origin().translate(
                actor.velocity().dx() * dtMillis,
                actor.velocity().dy() * dtMillis
            ));
        }
    }

    public void setOrigin(TPoint origin) {
        this.collisionShape.setOrigin(new TPoint(origin.x + collisionShapeOffset.x, origin.y + collisionShapeOffset.y));
    }

    public CollisionRect collisionShape() {
        return collisionShape;
    }

    public void setCollisionShape(CollisionRect collisionShape) {
        this.collisionShape = collisionShape;
    }

    public void setCollisionShapeOffset(TPoint offset) {
        collisionShapeOffset = offset;
    }

    public void setStatic(boolean b) {
        isStatic = b;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setHasCollisions(boolean b) {
        this.hasCollisions = b;
    }

    public boolean hasCollisions() {
        return hasCollisions;
    }
}
