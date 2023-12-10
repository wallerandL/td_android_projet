package com.Groupe4.td_android_projet.gamestates;

import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.Main.GameLoop;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.environement.GameMap;
import com.Groupe4.td_android_projet.Main.Game;
import com.Groupe4.td_android_projet.helpers.interfaces.GameStateInterface;

import java.util.ArrayList;
import java.util.Random;


public class Playing extends BaseState implements GameStateInterface {

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
    public Playing(Game game) {
        super(game);

        skeletons = new ArrayList<>();
        redPaint.setColor(Color.RED);
        yellowPaint.setColor(Color.rgb(255,140,0));
        skeleton = new Skeleton(new PointF(100,100));
        allies= new Allies(new PointF(500,500));


    }


    @Override
    public void update(double delta) {
        skeleton.update(delta);
        for (Skeleton skeleton : skeletons)
            skeleton.update(delta);

    }

    @Override
    public void render(Canvas c) {
        testMap.draw(c);



        float stripeWidth = 200f; // ajustez la largeur de la bande selon vos besoins
        float screenWidth = c.getWidth();
        float stripeLeft = screenWidth - stripeWidth;
        float stripeTop = 0;
        float stripeRight = screenWidth;
        float stripeBottom = c.getHeight();
        c.drawRect(stripeLeft,stripeTop, stripeRight, stripeBottom, yellowPaint);

        drawCharacter(c,skeleton);
        drawCharacter(c,allies);
        }


    @Override
    public void touchEvents(MotionEvent event) {

    }

    public void drawCharacter(Canvas canvas, Character c){
        canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);
}






}