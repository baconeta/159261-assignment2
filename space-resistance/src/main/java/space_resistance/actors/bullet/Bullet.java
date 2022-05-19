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

// TODO: Merge other bullet classes into this one
//   Create a new constructor that takes a CharacterType enum
//   Switch over the CharacterType to know which enemy shot image to load
public class Bullet extends Actor {
    private static final String PLAYER_DEFAULT_SHOT = "PlayerDefaultShots.png";
    private static final Dimension DIMENSION = new Dimension(64, 64);
    private static final Random RANDOM = new Random();

    private final GameWorld world;

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    private final int damageToDeal = 10;

    public Bullet(GameWorld world, TPoint origin) {
        SoundEffects.shared().defaultPlayerShootingSound().play(5);
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
        TGraphicCompound bulletSprite = new TGraphicCompound(DIMENSION);
        Sprite playerDefaultShot = new DefaultShot(AssetLoader.load(PLAYER_DEFAULT_SHOT), DIMENSION);
        bulletSprite.add(playerDefaultShot);
        if (Game.DEBUG_MODE) { bulletSprite.add(new TRect(DIMENSION)); }
        return bulletSprite;
    }

    private TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(500, new TVector(0, -1));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    public long timeExisted(){
        return currentTime - startTime;
    }

    public int damageToDeal() { return damageToDeal; }
}
