package com.Groupe4.td_android_projet;

import android.util.Log;

import com.Groupe4.td_android_projet.GamePanel;

public class GameLoop implements Runnable{
    private Thread gameThread;
    private GamePanel gamePanel;

    public GameLoop(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        gameThread = new Thread(this);

    }
    @Override
    public void run() {

        long lastFPScheck = System.currentTimeMillis();
        int fps =0;

        long lastDelta = System.nanoTime();
        long nanoSec=1_000_000_000;

        while (true){

            long nowDelta = System.nanoTime();
            double timeSinceLaseDelta = nowDelta - lastDelta;
            double delta = timeSinceLaseDelta / nanoSec;
            gamePanel.update(delta);
            gamePanel.render();
            lastDelta=nowDelta;

            fps++;
            //Log.e("fps", String.valueOf(fps));
        }

    }

    public void startGameLoop(){
        gameThread.start();

    }
}
