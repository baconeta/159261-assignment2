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
        // This is just an example, feel free to do what you like with it!
        TLabel pauseLabel = new TLabel("p: pause");
        pauseLabel.setFont(FontBook.shared().defaultFont());
        pauseLabel.setColor(Colors.Text.PRIMARY);
        // TEngine doesn't support calculating a label width yet, so we always have to manually place TLabels.
        // Sorry about the magic numbers :(
        pauseLabel.setOrigin(new Point(dimension.width - 85, dimension.height - 15));

        addAll(p1Scoreboard, pauseLabel);
    }
}
