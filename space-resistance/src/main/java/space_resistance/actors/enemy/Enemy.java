package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;

import java.awt.*;

public abstract class Enemy extends Actor {
    protected static String SHIP_SPRITE = null;

    protected int health = 100;
    protected static final Dimension DIMENSION = new Dimension(72, 72);

    protected int scoreWorth = 0;

    protected final GameWorld world;
    protected TPoint velocity = new TPoint(0, 1);
    public abstract Enemy spawnAt(GameWorld world, TPoint origin);

    protected Enemy(GameWorld world, TPoint origin) {
        this.world = world;
        graphic = initSprite();
        setOrigin(origin);
    }

    protected TGraphicCompound initSprite() {
        return null;
    }

    public void update() {
        this.setOrigin(new TPoint(this.origin.x + velocity.x, this.origin.y+ velocity.y));
        if (health < 0){
            this.destroy();
        }
    }
}
