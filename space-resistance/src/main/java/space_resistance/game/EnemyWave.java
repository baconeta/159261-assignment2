package space_resistance.game;

import space_resistance.actors.enemy.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class EnemyWave {
    private ArrayList<Enemy> wave;
    private int enemiesRemaining;
    private int msBetweenEnemySpawn;
    private int msBeforeWaveStarts;
    // private Boss boss;

    public EnemyWave(int currentLevel) {
        wave = new ArrayList<>(); // TODO implement wave spawning based on level parameters
        msBetweenEnemySpawn = 5000; // TODO magic number
        msBeforeWaveStarts = 5000; // TODO magic number
        enemiesRemaining = wave.size();
    }

    public ArrayList<Enemy> getWave() {
        return wave;
    }

    public Enemy getRandomEnemyFromWave() {
        Random random = new Random();
        int index = random.nextInt(0, enemiesRemaining);
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
