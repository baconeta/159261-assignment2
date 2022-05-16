package space_resistance.actors.spaceship;

import space_resistance.actors.bullet.Bullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.animated_sprites.PlayerThruster;
import space_resistance.assets.sprites.PlayerShip;
import space_resistance.game.Game;
import space_resistance.game.GameWorld;
import space_resistance.player.Player;
import space_resistance.player.PlayerNumber;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.AnimatedSprite;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Optional;

public class SpaceShip extends Actor {
    private static final String PLAYER_SHIP = "Player.png";
    private static final Dimension DIMENSION = new Dimension(64, 64);

    private final GameWorld world;
    private final Player player;
    private TPoint velocity = new TPoint(0, 0);
    private final ArrayList<Bullet> bullets = new ArrayList<>();

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
        setOrigin(origin);
    }

    private TGraphicCompound initSprite() {
        // Player Ship
        TGraphicCompound playerSprite = new TGraphicCompound(DIMENSION);
        Sprite spaceship = new PlayerShip(AssetLoader.load(PLAYER_SHIP), DIMENSION);
        playerSprite.add(spaceship);
        // Player Thrust
        AnimatedSprite spaceshipThrusters = new PlayerThruster();
        spaceshipThrusters.setOrigin(new TPoint(this.origin.x, this.origin.y + 30));
        playerSprite.add(spaceshipThrusters);
        return playerSprite;
    }

    public void checkBoundaries(){
        if (this.origin.x < 0){
            this.origin.x = 0;
        }
        if (this.origin.y < 0){
            this.origin.y = 0;
        }
        if (this.origin.x > Game.WINDOW_DIMENSION.width - DIMENSION.width){
            this.origin.x = Game.WINDOW_DIMENSION.width - DIMENSION.width;
        }
        if (this.origin.y > Game.WINDOW_DIMENSION.height - DIMENSION.height * 2){
            this.origin.y = Game.WINDOW_DIMENSION.height - DIMENSION.height * 2;
        }
    }

    public void normaliseVelocity(){
        try {
            double magnitude = Math.sqrt(((velocity.x * velocity.x) + (velocity.y * velocity.y)));
            velocity = new TPoint((int) (velocity.x * 10 / magnitude), (int) (velocity.y * 10 / magnitude));
        } catch (ArithmeticException e){

        }
    }

    public void update() {
        normaliseVelocity();
        this.setOrigin(new TPoint(this.origin.x + velocity.x, this.origin.y+ velocity.y));
        for (var bullet : bullets) {
            bullet.update();
        }
        checkBoundaries();
        if (shootKeyDown) {
            if (bullets.size() >= 1) {
                // Delay shots of bullets so that thousands don't spawn when the player holds down the shooting key
                if (bullets.get(bullets.size() - 1).timeExisted() > 50) {
                    bullets.add(Bullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y - 5)));
                }
            } else {
                bullets.add(Bullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y - 5)));
            }
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
            // Set player velocity for corresponding axis to 10 or -10 depending on direction
            // (arbitrary value, can be changed later)
            case MOVE_UP -> velocity.y = -10;
            case MOVE_DOWN -> velocity.y = 10;
            case MOVE_LEFT -> velocity.x = -10;
            case MOVE_RIGHT -> velocity.x = 10;
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
            case MOVE_UP, MOVE_DOWN -> velocity.y = 0;
            case MOVE_LEFT, MOVE_RIGHT -> velocity.x = 0;
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
}
