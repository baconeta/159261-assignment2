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
    // TODO: idk if this is correct? Gotta sort out sizing and scale
    private static final Dimension DIMENSION = new Dimension(512, 512);

    private final GameWorld world;
    private final Player player;

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
        setOrigin(origin);

        graphicEntity = initSprite();
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound playerSprite = new TGraphicCompound(DIMENSION);

        Sprite spaceship = new PlayerShip(AssetLoader.load(PLAYER_SHIP), DIMENSION);

        // Any other stuff you want here

        playerSprite.add(spaceship);

        return playerSprite;
    }

    public void update() {

    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        Optional<Action> action = player.controls().mappedAction(keyEvent.getKeyCode());
        action.ifPresent(this::performAction);

        return action.isPresent();
    }

    private void performAction(Action action) {
        switch(action) {
            case MOVE_UP -> {
                /* do something */
            }
            case MOVE_DOWN -> {
                /* do something */
            }
            case MOVE_LEFT -> {
                /* do something */
            }
            case MOVE_RIGHT -> {
                /* do something */
            }
            case SHOOT -> {
                /* do something */
            }
        }
    }

    public PlayerNumber playerNumber() {
        return player.playerNumber();
    }
}
