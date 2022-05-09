package space_resistance.game;

import space_resistance.actors.enemy.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class EnemyWave {
    private static final Random RANDOM = new Random();
    private ArrayList<Enemy> wave;
    private int enemiesRemaining;
    private final int msBetweenEnemySpawn;
    private final int msBeforeWaveStarts;
    // private Boss boss;

    public EnemyWave(int currentLevel) {
        GenerateWave(currentLevel);
        msBetweenEnemySpawn = 5000; // TODO magic number
        msBeforeWaveStarts = 5000; // TODO magic number
        enemiesRemaining = wave.size();
    }

    private void GenerateWave(int currentLevel) {
        wave = new ArrayList<>();
        // TODO magic numbers and kinda weird logic
        int totalEnemies = RANDOM.nextInt(currentLevel + 5, currentLevel + 10);
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

    public Enemy getRandomEnemyFromWave() {
        int index = RANDOM.nextInt(0, enemiesRemaining);
        Enemy enemy = wave.get(index);
        wave.remove(index);
        enemiesRemaining -= 1;
        return enemy;
    }

    // public Boss getBoss() {
    //    return boss;
    // }

    public int getEnemiesRemaining() {
        return enemiesRemaining;
    }

    public int getMsBetweenEnemySpawn() {
        return msBetweenEnemySpawn;
    }

    public int getMsBeforeWaveStarts() {
        return msBeforeWaveStarts;
    }
}
