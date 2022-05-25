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
    private final Game engine;
    private GameWorld world;
    private final GameState gameState;
    public static boolean paused;

    public PlayGameScreen(Game game, Consumer<ScreenIdentifier> screenChangeCallback, GameWorld g) {
        SoundEffects.shared().backgroundMusic().playOnLoop();
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;
        paused = false;
        gameState = new GameState(Settings.shared().config());
        if (g == null){
            world = new GameWorld(
                    Game.WINDOW_DIMENSION,
                    this::onGameOverNotified,
                    gameState);
        } else {
            world = g;
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
            if (paused){
                SoundEffects.shared().backgroundMusic().stopPlayingLoop();
                for (Actor a: world.actors()) {
                    if (a instanceof SpaceShip){
                        ((SpaceShip) a).spaceshipThrusters().setPaused(true);
                        ((SpaceShip) a).velocity().setDirectionX(0);
                        ((SpaceShip) a).velocity().setDirectionY(0);
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
    public void addToCanvas() {
        engine.loadWorld(world);
    }

    @Override
    public void removeFromCanvas() {
        engine.unloadWorld();
    }

    @Override
    public void update(double dtMillis) {
        if (!paused) {
            world.update();
        }
    }

    @Override
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
