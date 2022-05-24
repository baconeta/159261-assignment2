package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.enemy.GoliathEnemy;
import tengine.geom.TPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.max;
import static space_resistance.actors.enemy.EnemyType.GOLIATH;
import static space_resistance.actors.enemy.EnemyType.MITE;

public class EnemyWave {
    // Static game design settings
    private static final int minEnemiesPerWave = 10;
    private static final int minBetweenSpawnMs = 500;
    private static final Random RANDOM = new Random();

    // Static spawn constants
    private static final int minSpawnX = 25;
    private static final int maxSpawnX = 575;
    private static final int spawnY = 50;
    private static final int spawnWidth = 72;
    private static final int spawnHeight = 72;

    // Spawn constants
    private final int spawnSpeedStep = 250; // how many ms faster enemies spawn per level.

    // Game design setting constants
    private final int initialMillisecondsBetweenSpawns = 4500;
    private final int initialMillisecondsBeforeWave = 3000;
    private final int bossHealthPerLevel = 2500;

    // This wave
    private List<Enemy> wave = new ArrayList<>();
    private int enemiesRemaining;
    private final int level;
    private final int enemiesPerSpawn;

    private GoliathEnemy boss;

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
            // This should also select an enemy type based on weight. Could some
            // enemy parameters be set based on the current level or difficulty?
            wave.add(new Enemy(MITE,
                new TPoint(RANDOM.nextInt(minSpawnX,maxSpawnX-spawnWidth), spawnY),
                new Dimension(spawnWidth, spawnHeight),
                100));
        }
        boss = new GoliathEnemy(GOLIATH,
                 new TPoint(RANDOM.nextInt(minSpawnX,maxSpawnX-spawnWidth*2), spawnY),
                 new Dimension(spawnWidth * 2, spawnHeight * 2), 1000);
        boss.setMaxHealth(bossHealthPerLevel * level);
    }

    public List<Enemy> getWave() {
        return wave;
    }

    public Enemy randomEnemyFromWave() {
        int index = RANDOM.nextInt(0, enemiesRemaining);
        var enemy = wave.get(index);
        wave.remove(index);
        enemiesRemaining -= 1;
        return enemy;
    }

     public GoliathEnemy getBoss() {
        return boss;
     }

    public int enemiesRemaining() {
        return enemiesRemaining;
    }

    public int delayBetweenSpawns() {
        return max(initialMillisecondsBetweenSpawns - spawnSpeedStep * (level - 1), minBetweenSpawnMs);
    }

    public int delayBeforeWave() {
        return initialMillisecondsBeforeWave;
    }

    public int enemiesPerSpawn() {
        return enemiesPerSpawn;
    }
}
