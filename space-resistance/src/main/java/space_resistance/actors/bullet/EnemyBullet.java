package space_resistance.actors.bullet;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.sprites.EnemyShot;
import space_resistance.game.Game;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;

public class EnemyBullet extends Bullet {
    private final EnemyType type;

    public EnemyBullet(EnemyType type, TPoint origin) {
        super(origin);
        this.type = type;

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    @Override
    protected TGraphicCompound initSprite() {
         var sprite = new TGraphicCompound(EnemyType.enemyBulletDimension(type));
         sprite.add(EnemyShot.shotFor(type));
         if (Game.DEBUG_MODE) { sprite.add(new TRect(EnemyType.enemyBulletDimension(type))); }

         return sprite;
    }

    @Override
    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, EnemyType.enemyBulletDimension(type));
        velocity = new TVelocity(EnemyType.bulletSpeed(type), new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    @Override
    protected Dimension dimension() {
        return EnemyType.enemyBulletDimension(type);
    }
}
