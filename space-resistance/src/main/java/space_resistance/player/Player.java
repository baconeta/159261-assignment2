package space_resistance.player;

import space_resistance.actors.pickup.PickupType;

public class Player {
    public static final int STARTING_HEALTH = 100;
    public static final int STARTING_SHIELD = 0;

    private final PlayerNumber playerNumber;
    private final PlayerControls playerControls;
    private int score;
    private int health;
    private boolean shieldsOn;
    private int shieldValue;

    public Player(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
        this.playerControls = PlayerControls.controlsForPlayer(playerNumber);
        health = STARTING_HEALTH;
        score = 0;
        shieldValue = STARTING_SHIELD;
        shieldsOn = shieldValue > 0;
    }

    public void increaseScore(int increaseAmount) {
        score+= increaseAmount;
    }

    public void reduceHealth(int damage) {
        if (shieldsOn) {
            damage = damageShields(damage);
        }
        health -= damage;
    }

    private int damageShields(int damage) {
        if (shieldValue > damage) {
            shieldValue -= damage;
            return 0;
        }
        else {
            int preDamageShield = shieldValue;
            shieldValue = 0;
            shieldsOn = false;
            return damage - preDamageShield;
        }
    }

    public void addHealth(int healing) {
        health += healing;
        if (health > STARTING_HEALTH) { health = STARTING_HEALTH; }
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

    public void handlePickup(PickupType type) {
        switch(type) {
            case HEALTH -> addHealth(20);
            case SHIELD -> {
                shieldsOn = true;
                shieldValue += 25;
            }
            case MISSILE -> {}
        }
    }

    public boolean shieldEnabled() { return shieldsOn; }

    public int shieldHealth() { return shieldValue; }
}
