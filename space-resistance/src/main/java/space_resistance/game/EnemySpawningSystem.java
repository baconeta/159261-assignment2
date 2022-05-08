package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import java.awt.*;

// TODO The boss class must notify the spawning system when it dies so we can prepare and start the next wave
public class EnemySpawningSystem {
    private int currentLevel;
    private EnemyWave currentWave;
    private long timeBeforeWaveStart;
    private long timeLastWaveGenerated;
    private long timeLastEnemySpawned;
    private GameWorld gameWorld;

    public SpawnState getCurrentState() {
        return currentState;
    }

    private enum SpawnState {
        PRE_WAVE,
        SPAWNING,
        BOSS,
        POST_WAVE,
        DEFAULT;
    }

    private SpawnState currentState = SpawnState.DEFAULT;

    public EnemySpawningSystem(GameWorld gw) {
        gameWorld = gw;
        currentLevel = 1;
        generateEnemyWave();
    }

    public void update() {}

    private void generateEnemyWave() {
        // based on the currentLevel value, we need to create an Array of enemies that will spawn in the next wave
        // once this function is called, we record the time so that we can check how much time has passed between
        // the start of the wave and the first spawn of the wave. This enables there to be a tiny bit or breathing
        // time between successive "levels".

        // TODO add in creation of wave data
        currentWave = new EnemyWave(currentLevel);
        currentState = SpawnState.PRE_WAVE;
        timeLastWaveGenerated = System.currentTimeMillis();
    }

    public void bossDestroyed() {
        currentLevel += 1;
        generateEnemyWave();
    }

    private void SpawnEnemy() {
        Enemy enemy = currentWave.getRandomEnemyFromWave();
        Point spawnLocation = new Point(0,0); // TODO generalise and randomise spawnLocation
//        enemy.spawnAt(gameWorld, spawnLocation)
    }
}
