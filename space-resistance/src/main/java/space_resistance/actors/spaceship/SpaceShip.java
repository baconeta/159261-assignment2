package space_resistance.actors.spaceship;

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
import java.util.Optional;

public class SpaceShip extends Actor {
    private static final String PLAYER_SHIP = "Player.png";
    private static final Dimension DIMENSION = new Dimension(64, 64);

    private final GameWorld world;
    private final Player player;

    private Point velocity = new Point(0, 0);

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
    }

    public boolean handleKeyPressed(KeyEvent keyEvent) {
        Optional<Action> action = player.controls().mappedAction(keyEvent.getKeyCode());
        action.ifPresent(this::performAction);

        return action.isPresent();
    }
    public boolean handleKeyReleased(KeyEvent keyEvent) {
        Optional<Action> action = player.controls().mappedAction(keyEvent.getKeyCode());
        action.ifPresent(this::movementKeyReleasedAction);

        return action.isPresent();
    }
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
                /* do something */
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
        }
    }

    public PlayerNumber playerNumber() {
        return player.playerNumber();
    }
}
