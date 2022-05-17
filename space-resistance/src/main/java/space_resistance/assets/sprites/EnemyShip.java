package space_resistance.assets.sprites;

import space_resistance.actors.enemy.EnemyType;
import space_resistance.assets.AssetLoader;
import tengine.graphics.components.sprites.Sprite;

import java.awt.*;
import java.io.InputStream;

public class EnemyShip extends Sprite {
    private static final String[] ENEMY_SHIP_SPRITES = {"MiteEnemy.png", "GrasshopperEnemy.png", "TarantulaEnemy.png"};
    public EnemyShip(EnemyType enemyType, Dimension dimension) {
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
        }
        return null;
    }
}
