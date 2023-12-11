package com.Groupe4.td_android_projet.environement;

import android.graphics.Canvas;
import android.util.Log;

import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.helpers.GameConstants;

import java.util.ArrayList;

public class GameMap {

    private int[][] spriteIds;
    private ArrayList<Skeleton> skeletonsArrayList;


    public GameMap(int[][] spriteIds) {
            this.spriteIds = spriteIds;
            this.skeletonsArrayList = skeletonsArrayList;
        }

        public ArrayList<Skeleton> getSkeletonsArrayList() {
            return skeletonsArrayList;
        }

        /*public void draw(Canvas c) {
            for (int j = 0; j < spriteIds.length; j++)
                for (int i = 0; i < spriteIds[j].length; i++)
                    c.drawBitmap(Floor.OUTSIDE.getSprite(spriteIds[j][i]), i * GameConstants.Sprite.SIZE, j * GameConstants.Sprite.SIZE, null);
        }*/

        public int getArrayWidth() {
            return spriteIds[0].length;
        }

        public int getArrayHeight() {
            return spriteIds.length;
        }

        public int getSpriteID(int xIndex, int yIndex) {

            return spriteIds[yIndex][xIndex];
        }
    }