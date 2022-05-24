package space_resistance.player;

public class Player {
    public static final int STARTING_HEALTH = 100;

    private final PlayerNumber playerNumber;
    private final PlayerControls playerControls;
    private int score;
    private int health;

    public Player(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
        this.playerControls = PlayerControls.controlsForPlayer(playerNumber);
        health = STARTING_HEALTH;
        score = 0;
    }

    public void increaseScore(int increaseAmount) {
        score+= increaseAmount;
    }

    public void reduceHealth(int damage) {
        health -= damage;
    }

    public int score() {
        return score;
    }

    public int healthRemaining() {
        return health;
    }

    public PlayerNumber playerNumber() {
        return playerNumber;
    }

    public PlayerControls controls() {
        return playerControls;
    }
}
