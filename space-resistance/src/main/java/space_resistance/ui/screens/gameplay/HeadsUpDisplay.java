package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.game.GameState;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

// TODO: Consider stacking the score and health above each other and placing them in the bottom left.
//   This would allow placing a second player's score on the bottom right, and moving the pause instruction into the
//   middle as well as making it smaller so it is subtler, e.g.
//    |                                                        |
//    | Health: 074                                Health: 085 |
//    | Score: 00300           p: pause           Score: 01200 |
//    +--------------------------------------------------------+
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
        healthLabel.setText("Health: " + padHealthText(state.playerOne().healthRemaining()));
        healthLabel.setFont(FontBook.shared().hudFont());
        healthLabel.setColor(Colors.Text.PRIMARY);
        healthLabel.setOrigin(new TPoint(HEALTH_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));

        // Score Label
        scoreLabel.setText("Score: " + padScoreText(state.playerOne().score()));
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
            healthLabel.setText("Health: " + padHealthText(state.playerOne().healthRemaining() + state.playerOne().shieldHealth()));
            healthLabel.setColor(Colors.Text.SHIELD_ENABLED);
        } else {
            healthLabel.setText("Health: " + padHealthText(state.playerOne().healthRemaining()));
            healthLabel.setColor(Colors.Text.PRIMARY);
        }

        scoreLabel.setText("Score: " + padScoreText(state.playerOne().score()));

        super.update(dtMillis);
    }

    private String padScoreText(int score) {
        if (score < 100) {
            return "0000" + score;
        } else if (score < 1000) {
            return "00" + score;
        } else if (score < 10000) {
            return "0" + score;
        }

        return "" + score;
    }

    private String padHealthText(int health) {
        if (health < 10) {
            return "00" + health;
        } else if (health < 100) {
            return "0" + health;
        }

        return "" + health;
    }
}
