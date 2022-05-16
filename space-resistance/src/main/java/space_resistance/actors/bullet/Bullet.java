package space_resistance.actors.bullet;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.SoundEffects;
import space_resistance.assets.sprites.DefaultShot;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.util.Random;

public class Bullet extends Actor {
    private static final String PLAYER_DEFAULT_SHOT = "PlayerDefaultShots.png";
    private static final Dimension DIMENSION = new Dimension(64, 64);
    private static final Random RANDOM = new Random();

    private final GameWorld world;

    private final TPoint velocity = new TPoint(0, -10);

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    public static Bullet spawnAt(GameWorld world, TPoint origin) {
        Bullet bullet = new Bullet(
                world,
                origin);
        world.add(bullet);
        return bullet;
    }

    private Bullet(GameWorld world, TPoint origin) {
        SoundEffects.shared().defaultPlayerShootingSound().play(5);
        this.world = world;
        graphic = initSprite();
        // Variation in the shots
        origin.x += RANDOM.nextInt(1 + 1) - 1;
        startTime = System.currentTimeMillis();
        setOrigin(origin);
    }

    public void update() {
        this.setOrigin(new TPoint(this.origin.x + velocity.x, this.origin.y + velocity.y));
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

    public long timeExisted(){
        return currentTime - startTime;
    }
}
