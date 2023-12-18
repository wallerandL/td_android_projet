package com.Groupe4.td_android_projet.events;

import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.REPTIL;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.SKELLETON;

import android.annotation.SuppressLint;

import com.Groupe4.td_android_projet.gamestates.Playing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WaveManager {
    private Playing playing;
    private Random rand = new Random();
    private static ArrayList<Waves> waves=new ArrayList<>();
    private int enemySpawnTickLimit=60*1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex;
    private static int waveIndex;
    public WaveManager(Playing playing){
        this.playing=playing;
        creatWaves();
    }

    public static boolean isTherMoreWaves() {
        return waveIndex +1 < waves.size();
    }

    public void increaseWaveIndex(){
        waveIndex++;

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
        waves.add(new Waves(new ArrayList<Integer>(Arrays.asList(0))));
        waves.add(new Waves(new ArrayList<Integer>(Arrays.asList(1,rand.nextInt(2),rand.nextInt(2),rand.nextInt(2)))));
    }

    public ArrayList<Waves> getWaves(){
        return waves;
    }
    public boolean isTimeForNewEnemyq(){
        return enemySpawnTick>=enemySpawnTickLimit;
    }
    public boolean isThereMoreEnemiesInWaves(){
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public void resetEnemyIndex() {
        enemyIndex=0;
    }
}
