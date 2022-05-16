package space_resistance.actors.enemy;

import space_resistance.actors.bullet.MiteEnemyBullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.MiteShip;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class MiteEnemy extends Enemy {

    private final ArrayList<MiteEnemyBullet> bullets = new ArrayList<>();

    private MiteEnemy(GameWorld world, TPoint origin) {
        super(world, origin);
        this.scoreWorth = 100;
    }

    public static Enemy spawnAt(GameWorld world, TPoint origin) {
        var miteEnemy = new MiteEnemy(
                world,
                origin);
        world.add(miteEnemy);
        return miteEnemy;
    }

    @Override
    public TGraphicCompound initSprite() {
        SHIP_SPRITE = "MiteEnemy.png";
        TGraphicCompound miteSprite = new TGraphicCompound(DIMENSION);
        Sprite miteShip = new MiteShip(AssetLoader.load(SHIP_SPRITE), DIMENSION);
        miteSprite.add(miteShip);

        return miteSprite;
    }

    // TODO: Remove magic numbers
    @Override
    public void update() {
        super.update();
        for (MiteEnemyBullet bullet : bullets) {
            bullet.update();
        }
        if (bullets.size() < (Math.random() * 40)) {
            if (bullets.size() >= 1) {
                // Delay shots
                if (bullets.get(bullets.size() - 1).timeExisted() > 150) {
                    bullets.add(MiteEnemyBullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y + 20)));
                }
            } else {
                bullets.add(MiteEnemyBullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y + 20)));
            }
        } else {
            final Random RANDOM = new Random();
            if (bullets.get(bullets.size() - 1).timeExisted()
                    > RANDOM.nextInt(2100 - 1300)
                    + 1300) { // Shoot in bursts so that player isn't bombarded with constant shots from
                // the enemy ship
                for (MiteEnemyBullet bullet : bullets) {
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }
}
