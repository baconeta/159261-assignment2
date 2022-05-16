package space_resistance.actors.bullet;

import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.MiteEnemyShot;
import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.util.Random;

public class TarantulaEnemyBullet extends Actor {
    private static final String TARANTULA_ENEMY_SHOT = "TarantulaEnemyShot.png";
    private static final Dimension DIMENSION = new Dimension(72, 72);
    private static final Random RANDOM = new Random();

    private final GameWorld world;

    private final TPoint velocity = new TPoint(0, 10);

    private final long startTime;
    // Keeps track of how long bullet actor has existed
    private long currentTime = 0;

    public static TarantulaEnemyBullet spawnAt(GameWorld world, TPoint origin) {
        TarantulaEnemyBullet bullet = new TarantulaEnemyBullet(
                world,
                origin);
        world.add(bullet);
        return bullet;
    }

    private TarantulaEnemyBullet(GameWorld world, TPoint origin) {
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
        TGraphicCompound enemyBulletSprite = new TGraphicCompound(DIMENSION);

        Sprite enemyShot = new MiteEnemyShot(AssetLoader.load(TARANTULA_ENEMY_SHOT), DIMENSION);

        enemyBulletSprite.add(enemyShot);

        return enemyBulletSprite;
    }

    public long timeExisted(){
        return currentTime - startTime;
    }
}