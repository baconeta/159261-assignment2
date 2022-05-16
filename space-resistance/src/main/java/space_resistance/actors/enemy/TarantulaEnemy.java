package space_resistance.actors.enemy;

import space_resistance.actors.bullet.TarantulaEnemyBullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.TarantulaShip;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class TarantulaEnemy extends Enemy {

    private final ArrayList<TarantulaEnemyBullet> bullets = new ArrayList<>();

    public TarantulaEnemy(GameWorld world, TPoint origin) {
        super(world, origin);
        graphic = initSprite();
        this.scoreWorth = 500;
    }

    @Override
    public TarantulaEnemy spawnAt(GameWorld world, TPoint origin) {
        world.add(this);
        return this;
    }

    public TGraphicCompound initSprite() {
        SHIP_SPRITE = "TarantulaEnemy.png";
        TGraphicCompound tarantulaEnemy = new TGraphicCompound(DIMENSION);
        Sprite tarantulaShip = new TarantulaShip(AssetLoader.load(SHIP_SPRITE), DIMENSION);
        tarantulaEnemy.add(tarantulaShip);

        return tarantulaEnemy;
    }

    @Override
    public void update() {
        super.update();
        for (TarantulaEnemyBullet bullet : bullets) {
            bullet.update();
        }
        if (bullets.size() < (Math.random() * 40)) {
            if (bullets.size() >= 1) {
                // Delay shots
                if (bullets.get(bullets.size() - 1).timeExisted() > 150) {
                    bullets.add(
                            TarantulaEnemyBullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y + 20)));
                }
            } else {
                bullets.add(
                        TarantulaEnemyBullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y + 20)));
            }
        } else {
            final Random RANDOM = new Random();
            if (bullets.get(bullets.size() - 1).timeExisted()
                    > RANDOM.nextInt(2100 - 1300)
                    + 1300) { // Shoot in bursts so that player isn't bombarded with constant shots from
                // the enemy ship
                for (TarantulaEnemyBullet bullet : bullets) {
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }
}
