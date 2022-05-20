package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.enemy.GoliathEnemy;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemySpawningSystem {
    private final GameWorld gameWorld;
    private int currentLevel;
    private EnemyWave currentWave;
    private long timeLastWaveGenerated;
    private long timeLastEnemySpawned;
    private SpawnState currentState = SpawnState.DEFAULT;
    private ArrayList<Enemy> enemiesSpawned;

    public EnemySpawningSystem(GameWorld gw) {
        gameWorld = gw;
        currentLevel = 1;
        generateEnemyWave();
        enemiesSpawned = new ArrayList<>();
    }

    public SpawnState getCurrentState() {
        return currentState;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        switch (currentState) {
            case PRE_WAVE:
                if ((currentTime - timeLastWaveGenerated) > currentWave.delayBeforeWave()) {
                    currentState = SpawnState.SPAWNING;
                }
                break;
            case SPAWNING:
                if ((currentTime - timeLastEnemySpawned) > currentWave.delayBetweenSpawns()) {
                    SpawnEnemy();
                }
                break;
            case BOSS:
            case POST_WAVE:
            case DEFAULT:
                break;
        }
        updateEnemiesInWorld();
    }

    private void updateEnemiesInWorld() {
        Iterator<Enemy> iterator = enemiesSpawned.iterator();
        // An iterator is required to allow removing an element during iteration
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            if (e == null) { continue; }
            if (e.isDead()) {
                iterator.remove();
                continue;
            }
            e.update();
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
        if (currentWave.enemiesRemaining() > 0) {
            var enemy = currentWave.randomEnemyFromWave();
            enemy.spawnInWorld(gameWorld);
            enemiesSpawned.add(enemy);
        } else {
            currentState = SpawnState.BOSS;
            GoliathEnemy boss = currentWave.getBoss();
            boss.spawnBoss(this);
            enemiesSpawned.add(boss);
    }
        timeLastEnemySpawned = System.currentTimeMillis();
    }

    private enum SpawnState {
        PRE_WAVE, SPAWNING, BOSS, POST_WAVE, DEFAULT
    }
}
