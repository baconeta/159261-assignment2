package space_resistance.game;

import space_resistance.player.Player;
import space_resistance.player.PlayerNumber;
import space_resistance.settings.MultiplayerMode;

import java.util.Optional;

public class GameState {
    private final GameConfig gameConfig;
    private final Player playerOne;
    private Player playerTwo = null;

    public GameState(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        playerOne = new Player(PlayerNumber.PLAYER_ONE);
        if (gameConfig.multiplayerMode() == MultiplayerMode.MULTIPLAYER) {
            playerTwo = new Player(PlayerNumber.PLAYER_TWO);
        }
    }

    public GameConfig gameConfig() {
        return gameConfig;
    }

    public Player playerOne() {
        return playerOne;
    }

    public Player playerTwo() {
        return playerTwo;
    }

    /**
     * Returns the winner or none if a draw.
     */
    public Optional<Player> winner() {
        return switch(gameConfig.multiplayerMode()) {
            case SINGLE_PLAYER -> Optional.of(playerOne);
            case MULTIPLAYER -> {
                if (playerOne.score() == playerTwo.score()) {
                    yield Optional.empty();
                } else {
                    yield Optional.of(playerOne.score() > playerTwo.score() ? playerOne : playerTwo);
                }
            }
        };
    }

    public int maxScore() {
        return winner().orElse(playerOne).score();
    }
}
