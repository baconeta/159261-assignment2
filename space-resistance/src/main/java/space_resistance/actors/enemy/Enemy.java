package space_resistance.actors.enemy;

import space_resistance.game.GameWorld;
import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;

import java.awt.*;

public abstract class Enemy extends Actor {
    protected static String SHIP_SPRITE = null;

    protected int health = 100;
    protected static final Dimension DIMENSION = new Dimension(72, 72);

    protected int scoreWorth = 0;

    protected final GameWorld world;
    protected Point velocity = new Point(0, 1);
    public abstract Enemy spawnAt(GameWorld world, Point origin);

    protected Enemy(GameWorld world, Point origin) {
        this.world = world;
        graphic = initSprite();
        setOrigin(origin);
    }

    protected TGraphicCompound initSprite() {
        return null;
    }

    public void update() {
        this.setOrigin(new Point(this.origin.x + velocity.x, this.origin.y+ velocity.y));
        if (health < 0){
            this.destroy();
        }
    }
}
