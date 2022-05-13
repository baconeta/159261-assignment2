package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.game.GameState;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

public class HeadsUpDisplay extends TGraphicCompound {
    public HeadsUpDisplay(Dimension dimension, GameState state) {
        super(dimension);

        Scoreboard p1Scoreboard = Scoreboard.playerOneScoreboard(state.playerOne(), state.gameConfig());
        int scoreboardX = dimension.width - p1Scoreboard.width();
        int scoreboardY = (int) (p1Scoreboard.height() * 1.5);
        p1Scoreboard.setOrigin(new Point(scoreboardX, scoreboardY));

        // Add pause instruction

        TLabel pauseLabel = new TLabel("P: pause");
        pauseLabel.setFont(FontBook.shared().scoreBoardFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new Point(dimension.width - 175, dimension.height - 15));
        addAll(p1Scoreboard, pauseLabel);

        // Add Health Label

        TLabel healthLabel = new TLabel("Health: " + state.playerOne().healthRemaining());
        healthLabel.setFont(FontBook.shared().scoreBoardFont());
        healthLabel.setColor(Colors.Text.PRIMARY);
        healthLabel.setOrigin(new Point(55, dimension.height - 15));
        addAll(p1Scoreboard, healthLabel);

        // Add Health Label

        TLabel scoreLabel = new TLabel("Score: " + state.playerOne().score());
        scoreLabel.setFont(FontBook.shared().scoreBoardFont());
        scoreLabel.setColor(Colors.Text.PRIMARY);
        scoreLabel.setOrigin(new Point(255, dimension.height - 15));
        addAll(p1Scoreboard, scoreLabel);
    }
}
