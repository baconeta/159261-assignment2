package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.player.Player;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

class Scoreboard extends TGraphicCompound {
    private static final Dimension DIMENSION = new Dimension(200, 50);
    private static final int HEALTH_LABEL_OFFSET = 15;
    private static final int SCORE_LABEL_OFFSET = 40;

    private final TLabel healthLabel = new TLabel("");
    private final TLabel scoreLabel = new TLabel("");

    private final Player player;

    public Scoreboard(Player player) {
        super(DIMENSION);

        this.player = player;

        // Health Label
        healthLabel.setText("Health: " + player.healthRemaining());
        healthLabel.setFont(FontBook.shared().hudFont());
        healthLabel.setColor(Colors.Text.PRIMARY);
        healthLabel.setOrigin(new TPoint(0, HEALTH_LABEL_OFFSET));

        // Score Label
        scoreLabel.setText("Score: " + player.score());
        scoreLabel.setFont(FontBook.shared().hudFont());
        scoreLabel.setColor(Colors.Text.PRIMARY);
        scoreLabel.setOrigin(new TPoint(0, SCORE_LABEL_OFFSET));

        addAll(healthLabel, scoreLabel);
    }

    @Override
    public void update(double dtMillis) {
        if (player.shieldEnabled()) {
            healthLabel.setText("Health: " + (player.healthRemaining() + player.shieldHealth()));
            healthLabel.setColor(Colors.Text.SHIELD_ENABLED);
        } else if (player.dead()) {
            healthLabel.setText("Health: DEAD");
            healthLabel.setColor(Colors.Text.PLAYER_DEAD);
        } else {
            healthLabel.setText("Health: " + player.healthRemaining());
            healthLabel.setColor(Colors.Text.PRIMARY);
        }

        scoreLabel.setText("Score: " + player.score());

        super.update(dtMillis);
    }
}
