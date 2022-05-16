package space_resistance.actors.enemy;

import space_resistance.actors.bullet.GrassHopperEnemyBullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.GrassHopperShip;
import space_resistance.game.GameWorld;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class GrassHopperEnemy extends Enemy {

    private final ArrayList<GrassHopperEnemyBullet> bullets = new ArrayList<>();

    public GrassHopperEnemy(GameWorld world, TPoint origin) {
        super(world, origin);
        graphic = initSprite();
        this.scoreWorth = 300;
    }

    @Override
    public GrassHopperEnemy spawnAt(GameWorld world, TPoint origin) {
        world.add(this);
        return this;
    }

    public TGraphicCompound initSprite() {
        SHIP_SPRITE = "GrasshopperEnemy.png";
        TGraphicCompound grassHopperSprite = new TGraphicCompound(DIMENSION);
        Sprite grasshoppership = new GrassHopperShip(AssetLoader.load(SHIP_SPRITE), DIMENSION);
        grassHopperSprite.add(grasshoppership);

        return grassHopperSprite;
    }

    @Override
    public void update() {
        super.update();
        for (GrassHopperEnemyBullet bullet : bullets) {
            bullet.update();
        }
        if (bullets.size() < Math.random() * 40) {
            if (bullets.size() >= 1) {
                // Delay shots
                if (bullets.get(bullets.size() - 1).timeExisted() > 150) {
                    bullets.add(
                            GrassHopperEnemyBullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y + 20)));
                }
            } else {
                bullets.add(
                        GrassHopperEnemyBullet.spawnAt(world, new TPoint(this.origin.x, this.origin.y + 20)));
            }
        } else {
            final Random RANDOM = new Random();
            if (bullets.get(bullets.size() - 1).timeExisted()
                    > RANDOM.nextInt(2100 - 1300)
                    + 1300) { // Shoot in bursts so that player isn't bombarded with constant shots from
                // the enemy ship
                for (GrassHopperEnemyBullet bullet : bullets) {
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }
}
