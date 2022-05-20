package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import tengine.geom.TPoint;

import java.util.ArrayList;
import java.util.Iterator;

// TODO remove all println statements before publish
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
        // this could be a LOT more efficient, particularly in reference to calling a System function
        // each frame.
        // for now, this is sufficient until we have more implemented in the game...
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
                // TODO Check if the boss exists in the world? or implement a notifier?
                break;
            case POST_WAVE:
            case DEFAULT:
                break;
        }
        updateEnemiesInWorld(); // this should probably be done a different way but for now it works
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
            //            Boss boss = currentWave.getBoss();
            TPoint spawnLocation = new TPoint(0, 0); // TODO generalise and randomise spawnLocation?
            //        boss.spawnAt(gameWorld, spawnLocation)

        }
        timeLastEnemySpawned = System.currentTimeMillis();
    }

    private enum SpawnState {
        PRE_WAVE, SPAWNING, BOSS, POST_WAVE, DEFAULT
    }
}
