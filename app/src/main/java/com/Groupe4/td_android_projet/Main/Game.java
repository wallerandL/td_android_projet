package com.Groupe4.td_android_projet.Main;

import android.app.GameState;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.Groupe4.td_android_projet.R;
import com.Groupe4.td_android_projet.gamestates.Menu;
import com.Groupe4.td_android_projet.gamestates.Playing;

public class Game    {
    private SurfaceHolder holder;
    private Menu menu;
    private Playing playing;
    private GameState currentGameState = GameState.MENU;
    private Paint yellowPaint;

    public Game(SurfaceHolder holder)
    {
        this.holder=holder;
        initGameStates();
    }

    public void update(double delta)
    {
        switch (currentGameState) {
            case MENU: menu.update(delta);
                break;
            case PLAYING: playing.update(delta);
                break;
        }


    }
    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);


        switch (currentGameState){
            case MENU: menu.render(c);
                break;
            case PLAYING:playing.render(c);
                break;
        }
        /* float stripeWidth = 200f; // ajustez la largeur de la bande selon vos besoins
        float screenWidth = getWidth();
        float stripeLeft = screenWidth - stripeWidth;
        float stripeTop = 0;
        float stripeRight = screenWidth;
        float stripeBottom = getHeight();

        c.drawRect(stripeLeft,stripeTop, stripeRight, stripeBottom, yellowPaint);
        */
        holder.unlockCanvasAndPost(c);


    }

    private void initGameStates() {
        menu = new Menu(this);
        playing=new Playing(this);
    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState){
            case MENU:menu.touchEvents(event);
                break;
            case PLAYING:playing.touchEvents(event);
                break;
        }
    }

    public enum GameState{
        MENU, PLAYING;
    }
    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }
}
