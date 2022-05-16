package space_resistance.ui.screens.gameplay;

import space_resistance.assets.FontBook;
import space_resistance.game.GameConfig;
import space_resistance.player.Player;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

class Scoreboard extends TGraphicCompound {
    private final Player player;
    private final TLabel score;

    public static Scoreboard playerOneScoreboard(Player player, GameConfig gameConfig) {
        return new Scoreboard(player);
    }

    public static Scoreboard playerTwoScoreboard(Player player) {
        return new Scoreboard(player);
    }

    private Scoreboard(Player player) {
        super(new Dimension());

        this.player = player;

        score = new TLabel("");
        score.setFont(FontBook.shared().defaultFont());
        score.setColor(Color.BLACK);
        score.setOrigin(new TPoint(100, 100));

        addAll(score);
    }

    @Override
    public void update(double dtMillis) {
        score.setText(Integer.toString(player.score()));

        super.update(dtMillis);
    }
}
