package com.Groupe4.td_android_projet.Main;

public class GameLoop implements Runnable{
    private Thread gameThread;
    private final Game game;

    public GameLoop(Game game) {
        this.game = game;
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
            game.update(delta);
            game.render();
            lastDelta=nowDelta;

            fps++;


            long now = System.currentTimeMillis();
            if (now - lastFPScheck >= 1000){
                System.out.println("FPS : " + fps + " " + System.currentTimeMillis());
                fps = 0;
                lastFPScheck += 1000;
            }
        }

    }

    public void startGameLoop(){
        gameThread.start();

    }
}
