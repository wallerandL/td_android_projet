package com.Groupe4.td_android_projet;

import  static com.Groupe4.td_android_projet.helpers.GameConstants.Sprite.DEFAULT_SIZE;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Sprite.DEFAULT_SIZE;

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
import com.Groupe4.td_android_projet.environement.GameMap;
import com.Groupe4.td_android_projet.entites.GameSheet;
import com.Groupe4.td_android_projet.helpers.GameConstants;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private Paint yellowPaint = new Paint();
    private SurfaceHolder holder;
    private float x,y;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    private GameMap testMap;
    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        yellowPaint.setColor(Color.rgb(255,140,0));
        redPaint.setColor(Color.RED);

        gameLoop = new GameLoop(this);

        int[][] spriteIds = {
                {18, 18, 18, 18,18,18,18,18,18,18,18,18,18,18},
                {18, 18, 18, 18,18,18,18,18,18,18,18,18,18,18},
                {18, 18, 18, 18,18,18,18,18,18,18,18,18,18,18},
                {9, 9,9, 9,9, 9,9, 9,9, 9,9, 9,9, 9,9},
                {60, 60, 60, 60,60, 60, 60,60, 60, 60,60, 60, 60,60},
                {60, 60, 60, 60,60, 60, 60,60, 60, 60,60, 60, 60,60},
                {60, 60, 60, 60,60, 60, 60,60, 60, 60,60, 60, 60,60},

        };

        testMap = new GameMap(spriteIds);
    }

    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        testMap.draw(c);
/*

            c.drawBitmap(GameSheet.MAP.getSpriteSheet(),50,500,null);

            c.drawBitmap(GameSheet.MAP.getSprite(1,1),0,0,null);

            //c.drawBitmap(GameSheet.GRASS.getSpriteSheet(),200,1600,null);

            c.drawBitmap(GameSheet.GRASS.getSprite(8,1),60,90,null);

            c.drawBitmap(GameSheet.WATER.getSprite(1,6),120,180,null);
            GameSheet.ROAD.getSprite(9,0);
            c.drawBitmap(GameSheet.ROAD.getSprite(9,0),180,500,null);*/
        float stripeWidth = 200f; // ajustez la largeur de la bande selon vos besoins
        float screenWidth = getWidth();
        float stripeLeft = screenWidth - stripeWidth;
        float stripeTop = 0;
        float stripeRight = screenWidth;
        float stripeBottom = getHeight();

        c.drawRect(stripeLeft,stripeTop, stripeRight, stripeBottom, yellowPaint);


        for (PointF pos : sqarePos)
            c.drawRect(pos.x, pos.y, pos.x+ DEFAULT_SIZE, pos.y+DEFAULT_SIZE, redPaint);
        holder.unlockCanvasAndPost(c);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            sqarePos.add(new PointF(event.getX(),event.getY()));

            render();

        }

        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//        for(x=0; x<34; x++){
//            for(y=0; y<64; y++){
//                sqarePos.add(new PointF(x,y));
//            }
//        }
        render();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
