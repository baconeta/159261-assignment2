package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.game.GameState;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

public class HeadsUpDisplay extends TGraphicCompound {
    private TLabel healthLabel;
    private TLabel scoreLabel;
    private Scoreboard p1Scoreboard;

    private static final int PAUSE_LABEL_OFFSET = 150;
    private static final int HEALTH_LABEL_OFFSET = 30;
    private static final int SCREEN_BOTTOM_OFFSET = 15;
    private static final int SCORE_LABEL_OFFSET = 255;

    public HeadsUpDisplay(Dimension dimension, GameState state) {
        super(dimension);
        p1Scoreboard = Scoreboard.playerOneScoreboard(state.playerOne(), state.gameConfig());
        int scoreboardX = dimension.width - p1Scoreboard.width();
        int scoreboardY = (int) (p1Scoreboard.height() * 1.5);
        p1Scoreboard.setOrigin(new TPoint(scoreboardX, scoreboardY));

        // Add pause instruction
        TLabel pauseLabel = new TLabel("P: pause");
        pauseLabel.setFont(FontBook.shared().scoreBoardFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new TPoint(dimension.width - PAUSE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));
        addAll(p1Scoreboard, pauseLabel);

        // Add Health Label
        if (!state.playerOne().shieldEnabled()) {
            healthLabel = new TLabel("Health: " + state.playerOne().healthRemaining());
            healthLabel.setColor(Colors.Text.PRIMARY);
        } else {
            healthLabel = new TLabel("Health: " + (state.playerOne().healthRemaining() + state.playerOne().shieldHealth()));
            healthLabel.setColor(Colors.Text.SHIELD_ENABLED);
        }
        healthLabel.setFont(FontBook.shared().scoreBoardFont());
        healthLabel.setOrigin(new TPoint(HEALTH_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));
        addAll(p1Scoreboard, healthLabel);

        // Add Score Label
        scoreLabel = new TLabel("Score: " + state.playerOne().score());
        scoreLabel.setFont(FontBook.shared().scoreBoardFont());
        scoreLabel.setColor(Colors.Text.PRIMARY);
        scoreLabel.setOrigin(new TPoint(SCORE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));
        addAll(p1Scoreboard, scoreLabel);
    }
    public void update(GameState state){
        removeAll();
        p1Scoreboard = Scoreboard.playerOneScoreboard(state.playerOne(), state.gameConfig());
        int scoreboardX = dimension.width - p1Scoreboard.width();
        int scoreboardY = (int) (p1Scoreboard.height() * 1.5);
        p1Scoreboard.setOrigin(new TPoint(scoreboardX, scoreboardY));

        // Add pause instruction
        TLabel pauseLabel = new TLabel("P: pause");
        pauseLabel.setFont(FontBook.shared().scoreBoardFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new TPoint(dimension.width - PAUSE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));
        addAll(p1Scoreboard, pauseLabel);

        // Add Health Label
        if (!state.playerOne().shieldEnabled()) {
            healthLabel = new TLabel("Health: " + state.playerOne().healthRemaining());
            healthLabel.setColor(Colors.Text.PRIMARY);
        } else {
            healthLabel = new TLabel("Health: " + (state.playerOne().healthRemaining() + state.playerOne().shieldHealth()));
            healthLabel.setColor(Colors.Text.SHIELD_ENABLED);
        }
        healthLabel.setFont(FontBook.shared().scoreBoardFont());
        healthLabel.setOrigin(new TPoint(HEALTH_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));
        addAll(p1Scoreboard, healthLabel);

        // Add Score Label
        scoreLabel = new TLabel("Score: " + state.playerOne().score());
        scoreLabel.setFont(FontBook.shared().scoreBoardFont());
        double scoreLabelFontSize;
        // Equation for HUD score font size (x = player score):
        //             30
        //---------------------------------
        //  ( (x + 6500) / 6 ) * 1000

        scoreLabelFontSize = ((30.0 / ((state.playerOne().score() + 6500) / 6.0)) * 1000);
        scoreLabel.setFontSize((int) scoreLabelFontSize);
        scoreLabel.setColor(Colors.Text.PRIMARY);
        scoreLabel.setOrigin(new TPoint(SCORE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));
        addAll(p1Scoreboard, scoreLabel);
    }
}
