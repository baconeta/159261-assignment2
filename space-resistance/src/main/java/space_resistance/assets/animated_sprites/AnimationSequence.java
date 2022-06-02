package space_resistance.assets.animated_sprites;

import tengine.world.GridSquare;

import java.awt.*;
import java.util.ArrayList;

class AnimationSequence {
    protected static ArrayList<GridSquare> getGridSquares(Dimension sequenceGrid) {
        int numFrames = sequenceGrid.width * sequenceGrid.height;
        ArrayList<GridSquare> sequence = new ArrayList<>(numFrames);
        for (int i = 0; i < numFrames; ++i) {
            sequence.add(new GridSquare(i / sequenceGrid.width, i % sequenceGrid.width));
        }

        return sequence;
    }
}
