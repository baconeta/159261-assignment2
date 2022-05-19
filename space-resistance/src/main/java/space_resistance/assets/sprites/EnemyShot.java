package space_resistance.assets.sprites;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class EnemyShot extends Sprite {
    private static final String[] ENEMY_SHIP_SPRITES = {"MiteEnemyShot.png", "GrasshopperEnemyShot.png", "TarantulaEnemyShot.png", "GrasshopperEnemyShot.png"};
    public EnemyShot(EnemyType enemyType, Dimension dimension) {
        super(getShipSprite(enemyType), dimension);
    }
    private static InputStream getShipSprite(EnemyType shipType){
        switch (shipType){
            case Mite -> {
                return AssetLoader.load(ENEMY_SHIP_SPRITES[0]);
            }
            case Grasshopper -> {
                return AssetLoader.load(ENEMY_SHIP_SPRITES[1]);
            }
            case Tarantula -> {
                return AssetLoader.load(ENEMY_SHIP_SPRITES[2]);
            }
            case BossGoliath -> {
                return AssetLoader.load(ENEMY_SHIP_SPRITES[3]); // TODO temporary sprite
            }
        }
        return null;
    }
}
