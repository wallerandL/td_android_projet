package com.Groupe4.td_android_projet.events;

import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.SKELLETON;

import android.annotation.SuppressLint;

import com.Groupe4.td_android_projet.gamestates.Playing;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {
    public boolean isTimeForNewEnemy;
    private Playing playing;
    private ArrayList<Waves> waves=new ArrayList<>();
    private int enemySpawnTickLimit=60*1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex,waveIndex;
    public WaveManager(Playing playing){
        this.playing=playing;
        creatWaves();
    }

    @SuppressLint("SuspiciousIndentation")
    public void update(){
        if (enemySpawnTick < enemySpawnTickLimit)
        enemySpawnTick++;
    }

    public int getNextEnemy(){
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void creatWaves() {
        waves.add(new Waves(new ArrayList<Integer>(Arrays.asList(SKELLETON,SKELLETON,SKELLETON,SKELLETON,SKELLETON,SKELLETON,SKELLETON,SKELLETON,SKELLETON,SKELLETON))));
    }

    public ArrayList<Waves> getWaves(){
        return waves;
    }
    public boolean isTimeForNewEnemy(){
        return enemySpawnTick>=enemySpawnTickLimit;
    }
    public boolean isThereMoreEnemiesInWaves(){
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }
}
