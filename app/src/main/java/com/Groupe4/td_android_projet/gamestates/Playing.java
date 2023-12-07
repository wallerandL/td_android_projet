package com.Groupe4.td_android_projet.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.Groupe4.td_android_projet.GameLoop;
import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.environement.GameMap;
import com.Groupe4.td_android_projet.helpers.interfaces.GameStateInterface;

import java.util.ArrayList;

public class Playing implements GameStateInterface {

    private float x,y;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    public Playing(){


        redPaint.setColor(Color.RED);

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
        drawCharacter(c,skeleton);
        drawCharacter(c,allies);

        public void drawCharacter(Canvas canvas, Character c){
            canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);

        }
    }

    @Override
    public void touchEvents(MotionEvent event) {

    }
}
