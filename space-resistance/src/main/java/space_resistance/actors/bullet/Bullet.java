package space_resistance.actors.bullet;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.DefaultShot;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.util.Random;

public class Bullet extends Actor {
    private static final String PLAYER_DEFAULT_SHOT = "PlayerDefaultShots.png";
    private static final Dimension DIMENSION = new Dimension(64, 64);

    private final GameWorld world;

    private Point velocity = new Point(0, -10);

    private long startTime = 0;
    private long currentTime = 0; // Keeps track of how long bullet actor has existed

    public static Bullet spawnAt(GameWorld world, Point origin) {
        Bullet bullet = new Bullet(
                world,
                origin);
        world.add(bullet);
        return bullet;
    }
    private Bullet(GameWorld world, Point origin) {
        SoundEffects.shared().defaultPlayerShootingSound().play(5);
        this.world = world;
        graphic = initSprite();
        Point variedOrigin = origin;
        variedOrigin.x += new Random().nextInt(1 + 1) - 1; // Variation in the shots
        startTime = System.currentTimeMillis();
        setOrigin(variedOrigin);
    }
    public void update(){
        this.setOrigin(new Point(this.origin.x + velocity.x, this.origin.y+ velocity.y)); // Update bullet position based on velocity
        if (this.origin.y < 0 || this.origin.y > 800 || this.origin.x > 600 || this.origin.x < 0){
            this.destroy();
        }
        currentTime = System.currentTimeMillis();
    }
    private TGraphicCompound initSprite() {
        TGraphicCompound playerSprite = new TGraphicCompound(DIMENSION);

        Sprite playerDefaultShot = new DefaultShot(AssetLoader.load(PLAYER_DEFAULT_SHOT), DIMENSION);

        playerSprite.add(playerDefaultShot);

        return playerSprite;
    }
    public long getTimeExisted(){
        return currentTime - startTime;
    }
}
