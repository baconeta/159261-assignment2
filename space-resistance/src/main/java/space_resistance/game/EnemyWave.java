package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.enemy.GoliathEnemy;
import tengine.geom.TPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.max;
import static space_resistance.actors.enemy.EnemyType.BossGoliath;
import static space_resistance.actors.enemy.EnemyType.Mite;

public class EnemyWave {
    // Game design settings
    private static final int minEnemiesPerWave = 10;
    private static final int minBetweenSpawnMs = 500;
    private static final Random RANDOM = new Random();
    private final int initialMillisecondsBetweenSpawns = 5100;
    private final int initialMillisecondsBeforeWave = 5000;
    private final int bossHealthPerLevel = 5000;

    // Spawn variables
    private final int spawnSpeedStep = 100; // how many ms faster enemies spawn per level.
    private static final int minSpawnX = 25;
    private static final int maxSpawnX = 575;
    private static final int spawnY = 50;
    private int spawnWidth = 72;
    private int spawnHeight = 72;

    // This wave
    private ArrayList<Enemy> wave;
    private int enemiesRemaining;
    private final int level;
    private final int enemiesPerSpawn;

    private GoliathEnemy boss;

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
            wave.add(new Enemy(Mite, gw,
                    new TPoint(RANDOM.nextInt(minSpawnX,maxSpawnX-spawnWidth), spawnY),
                    new Dimension(spawnWidth, spawnHeight), 100));
        }
        boss = new GoliathEnemy(BossGoliath, gw,
                 new TPoint(RANDOM.nextInt(minSpawnX,maxSpawnX-spawnWidth*2), spawnY),
                 new Dimension(spawnWidth * 2, spawnHeight * 2), 1000);
        boss.setMaxHealth(bossHealthPerLevel * level);
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

     public GoliathEnemy getBoss() {
        return boss;
     }

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
