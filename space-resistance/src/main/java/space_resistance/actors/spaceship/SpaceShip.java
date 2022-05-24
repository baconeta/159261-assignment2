package space_resistance.actors.spaceship;

import space_resistance.actors.bullet.EnemyBullet;
import space_resistance.actors.bullet.PlayerBullet;
import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.pickup.Pickup;
import space_resistance.assets.animated_sprites.PlayerThruster;
import space_resistance.assets.sprites.PlayerShip;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import space_resistance.player.Player;
import space_resistance.player.PlayerNumber;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.shapes.TRect;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.TVector;
import tengine.physics.kinematics.TVelocity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class SpaceShip extends Actor {
    private static final Dimension DIMENSION = new Dimension(64, 64);
    private static final int DELAY_BETWEEN_BULLETS = 50;

    private final GameWorld world;

    private AnimatedSprite spaceshipThrusters = PlayerThruster.sprite();

    private final Player player;
    private long lastBulletFired;

    // TODO: maybe rework the player controls mapping so we don't need to store these on the class
    KeyEvent keyPressed = null;
    KeyEvent keyReleased = null;

    private boolean shootKeyDown = false;

    public static SpaceShip spawnAt(GameWorld world, TPoint origin, Player player) {
        SpaceShip spaceShip = new SpaceShip(
                world,
                origin,
                player);

        world.add(spaceShip);

        return spaceShip;
    }

    private SpaceShip(GameWorld world, TPoint origin, Player player) {
        this.world = world;
        this.player = player;

        graphic = initSprite();
        physics = initPhysics();

        setOrigin(origin);
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound playerSprite = new TGraphicCompound(DIMENSION);

        // Thruster
        spaceshipThrusters.setOrigin(new TPoint(this.origin.x, this.origin.y + 30));

        playerSprite.add(spaceshipThrusters);
        playerSprite.add(PlayerShip.shipSprite());
        if (Game.DEBUG_MODE) { playerSprite.add(new TRect(DIMENSION)); }

        return playerSprite;
    }

    protected TPhysicsComponent initPhysics() {
        boolean isStatic = false;
        boolean hasCollisions = true;
        CollisionRect collisionRect = new CollisionRect(origin, DIMENSION);

        // Feel free to change this if the speed isn't right
        velocity = new TVelocity(200, new TVector(0, 0));

        return new TPhysicsComponent(this, isStatic, collisionRect, hasCollisions);
    }

    // TODO: Reduce calculations here, maybe use a boundary to be able to easily check if player is within it
    public void checkBoundaries() {
        if (this.origin.x < 0) {
            this.origin.x = 0;
        }

        if (this.origin.y < 0) {
            this.origin.y = 0;
        }

        if (this.origin.x > Game.WINDOW_DIMENSION.width - DIMENSION.width) {
            this.origin.x = Game.WINDOW_DIMENSION.width - DIMENSION.width;
        }

        if (this.origin.y > Game.WINDOW_DIMENSION.height - DIMENSION.height * 2) {
            this.origin.y = Game.WINDOW_DIMENSION.height - DIMENSION.height * 2;
        }
    }

    public AnimatedSprite spaceshipThrusters() {
        return spaceshipThrusters;
    }

    public void update() {
        checkBoundaries();

        // TODO: This should be handled in performAction, not here. Ideally this is handled at the GameWorld level so
        //  we don't have actors adding things to the world willy nilly.
        long currentTime = System.currentTimeMillis();
        if (shootKeyDown && currentTime-lastBulletFired > DELAY_BETWEEN_BULLETS) {
            var bullet = new PlayerBullet(world, new TPoint(this.origin.x, this.origin.y - 5));
            world.add(bullet);
            lastBulletFired  = currentTime;
        }
    }

    public boolean handleKeyPressed(KeyEvent keyEvent) {
        Optional<Action> action = player.controls().mappedAction(keyEvent.getKeyCode());
        action.ifPresent(this::performAction);
        keyPressed = keyEvent;
        return action.isPresent();
    }

    public boolean handleKeyReleased(KeyEvent keyEvent) {
        Optional<Action> action = player.controls().mappedAction(keyEvent.getKeyCode());
        action.ifPresent(this::movementKeyReleasedAction);
        keyReleased = keyEvent;
        return action.isPresent();
    }

    private void performAction(Action action) {
        switch(action) {
            case MOVE_UP -> velocity.setDirectionY(-1);
            case MOVE_DOWN -> velocity.setDirectionY(1);
            case MOVE_LEFT -> velocity.setDirectionX(-1);
            case MOVE_RIGHT -> velocity.setDirectionX(1);
            case SHOOT -> {
                if (player.playerNumber() == PlayerNumber.PLAYER_TWO) {
                    // Check if player 2 is pressing left shift
                    if (keyPressed.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                        shootKeyDown = true;
                    }
                } else {
                    shootKeyDown = true;
                }
            }
        }
    }

    public void movementKeyReleasedAction(Action action) {
        switch(action) {
            // Set player velocity for corresponding axis to 0
            case MOVE_UP, MOVE_DOWN -> velocity.setDirectionY(0);
            case MOVE_LEFT, MOVE_RIGHT -> velocity.setDirectionX(0);
            case  SHOOT -> {
                if (player.playerNumber() == PlayerNumber.PLAYER_TWO) {
                    // Check if player 2 is pressing left shift
                    if (keyPressed.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                        shootKeyDown = false;
                    }
                } else {
                    shootKeyDown = false;
                }
            }
        }
    }

    public PlayerNumber playerNumber() {
        return player.playerNumber();
    }

    public void collision(Actor actorB) {
        // TODO: possibly use reflection + a switch here
        if (actorB instanceof EnemyBullet) {
            player.reduceHealth(1);
        }

        if (actorB instanceof Enemy) {
            player.reduceHealth(100);
        }

        if (actorB instanceof Pickup) {
            player.handlePickup(((Pickup) actorB).pickupType());
        }
    }
}
