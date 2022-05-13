package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.enemy.MiteEnemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.max;

public class EnemyWave {
    // Game design settings
    private static final int minEnemiesPerWave = 10;
    private static final int minBetweenSpawnMs = 500;
    private final int initialMillisecondsBetweenSpawns = 5100;
    private final int initialMillisecondsBeforeWave = 5000;
    private final int spawnSpeedStep = 100; // how many ms faster enemies spawn per level.

    private static final Random RANDOM = new Random();
    private ArrayList<Enemy> wave;
    private int enemiesRemaining;
    private final int level;
    private int enemiesPerSpawn;

    // private Boss boss;

    public EnemyWave(int currentLevel, GameWorld gw) {
        level = currentLevel;
        GenerateWave(gw);
        enemiesRemaining = wave.size();
        enemiesPerSpawn = level;
    }

    private void GenerateWave(GameWorld gw) {
        wave = new ArrayList<>();
        int totalEnemies = RANDOM.nextInt(minEnemiesPerWave, minEnemiesPerWave + level);
        for (int i = 0; i < totalEnemies; i++) {
            // This should also select an enemy type based on weight. Could some
            // enemy parameters be set based on the current level or difficulty?
            wave.add(new MiteEnemy(gw, new Point()));
        }
        // boss = new Boss(); // only create one boss per level
    }

    public ArrayList<Enemy> getWave() {
        return wave;
    }

    public Enemy randomEnemyFromWave() {
        int index = RANDOM.nextInt(0, enemiesRemaining);
        var enemy = wave.get(index);
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
        return max(initialMillisecondsBetweenSpawns - spawnSpeedStep * level, minBetweenSpawnMs);
    }

    public int delayBeforeWave() {
        return initialMillisecondsBeforeWave;
    }

    public int enemiesPerSpawn() {
        return enemiesPerSpawn;
    }
}