package tengine.world;

import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {
    protected TGraphicCompound canvas;
    protected List<Actor> actors;

    public World(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
        actors = new CopyOnWriteArrayList<>();
    }

    public void add(Actor actor) {
        actors.add(actor);
        actor.addToWorld(this);
        canvas.add(actor.graphic());
    }

    public void add(Actor... actors) {
        for (var actor : actors) {
            add(actor);
        }
    }

    public void add(List<Actor> actors) {
        actors.forEach(this::add);
    }

    public void remove(Actor actor) {
        actors.remove(actor);
    }

    public TGraphicCompound canvas() {
        return canvas;
    }

    public List<Actor> actors() {
        return actors;
    }
}
