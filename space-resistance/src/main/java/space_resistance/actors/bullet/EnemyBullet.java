package space_resistance.actors.bullet;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.sprites.EnemyShot;
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

public class EnemyBullet extends Actor {
    private static final Dimension DIMENSION = new Dimension(72, 72);
    private static final Random RANDOM = new Random();

    private final GameWorld world;

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;
    private EnemyType type;

    public EnemyBullet(EnemyType type, GameWorld world, TPoint origin) {
        this.type = type;
        this.world = world;
        graphic = initSprite();
        physics = initPhysics();

        // Variation in the shots
        origin.x += RANDOM.nextInt(1 + 1) - 1;
        startTime = System.currentTimeMillis();

        destroyWhenOffScreen = true;
        setOrigin(origin);
        world.add(this);
    }

    public void update() {
        currentTime = System.currentTimeMillis();
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound enemyBulletSprite = new TGraphicCompound(DIMENSION);
        Sprite enemyShot = new EnemyShot(type, new Dimension(72, 72));
        enemyBulletSprite.add(enemyShot);

        if (Game.DEBUG_MODE) { enemyBulletSprite.add(new TRect(new Dimension(7, enemyBulletSprite.height() / 2))); }
        return enemyBulletSprite;
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, new Dimension(7, graphic().dimension().height / 2));
        velocity = new TVelocity(500, new TVector(0, 1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public long timeExisted(){
        return currentTime - startTime;
    }
}
