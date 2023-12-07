package com.Groupe4.td_android_projet;

import  static com.Groupe4.td_android_projet.helpers.GameConstants.Sprite.DEFAULT_SIZE;
import static com.Groupe4.td_android_projet.MainActivity.GAME_WIDTH;
import static com.Groupe4.td_android_projet.MainActivity.GAME_HEIGHT;
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

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.environement.GameMap;
import com.Groupe4.td_android_projet.entites.GameSheet;
import com.Groupe4.td_android_projet.helpers.GameConstants;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();

    private Random rand = new Random();
    private Paint yellowPaint = new Paint();
    private boolean movePlayer;
    private SurfaceHolder holder;
    private float x,y;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    private GameMap testMap;
    private Skeleton skeleton;

    private ArrayList<Skeleton> skeletons;
    public GamePanel(Context context) {
        super(context);
        skeletons = new ArrayList<>();
        holder = getHolder();
        holder.addCallback(this);
        yellowPaint.setColor(Color.rgb(255,140,0));
        redPaint.setColor(Color.RED);
        gameLoop = new GameLoop(this);
        skeleton = new Skeleton(new PointF(100,100));

        int[][] spriteIds = {

                {454, 276, 275, 275, 275, 275, 275, 279, 275, 275, 190, 297, 110},
                {454, 275, 275, 275, 169, 232, 232, 232, 232, 232, 238, 297, 110},
                {454, 275, 275, 276, 190, 275, 279, 275, 275, 275, 279, 297, 110},
                {454, 275, 275, 279, 190, 275, 275, 275, 275, 275, 275, 297, 110},
                {454, 275, 275, 276, 190, 275, 275, 279, 279, 279, 275, 297, 110},
                {232, 232, 232, 232, 238, 275, 275, 279, 276, 279, 275, 297, 110},
                {454, 275, 275, 275, 275, 275, 279, 279, 279, 279, 275, 297, 110},


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


//        for (PointF pos : sqarePos)
//            c.drawRect(pos.x, pos.y, pos.x+ DEFAULT_SIZE, pos.y+DEFAULT_SIZE, redPaint);
       // c.drawBitmap(GameSheet.SKELETON.getSpriteSheet(),500,500,null);
 //       c.drawBitmap(GameSheet.SKELETON.getSprite(0,0),100,100,null);
        drawCharacter(c,skeleton);
        holder.unlockCanvasAndPost(c);
    }



    public void drawCharacter(Canvas canvas, Character c){
        canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);

    }

    public void update(double delta){
        skeleton.update(delta);
        for (Skeleton skeleton : skeletons)
            skeleton.update(delta);

        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            //sqarePos.add(new PointF(event.getX(),event.getY()));
            x=event.getX();
            y=event.getY();
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
