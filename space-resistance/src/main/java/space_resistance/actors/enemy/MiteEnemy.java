package space_resistance.actors.enemy;

import space_resistance.actors.bullet.MiteEnemyBullet;
import space_resistance.assets.AssetLoader;
import space_resistance.assets.sprites.MiteShip;
import space_resistance.game.GameWorld;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MiteEnemy extends Enemy{

    private final ArrayList<MiteEnemyBullet> bullets = new ArrayList<>();

    @Override
    public MiteEnemy spawnAt(GameWorld world, Point origin) {
        MiteEnemy enemy = new MiteEnemy(
                world,
                origin);

        world.add(enemy);
        return enemy;
    }

    public MiteEnemy(GameWorld world, Point origin){
        super(world, origin);
        graphic = initSprite();
        this.scoreWorth = 100;
    }
    public TGraphicCompound initSprite(){
        SHIP_SPRITE = "MiteEnemy.png";
        TGraphicCompound miteSprite = new TGraphicCompound(DIMENSION);
        Sprite miteship = new MiteShip(AssetLoader.load(SHIP_SPRITE), DIMENSION);
        miteSprite.add(miteship);

        return miteSprite;
    }
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
                    bullets.add(MiteEnemyBullet.spawnAt(world, new Point(this.origin.x, this.origin.y + 20)));
                }
            } else {
                bullets.add(MiteEnemyBullet.spawnAt(world, new Point(this.origin.x, this.origin.y + 20)));
            }
        } else {
            final Random RANDOM = new Random();
            if (bullets.get(bullets.size() - 1).timeExisted() > RANDOM.nextInt(2100 - 1300) + 1300) { // Shoot in bursts so that player isn't bombarded with constant shots from the enemy ship
                for (MiteEnemyBullet bullet: bullets){
                    bullet.destroy();
                }
                bullets.clear();
            }
        }
    }
}
