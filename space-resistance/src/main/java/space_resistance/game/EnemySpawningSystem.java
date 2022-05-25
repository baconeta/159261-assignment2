package space_resistance.game;

import space_resistance.actors.enemy.GoliathEnemy;
import space_resistance.assets.FontBook;
import tengine.geom.TPoint;
import tengine.graphics.components.text.TLabel;

import java.awt.*;

public class EnemySpawningSystem {
    private enum SpawnState {
        PRE_WAVE, SPAWNING, BOSS, POST_WAVE, DEFAULT
    }

    private static final TPoint NEW_LEVEL_LABEL_ORIGIN = new TPoint(245, 280);

    private final GameWorld gameWorld;

    private EnemyWave currentWave;
    private long timeLastWaveGenerated;
    private long timeLastEnemySpawned;
    private SpawnState currentState = SpawnState.DEFAULT;

    private int currentLevel = 1;
    private final TLabel newLevelGraphic = new TLabel("Level" + currentLevel, NEW_LEVEL_LABEL_ORIGIN);

    public EnemySpawningSystem(GameWorld gw) {
        gameWorld = gw;
        newLevelGraphic.setFont(FontBook.shared().scoreBoardFont());
        newLevelGraphic.setColor(Color.white);

        generateEnemyWave();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        switch (currentState) {
            case PRE_WAVE -> {
                showNewLevelLabel();

                if ((currentTime - timeLastWaveGenerated) > currentWave.delayBeforeWave()) {
                    currentState = SpawnState.SPAWNING;
                }
            }
            case SPAWNING -> {
                newLevelGraphic.removeFromParent();

                if ((currentTime - timeLastEnemySpawned) > currentWave.delayBetweenSpawns()) {
                    spawnEnemy();
                }
            }
            case BOSS, POST_WAVE, DEFAULT -> {}
        }
    }

    public void levelUp() {
        currentState = SpawnState.POST_WAVE;
        currentLevel += 1;
        generateEnemyWave();
    }

    public GameWorld gameWorld() {
        return gameWorld;
    }

    private void showNewLevelLabel() {
        newLevelGraphic.setText("Level " + currentLevel);
        gameWorld.canvas().add(newLevelGraphic);
    }

    private void generateEnemyWave() {
        currentWave = new EnemyWave(currentLevel);
        currentWave.getWave().forEach(e -> {
            e.setBulletsPerBarrageMin(e.bulletsPerBarrageMin() + 2 * currentLevel);
            e.setBulletsPerBarrageMax(e.bulletsPerBarrageMax() + 2 * currentLevel);
        });
        currentState = SpawnState.PRE_WAVE;
        timeLastWaveGenerated = System.currentTimeMillis();
    }

    private void spawnEnemy() {
        if (currentWave.enemiesRemaining() > 0) {
            var enemy = currentWave.randomEnemyFromWave();
            enemy.spawnInWorld(gameWorld);
        } else {
            currentState = SpawnState.BOSS;
            GoliathEnemy boss = currentWave.getBoss();
            boss.spawnBoss(this);
        }
        timeLastEnemySpawned = System.currentTimeMillis();
    }
}
