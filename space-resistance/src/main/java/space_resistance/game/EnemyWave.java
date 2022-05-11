package space_resistance.game;

import space_resistance.actors.enemy.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class EnemyWave {
    // Game design settings
    private static final int minEnemiesPerWave = 10;
    private final int initialMillisecondsBetweenSpawns = 5100;
    private final int initialMillisecondsBeforeWave = 5000;
    private final int spawnSpeedStep = 100; // how many ms faster enemies spawn per level.

    private static final Random RANDOM = new Random();
    private ArrayList<Enemy> wave;
    private int enemiesRemaining;
    private final int level;
    private int enemiesPerSpawn;

    // private Boss boss;

    public EnemyWave(int currentLevel) {
        level = currentLevel;
        GenerateWave();
        enemiesRemaining = wave.size();
        enemiesPerSpawn = level;
    }

    private void GenerateWave() {
        wave = new ArrayList<>();
        int totalEnemies = RANDOM.nextInt(minEnemiesPerWave, minEnemiesPerWave + level);
        for (int i = 0; i < totalEnemies; i++) {
            // TODO this should be a specific enemy and not the parent class.
            // This should also select an enemy type based on weight. Could some
            // enemy parameters be set based on the current level or difficulty?
            wave.add(new Enemy());
        }
        // boss = new Boss();
    }

    public ArrayList<Enemy> getWave() {
        return wave;
    }

    public Enemy randomEnemyFromWave() {
        int index = RANDOM.nextInt(0, enemiesRemaining);
        Enemy enemy = wave.get(index);
        wave.remove(index);
        enemiesRemaining -= 1;
        return enemy;
    }

    // public Boss getBoss() {
    //    return boss;
    // }

    public int enemiesRemaining() {
        return enemiesRemaining;
    }

    public int delayBetweenSpawns() {
        return initialMillisecondsBetweenSpawns - spawnSpeedStep * level;
    }

    public int delayBeforeWave() {
        return initialMillisecondsBeforeWave;
    }

    public int enemiesPerSpawn() {
        return enemiesPerSpawn;
    }
}
