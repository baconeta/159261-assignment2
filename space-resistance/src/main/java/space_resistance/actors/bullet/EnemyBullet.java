package space_resistance.actors.bullet;

import space_resistance.actors.enemy.EnemyConstants;
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
    private final int damage;
    private static final TVector DIRECTION = new TVector(0, 1);

    public EnemyBullet(EnemyType type, TPoint origin) {
        super(origin);
        this.type = type;
        this.damage = EnemyConstants.bulletDamage(type);

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    @Override
    protected TGraphicCompound initSprite() {
         var sprite = new TGraphicCompound(EnemyConstants.enemyBulletDimension(type));
         sprite.add(EnemyShot.shotFor(type));
         // TODO: Remove before submitting
         if (Game.DEBUG_MODE) {
            TRect debugRect = new TRect(EnemyConstants.enemyBulletDimension(type));
            debugRect.outlineColor = Color.RED;
            sprite.add(debugRect);
         }

         return sprite;
    }

    @Override
    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, EnemyConstants.enemyBulletDimension(type));
        velocity = new TVelocity(EnemyConstants.bulletSpeed(type), DIRECTION);

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    @Override
    protected Dimension dimension() {
        return EnemyConstants.enemyBulletDimension(type);
    }

    public int bulletDamage() { return damage; }
}
