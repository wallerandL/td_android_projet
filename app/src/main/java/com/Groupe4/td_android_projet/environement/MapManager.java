package com.Groupe4.td_android_projet.environement;

import android.graphics.Canvas;

import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.helpers.GameConstants;

import java.util.ArrayList;
import java.util.Random;

public class MapManager {

    private GameMap currentMap;


    Random random = new Random();

    int x,y;





    public MapManager() {
        initTestMap(new ArrayList<>());
    }
    public int getMaxWidthCurrentMap() {
        return currentMap.getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public int getMaxHeightCurrentMap() {
        return currentMap.getArrayHeight() * GameConstants.Sprite.SIZE;
    }


    public void draw(Canvas c) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                c.drawBitmap(Floor.OUTSIDE.getSprite(currentMap.getSpriteID(i,j)), i * GameConstants.Sprite.SIZE, j * GameConstants.Sprite.SIZE, null);
    }



    public void initTestMap(ArrayList<Skeleton> skeletonsArrayList) {

        x = random.nextInt(3);
        y = random.nextInt(2);


        int[][] spriteIds = {

                {135 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 25 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},
                {135 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 4 + x*154 + y*11,   67 + x*154 + y*11, 67 + x*154 + y*11, 67 + x*154 + y*11,  67 + x*154 + y*11,    67 + x*154 + y*11, 73 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},
                {135 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 25 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},
                {135 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 25 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},
                {135 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 25 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},
                {67  + x*154 +y*11,   67 + x*154 + y*11, 67 + x*154 + y*11 ,  67 + x*154 + y*11, 73 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},
                {135 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 110 + x*154 + y*11, 132 + x*154 + y*11, 110+ x*154 + y*11},

        };

        currentMap = new GameMap(spriteIds);
    }
}