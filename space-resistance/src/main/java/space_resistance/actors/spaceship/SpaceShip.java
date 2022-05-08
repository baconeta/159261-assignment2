package space_resistance.actors.spaceship;

import space_resistance.actors.bullet.Bullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.PlayerShip;
import space_resistance.game.GameWorld;
import space_resistance.player.Player;
import space_resistance.player.PlayerNumber;
import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;
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

    private Point velocity = new Point(0, 0);

    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    private boolean shootKeyDown = false;

    public static SpaceShip spawnAt(GameWorld world, Point origin, Player player) {
        SpaceShip spaceShip = new SpaceShip(
                world,
                origin,
                player);

        world.add(spaceShip);
        return spaceShip;
    }

    private SpaceShip(GameWorld world, Point origin, Player player) {
        this.world = world;
        this.player = player;
        graphic = initSprite();
        setOrigin(origin);
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound playerSprite = new TGraphicCompound(DIMENSION);

        Sprite spaceship = new PlayerShip(AssetLoader.load(PLAYER_SHIP), DIMENSION);

        playerSprite.add(spaceship);

        return playerSprite;
    }

    public void update() {
        this.setOrigin(new Point(this.origin.x + velocity.x, this.origin.y+ velocity.y)); // Update player position based on velocity
        for (Bullet bullet : bullets){
            bullet.update();
        }
        if (shootKeyDown ){
            if (bullets.size() >= 1){
                if (bullets.get(bullets.size() - 1).getTimeExisted() > 50) { // Delay shots of bullets so that thousands don't spawn when the player holds down the shooting key
                    bullets.add(Bullet.spawnAt(world, new Point(this.origin.x, this.origin.y - 5)));
                }
            } else
                bullets.add(Bullet.spawnAt(world, new Point(this.origin.x, this.origin.y - 5)));
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
    KeyEvent keyPressed = null;
    KeyEvent keyReleased = null;
    private void performAction(Action action) {
        switch(action) {
            // If movement keys are pressed, set player velocity for corresponding axis to 10 or -10 (arbitrary value, can be changed later) depending on direction
            case MOVE_UP -> {
                velocity.y = -10;
            }
            case MOVE_DOWN -> {
                velocity.y = 10;
            }
            case MOVE_LEFT -> {
                velocity.x = -10;
            }
            case MOVE_RIGHT -> {
                velocity.x = 10;
            }
            case SHOOT -> {
                if (player.playerNumber() == PlayerNumber.PLAYER_TWO){
                    if (keyPressed.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT){ // Check if player 2 is pressing left shift
                        shootKeyDown = true;
                    }
                } else {
                    shootKeyDown = true;
                }
            }
        }
    }

    public void movementKeyReleasedAction(Action action){
        switch(action) {
            // If movement keys are released set player velocity for corresponding axis to 0
            case MOVE_UP, MOVE_DOWN -> {
                velocity.y = 0;
            }
            case MOVE_LEFT, MOVE_RIGHT -> {
                velocity.x = 0;
            }
            case  SHOOT -> {
                if (player.playerNumber() == PlayerNumber.PLAYER_TWO){
                    if (keyPressed.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT){ // Check if player 2 is pressing left shift
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
