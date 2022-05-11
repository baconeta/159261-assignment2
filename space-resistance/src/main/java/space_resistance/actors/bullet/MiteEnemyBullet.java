package space_resistance.actors.bullet;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.DefaultShot;
import space_resistance.assets.sprites.MiteEnemyShot;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.util.Random;

public class MiteEnemyBullet extends Actor {
    private static final String MITE_ENEMY_SHOT = "MiteEnemyShot.png";
    private static final Dimension DIMENSION = new Dimension(72, 72);
    private static final Random RANDOM = new Random();

    private final GameWorld world;

    private final Point velocity = new Point(0, 10);

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    public static MiteEnemyBullet spawnAt(GameWorld world, Point origin) {
        MiteEnemyBullet bullet = new MiteEnemyBullet(
                world,
                origin);
        world.add(bullet);
        return bullet;
    }

    private MiteEnemyBullet(GameWorld world, Point origin) {
        this.world = world;
        graphic = initSprite();
        // Variation in the shots
        origin.x += RANDOM.nextInt(1 + 1) - 1;
        startTime = System.currentTimeMillis();
        setOrigin(origin);
    }

    public void update() {
        this.setOrigin(new Point(this.origin.x + velocity.x, this.origin.y + velocity.y));
        if (this.origin.y < 0 || this.origin.y > 800 || this.origin.x > 600 || this.origin.x < 0){
            this.destroy();
        }
        currentTime = System.currentTimeMillis();
    }

    private TGraphicCompound initSprite() {
        TGraphicCompound enemyBulletSprite = new TGraphicCompound(DIMENSION);

        Sprite enemyShot = new MiteEnemyShot(AssetLoader.load(MITE_ENEMY_SHOT), DIMENSION);

        enemyBulletSprite.add(enemyShot);

        return enemyBulletSprite;
    }

    public long timeExisted(){
        return currentTime - startTime;
    }
}
