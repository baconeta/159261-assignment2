package tengine.physics.collisions.events;

import tengine.physics.PhysicsComponent;
import tengine.physics.kinematics.Vector;

import java.awt.*;
import java.util.Objects;

public class CollisionEvent {
    private final PhysicsComponent movingObj;
    private final PhysicsComponent staticObj;
    private Vector impactVector;
    private Point impactSide;

    public CollisionEvent(PhysicsComponent p1, PhysicsComponent p2) {
        this(p1, p2, new Vector(0,0), new Point());
    }

    public CollisionEvent(PhysicsComponent p1, PhysicsComponent p2, Vector impactVector, Point impactSide) {
        movingObj = p1;
        staticObj = p2;
        this.impactVector = impactVector;
        this.impactSide = impactSide;
    }

    public PhysicsComponent movingObj() {
        return movingObj;
    }

    public PhysicsComponent staticObj() {
        return staticObj;
    }

    public void setImpactVector(Vector impactVector) {
        this.impactVector = impactVector;
    }

    public Vector impactVector() {
        return impactVector;
    }

    public void setImpactSide(Point impactSide) {
        this.impactSide = impactSide;
    }

    public Point impactSide() {
        return impactSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollisionEvent other)) return false;
        return movingObj == other.movingObj
                && staticObj == other.staticObj
                && Objects.equals(impactVector, other.impactVector)
                && Objects.equals(impactSide, other.impactSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movingObj, staticObj, impactVector, impactSide);
    }
}
