package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.game.GameState;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

public class HeadsUpDisplay extends TGraphicCompound {
    private static final int PAUSE_LABEL_OFFSET = 125;
    private static final int HEALTH_LABEL_OFFSET = 10;
    private static final int SCREEN_BOTTOM_OFFSET = 15;
    private static final int SCORE_LABEL_OFFSET = 200;

    private final TLabel healthLabel = new TLabel("");
    private final TLabel scoreLabel = new TLabel("");

    private final GameState state;

    public HeadsUpDisplay(Dimension dimension, GameState state) {
        super(dimension);
        this.state = state;

        // Health Label
        healthLabel.setText("Health: " + state.playerOne().healthRemaining());
        healthLabel.setFont(FontBook.shared().hudFont());
        healthLabel.setColor(Colors.Text.PRIMARY);
        healthLabel.setOrigin(new TPoint(HEALTH_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));

        // Score Label
        scoreLabel.setText("Score: " + state.playerOne().score());
        scoreLabel.setFont(FontBook.shared().scoreFont());
        scoreLabel.setColor(Colors.Text.PRIMARY);
        scoreLabel.setOrigin(new TPoint(SCORE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));

        // Pause instruction
        TLabel pauseLabel = new TLabel("P: pause");
        pauseLabel.setFont(FontBook.shared().hudFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new TPoint(dimension.width - PAUSE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));

        addAll(healthLabel, scoreLabel, pauseLabel);
    }

    @Override
    public void update(double dtMillis) {
        if (state.playerOne().shieldEnabled()) {
            healthLabel.setText("Health: " + state.playerOne().healthRemaining() + state.playerOne().shieldHealth());
            healthLabel.setColor(Colors.Text.SHIELD_ENABLED);
        } else {
            healthLabel.setText("Health: " + state.playerOne().healthRemaining());
            healthLabel.setColor(Colors.Text.PRIMARY);
        }

        scoreLabel.setText("Score: " + state.playerOne().score());

        super.update(dtMillis);
    }
}
