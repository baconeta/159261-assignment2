package space_resistance.game;

import space_resistance.actors.enemy.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class EnemyWave {
    private ArrayList<Enemy> wave;
    private int enemiesRemaining;
    // private Boss boss;

    public EnemyWave(int currentLevel) {
        // TODO implement wave spawning based on level parameters
    }

    public ArrayList<Enemy> getWave() {
        return wave;
    }

    public Enemy getRandomEnemyFromWave() {
        Random random = new Random();
        int index = random.nextInt(0, enemiesRemaining);
        Enemy enemy = wave.get(index);
        wave.remove(index);
        return enemy;
    }

    //public Boss getBoss() {
    //    return boss;
    //}
}
