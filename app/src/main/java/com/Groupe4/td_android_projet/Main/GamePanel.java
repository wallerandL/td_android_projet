package com.Groupe4.td_android_projet.Main;

import android.content.Context;
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

public class    GamePanel extends SurfaceView implements SurfaceHolder.Callback {

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
    private Allies allies;

    private ArrayList<Skeleton> skeletons;
    private final Game game;
    public GamePanel(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

      //  redPaint.setColor(Color.RED);

       // gameLoop = new GameLoop(this);
        game = new Game(holder);
/*
        skeleton = new Skeleton(new PointF(100,100));
        allies= new Allies(new PointF(500,500));

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

 */
    }

  /*  public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

       // testMap.draw(c);





       // drawCharacter(c,skeleton);
       // drawCharacter(c,allies);
        holder.unlockCanvasAndPost(c);
    }

*/

   // public void drawCharacter(Canvas canvas, Character c){
     //   canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);

    //}

  /*  public void update(double delta){
    //    skeleton.update(delta);
      //  for (Skeleton skeleton : skeletons)
       //     skeleton.update(delta);

        //Log.d("update count", "update: marche");
        }

*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
     /*   if (event.getAction() == MotionEvent.ACTION_DOWN){
            x=event.getX();
            y=event.getY();
            render();

        }

        return true;
        */

        return game.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {game.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
