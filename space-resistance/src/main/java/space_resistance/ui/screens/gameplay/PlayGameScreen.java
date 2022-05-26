package space_resistance.ui.screens.gameplay;

import space_resistance.actors.bullet.Bullet;
import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.SoundEffects;
import space_resistance.game.Game;
import space_resistance.game.GameState;
import space_resistance.game.GameWorld;
import space_resistance.settings.Settings;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.Actor;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class PlayGameScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final GameWorld world;
    private final GameState gameState;
    public static boolean paused = false;

    public PlayGameScreen(Consumer<ScreenIdentifier> screenChangeCallback, GameWorld gameWorld) {
        SoundEffects.shared().backgroundMusic().playOnLoop();
        this.screenChangeCallback = screenChangeCallback;
        gameState = new GameState(Settings.shared().config());

        if (gameWorld == null) {
            world = new GameWorld(
                    Game.WINDOW_DIMENSION,
                    this::onGameOverNotified,
                    gameState);
        } else {
            world = gameWorld;
        }
    }

    public void onGameOverNotified() {
        screenChangeCallback.accept(ScreenIdentifier.SHOWING_GAME_OVER);
    }


    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (!paused){
            world.handleKeyPressed(keyEvent);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            screenChangeCallback.accept(ScreenIdentifier.SHOWING_PAUSE); //  Uncomment to display pause screen
            paused = !paused;

            if (paused) {
                SoundEffects.shared().backgroundMusic().stopPlayingLoop();
                for (Actor a: world.actors()) {
                    if (a instanceof SpaceShip spaceShip){
                        spaceShip.spaceshipThrusters().setPaused(true);
                        spaceShip.velocity().setDirectionX(0);
                        spaceShip.velocity().setDirectionY(0);
                    }

                    if (a instanceof Enemy || a instanceof Bullet) {
                        a.velocity().setSpeed(0);
                    }
                }
            } else {
                /*
                screenChangeCallback.accept(ScreenIdentifier.PLAYING); //  Uncomment to display pause screen
                SoundEffects.shared().backgroundMusic().playOnLoop();
                for (Actor a: world.actors()) {
                    if (a instanceof SpaceShip){
                        ((SpaceShip) a).spaceshipThrusters().setPaused(false);
                    }
                    if (a instanceof Bullet) { a.velocity().setSpeed(500); }
                    if (a instanceof Enemy) {
                        a.velocity().setSpeed(50);
                    }
                }

                 */
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent keyEvent) {
        if (!paused){
            world.handleKeyReleased(keyEvent);
        }
    }

    @Override
    public void addToCanvas(Game game) {
        game.loadWorld(world);
    }

    @Override
    public void removeFromCanvas() {
        world.canvas().removeFromParent();
    }

    @Override
    public void update(double dtMillis) {
        if (!paused) {
            world.update();
        }
    }

    public void handleCollisionEvent(CollisionEvent event) {
        world.handleCollisions(event);
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.PLAYING;
    }

    public GameState gameState() {
        return gameState;
    }
    public GameWorld gameWorld(){
        return world;
    }
}
