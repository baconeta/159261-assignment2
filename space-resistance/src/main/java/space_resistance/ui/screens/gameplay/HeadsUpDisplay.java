package space_resistance.ui.screens.gameplay;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.game.GameState;
import space_resistance.settings.MultiplayerMode;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

public class HeadsUpDisplay extends TGraphicCompound {
    private static final int SCREEN_BOTTOM_OFFSET = 15;
    private static final int PAUSE_LABEL_OFFSET = 260;
    private static final int SCOREBOARD_OFFSET = 15;

    public HeadsUpDisplay(Dimension dimension, GameState state) {
        super(dimension);

        Scoreboard playerOneScoreboard = new Scoreboard(state.playerOne());
        playerOneScoreboard.setOrigin(new TPoint(SCOREBOARD_OFFSET, dimension.height - playerOneScoreboard.height()));

        // TODO: Include this when implementing two player
        if (state.gameConfig().multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            // Scoreboard playerTwoScoreboard = new Scoreboard(state.playerTwo());
            // TODO: set origin to right hand side of screen
            // add(playerTwoScoreboard);
        }

        // Pause instruction
        TLabel pauseLabel = new TLabel("P: pause");
        pauseLabel.setFont(FontBook.shared().pauseFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        pauseLabel.setOrigin(new TPoint(PAUSE_LABEL_OFFSET, dimension.height - SCREEN_BOTTOM_OFFSET));

        addAll(playerOneScoreboard, pauseLabel);
    }
}
