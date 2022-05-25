package space_resistance.ui.screens.gameplay;

import space_resistance.actors.bullet.Bullet;
import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.spaceship.SpaceShip;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.Background;
import space_resistance.game.Game;
import space_resistance.game.GameState;
import space_resistance.game.GameWorld;
import space_resistance.ui.components.Button;
import space_resistance.ui.components.ButtonGroup;
import space_resistance.ui.screens.Screen;
import space_resistance.ui.screens.ScreenIdentifier;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;
import tengine.physics.collisions.events.CollisionEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class PauseScreen implements Screen {
    private final Consumer<ScreenIdentifier> screenChangeCallback;
    private final Game engine;
    private final TGraphicCompound graphic;

    private final ButtonGroup buttonGroup;
    private final Button resume;
    private final Button quit;
    private final String BACKGROUND = "SpaceBackground.png";
    private static final Dimension DIMENSION = new Dimension(600, 800);
    Background background = new Background(AssetLoader.load(BACKGROUND), DIMENSION);

    private GameWorld gameWorld;

    public PauseScreen(Game game, Consumer<ScreenIdentifier> screenChangeCallback, GameState gameState, GameWorld g) {
        this.engine = game;
        this.screenChangeCallback = screenChangeCallback;

        // Title
        TLabel title = new TLabel("Game Paused");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().bodyFont());
        title.setOrigin(new TPoint(240, 300));

        gameWorld  = g;
        // Buttons
        resume = new Button("RESUME");
        resume.setOrigin(new TPoint(80, 490));

        quit = new Button("QUIT TO MENU");
        quit.setOrigin(new TPoint(470, 490));

        buttonGroup = new ButtonGroup(resume, quit);

        // Graphic
        graphic = new TGraphicCompound(Game.WINDOW_DIMENSION);
        graphic.add(background);
        graphic.addAll(title, resume, quit);
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> buttonGroup.previous();
            case KeyEvent.VK_RIGHT -> buttonGroup.next();
            case KeyEvent.VK_ENTER -> {
                SoundEffects.shared().menuSelect().play();
                if (buttonGroup.getFocussed() == resume) {
                    screenChangeCallback.accept(ScreenIdentifier.PLAYING); //  Uncomment to display pause screen
                    SoundEffects.shared().backgroundMusic().playOnLoop();
                    for (Actor a: gameWorld.actors()) {
                        if (a instanceof SpaceShip){
                            ((SpaceShip) a).spaceshipThrusters().setPaused(false);
                        }
                        if (a instanceof Bullet) { a.velocity().setSpeed(500); }
                        if (a instanceof Enemy) {
                            a.velocity().setSpeed(50);
                        }
                    }
                    screenChangeCallback.accept(ScreenIdentifier.PLAYING);
                } else if (buttonGroup.getFocussed() == quit){
                    screenChangeCallback.accept(ScreenIdentifier.SHOWING_MENU);
                }
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        // No-op
    }

    @Override
    public void addToCanvas() {
        engine.graphicsEngine().add(graphic);
    }

    @Override
    public void removeFromCanvas() {
        graphic.removeFromParent();
    }

    @Override
    public ScreenIdentifier screen() {
        return ScreenIdentifier.SHOWING_GAME_OVER;
    }

    @Override
    public void update(double dtMillis) {
        // No-op
    }

    @Override
    public void handleCollisionEvent(CollisionEvent event) {
        // No-op
    }

    public GameWorld gameWorld() {
        return gameWorld;
    }
}
