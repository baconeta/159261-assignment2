package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import java.awt.*;

public class EnemySpawningSystem {
    private int currentLevel;
    private EnemyWave currentWave;
    private long timeLastWaveGenerated;
    private long timeLastEnemySpawned;
    private GameWorld gameWorld; // TODO is there a better way to handle this?
    private SpawnState currentState = SpawnState.DEFAULT;

    public EnemySpawningSystem(GameWorld gw) {
        gameWorld = gw;
        currentLevel = 1; // TODO magic number
        generateEnemyWave();
    }

    public SpawnState getCurrentState() {
        return currentState;
    }

    public void update() {
        // this could be a LOT more efficient, particularly in reference to calling a System function every frame
        // for now, this is sufficient until we have more implemented in the game
        long currentTime = System.currentTimeMillis();
        switch (currentState) {
            case PRE_WAVE:
                if ((currentTime - timeLastWaveGenerated) > currentWave.getMsBeforeWaveStarts()) {
                    currentState = SpawnState.SPAWNING;
                }
                break;
            case SPAWNING:
                if ((currentTime - timeLastEnemySpawned) > currentWave.getMsBetweenEnemySpawn()) {
                    SpawnEnemy();
                }
                break;
            case BOSS:
                // TODO Check if the boss exists in the world? or implement a notifier?
                break;
            case POST_WAVE:
            case DEFAULT:
                break;
        }
    }

    private void generateEnemyWave() {
        currentWave = new EnemyWave(currentLevel);
        currentState = SpawnState.PRE_WAVE;
        timeLastWaveGenerated = System.currentTimeMillis();
    }

    public void bossDestroyed() {
        currentState = SpawnState.POST_WAVE;
        currentLevel += 1;
        generateEnemyWave();
    }

    private void SpawnEnemy() {
        if (currentWave.getEnemiesRemaining() > 0) {
            Enemy enemy = currentWave.getRandomEnemyFromWave();
            Point spawnLocation = new Point(0, 0); // TODO generalise and randomise spawnLocation
            //        enemy.spawnAt(gameWorld, spawnLocation)
        } else {
            currentState = SpawnState.BOSS;
            //            Boss boss = currentWave.getBoss();
            Point spawnLocation = new Point(0, 0); // TODO generalise and randomise spawnLocation?
            //        boss.spawnAt(gameWorld, spawnLocation)

        }
        timeLastEnemySpawned = System.currentTimeMillis();
    }

    private enum SpawnState {
        PRE_WAVE,
        SPAWNING,
        BOSS,
        POST_WAVE,
        DEFAULT;
    }
}
