package com.Groupe4.td_android_projet.Main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.environement.GameMap;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();

    private Random rand = new Random();
    private Paint yellowPaint = new Paint();
    private boolean movePlayer;
    private SurfaceHolder holder;
    private float x,y;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    Game game;
    private GameLoop gameLoop;
    private GameMap testMap;
    private Skeleton skeleton;
    private Allies allies;

    private ArrayList<Skeleton> skeletons;
    public GamePanel(Context context) {
        super(context);
        skeletons = new ArrayList<>();
        holder = getHolder();
        holder.addCallback(this);
        game = new Game(holder);
        yellowPaint.setColor(Color.rgb(255,140,0));
    }

    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        float stripeWidth = 200f; // ajustez la largeur de la bande selon vos besoins
        float screenWidth = getWidth();
        float stripeLeft = screenWidth - stripeWidth;
        float stripeTop = 0;
        float stripeRight = screenWidth;
        float stripeBottom = getHeight();

        c.drawRect(stripeLeft,stripeTop, stripeRight, stripeBottom, yellowPaint);

        holder.unlockCanvasAndPost(c);
    }


   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.touchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x=event.getX();
            y=event.getY();
            render();

        }

        return true;
    }
*/
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
