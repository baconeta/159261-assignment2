package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.enemy.EnemyConstants;
import space_resistance.actors.enemy.EnemyType;
import space_resistance.actors.enemy.GoliathEnemy;
import tengine.geom.TPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.max;
import static space_resistance.actors.enemy.EnemyType.*;

public class EnemyWave {
    // Static game design settings
    private static final int minEnemiesPerWave = 10;
    private static final int minBetweenSpawnMs = 500;
    private static final Random RANDOM = new Random();

    // Static spawn constants
    private static final int minSpawnX = 25;
    private static final int maxSpawnX = 575;
    private static final int spawnY = 20;

    private static final double TARANTULA_RATE_BY_LEVEL = 0.01;
    private static final double TARANTULA_BASE_RATE = 0.05;
    private static final double GRASSHOPPER_RATE_BY_LEVEL = 0.02;
    private static final double GRASSHOPPER_BASE_RATE = 0.05;

    // Game design setting constants
    private static final int initialMillisecondsBetweenSpawns = 4500;
    private static final int initialMillisecondsBeforeWave = 3000;
    private static final int spawnSpeedStep = 250;

    // This wave
    private final List<Enemy> wave = new ArrayList<>();
    private int enemiesRemaining;
    private final int level;

    private final double tarantulaSpawningRate;
    private final double grasshopperSpawningRate;

    private GoliathEnemy boss;

    public EnemyWave(int currentLevel) {
        level = currentLevel;

        grasshopperSpawningRate = GRASSHOPPER_BASE_RATE + (currentLevel * GRASSHOPPER_RATE_BY_LEVEL);
        tarantulaSpawningRate = TARANTULA_BASE_RATE + (currentLevel * TARANTULA_RATE_BY_LEVEL);

        generateWave();
        enemiesRemaining = wave.size();
    }

    private void generateWave() {
        int totalEnemies = RANDOM.nextInt(minEnemiesPerWave, minEnemiesPerWave + level);

        for (int i = 0; i < totalEnemies; i++) {
            var enemyType = randomEnemyType();
            wave.add(new Enemy(
                enemyType,
                new TPoint(
                    RANDOM.nextInt(minSpawnX, maxSpawnX - EnemyConstants.dimension(enemyType).width),
                    spawnY
                ),
                level)
            );
        }

        boss = new GoliathEnemy(
                GOLIATH,
                 new TPoint(
                    RANDOM.nextInt(minSpawnX, maxSpawnX - EnemyConstants.dimension(GOLIATH).width),
                    spawnY
                 ),
                 level);
    }

    private EnemyType randomEnemyType() {
        double spawnTypeValue = RANDOM.nextDouble();

        if (spawnTypeValue < tarantulaSpawningRate) {
            return TARANTULA;
        }

        if (spawnTypeValue < tarantulaSpawningRate + grasshopperSpawningRate) {
            return GRASSHOPPER;
        }

        return MITE;
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
}
