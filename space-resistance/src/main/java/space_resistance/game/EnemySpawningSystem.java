package space_resistance.game;

import space_resistance.actors.enemy.Enemy;
import space_resistance.actors.enemy.GoliathEnemy;
import space_resistance.assets.FontBook;
import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.TGraphicObject;
import tengine.graphics.components.text.TLabel;
import tengine.graphics.transforms.TScale;

import java.awt.*;
import java.util.ArrayList;

public class EnemySpawningSystem {
    public final GameWorld gameWorld;

    private int currentLevel;
    private EnemyWave currentWave;
    private long timeLastWaveGenerated;
    private long timeLastEnemySpawned;
    private SpawnState currentState = SpawnState.DEFAULT;

    public EnemySpawningSystem(GameWorld gw) {
        gameWorld = gw;
        currentLevel = 1;
        generateEnemyWave();
        currentWave.setSpawnSpeedStep(currentLevel * 100);
    }

    public void update() {
        boolean levelLabelRemoved = false;
        long currentTime = System.currentTimeMillis();
        TGraphicCompound newLevel = new TGraphicCompound(gameWorld.canvas().dimension());
        TLabel levelLabel = new TLabel("Level " + currentLevel, new TPoint(245, 280));
        newLevel.add(levelLabel);
        newLevel.setScale(1.0001);
        levelLabel.setFont(FontBook.shared().scoreBoardFont());
        levelLabel.setColor(Color.white);
        switch (currentState) {
            case PRE_WAVE:
                if ((currentTime - timeLastWaveGenerated) > currentWave.delayBeforeWave()) {
                    currentState = SpawnState.SPAWNING;
                } else {
                    gameWorld.canvas().add(newLevel);
                    levelLabelRemoved = false;
                }
                break;
            case SPAWNING:
                ArrayList<TGraphicObject> newLevelLabelToBeRemoved = new ArrayList<TGraphicObject>();
                if (!levelLabelRemoved){
                    for (TGraphicObject c : gameWorld.canvas().children()){
                        if (c.scale().equals(new TScale(1.0001, 1.0001))){
                            newLevelLabelToBeRemoved.add(c);
                        }
                    }
                    for (TGraphicObject c : newLevelLabelToBeRemoved){
                        c.setParent(null);
                        gameWorld.canvas().remove(c);
                    }
                }
                newLevelLabelToBeRemoved = null;
                levelLabelRemoved = true;
                if ((currentTime - timeLastEnemySpawned) > currentWave.delayBetweenSpawns()) {
                    SpawnEnemy();
                }
                break;
            case BOSS:
            case POST_WAVE:
            case DEFAULT:
                break;
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
        for (Enemy e : currentWave.getWave()){
            e.setBulletsPerBarrageMin(e.bulletsPerBarrageMin() + 5);
            e.setBulletsPerBarrageMax(e.bulletsPerBarrageMax() + 5);
        }
        generateEnemyWave();
        currentWave.setSpawnSpeedStep(currentLevel * 100);
    }

    private void SpawnEnemy() {
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

    private enum SpawnState {
        PRE_WAVE, SPAWNING, BOSS, POST_WAVE, DEFAULT
    }
}
