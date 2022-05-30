package space_resistance.actors.bullet;

import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.DefaultShot;
import space_resistance.game.Game;
import space_resistance.player.Player;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;

public class PlayerBullet extends Bullet {
    private static final Dimension DIMENSION = new Dimension(5, 13);
    private static final TVector DIRECTION = new TVector(0, -1);
    private static final int SPEED = 500;
    private static final int DAMAGE = 5;
    private final Player instigator;

    public PlayerBullet(TPoint origin, Player instigator) {
        super(origin);
        SoundEffects.shared().shoot().play(5);
        this.instigator = instigator;

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    @Override
    protected TGraphicCompound initSprite() {
        var sprite = new TGraphicCompound(dimension());
        sprite.add(DefaultShot.fetchSprite());
        if (Game.DEBUG_MODE) {
            TRect debugRect = new TRect(dimension());
            debugRect.outlineColor = Color.RED;
            sprite.add(debugRect);
        }

        return sprite;
    }

    @Override
    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, graphic.dimension());
        velocity = new TVelocity(SPEED, DIRECTION);

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    @Override
    protected Dimension dimension() {
        return DIMENSION;
    }

    public int damageToDeal() {
        return DAMAGE;
    }

    public Player instigator() {
        return instigator;
    }
}
