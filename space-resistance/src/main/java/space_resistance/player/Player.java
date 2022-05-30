package space_resistance.player;

import space_resistance.actors.pickup.PickupType;

public class Player {
    public static final int STARTING_HEALTH = 100;
    public static final int STARTING_SHIELD = 0;

    private final PlayerNumber playerNumber;
    private final PlayerControls playerControls;
    private int score = 0;
    private int health = STARTING_HEALTH;
    private int shieldValue = STARTING_SHIELD;
    private boolean shieldsOn = false;
    private boolean isDead = false;
    private boolean hasMissile = false;
    private long missileStartTime;

    public Player(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
        this.playerControls = PlayerControls.controlsForPlayer(playerNumber);
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
            case MISSILE -> pickupMissile();
        }
    }

    public boolean shieldEnabled() {
        return shieldsOn;
    }

    public int shieldHealth() {
        return shieldValue;
    }

    public void playerDied(){
        isDead = true;
    }

    public boolean dead() {
        return isDead;
    }

    public void pickupMissile() {
        hasMissile = true;
        missileStartTime = System.currentTimeMillis();
    }

    public boolean missileActive() {
        return hasMissile;
    }

    public void deactivateMissile() {
        hasMissile = false;
    }

    public long missileTimer() {
        return missileStartTime;
    }

}
