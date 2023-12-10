package com.Groupe4.td_android_projet.environement;

import android.graphics.Canvas;

import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.helpers.GameConstants;

import java.util.ArrayList;

public class MapManager {

    private GameMap currentMap;

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
                c.drawBitmap(Floor.OUTSIDE.getSprite(currentMap.getSpriteID(j,i)), i * GameConstants.Sprite.SIZE, j * GameConstants.Sprite.SIZE, null);
    }

    public void initTestMap(ArrayList<Skeleton> skeletonsArrayList) {

        int[][] spriteIds = {

                {454, 276, 275, 275, 275, 275, 275, 279, 275, 275, 190, 297, 110},
                {454, 275, 275, 275, 169, 232, 232, 232, 232, 232, 238, 297, 110},
                {454, 275, 275, 276, 190, 275, 279, 275, 275, 275, 279, 297, 110},
                {454, 275, 275, 279, 190, 275, 275, 275, 275, 275, 275, 297, 110},
                {454, 275, 275, 276, 190, 275, 275, 279, 279, 279, 275, 297, 110},
                {232, 232, 232, 232, 238, 275, 275, 279, 276, 279, 275, 297, 110},
                {454, 275, 275, 275, 275, 275, 279, 279, 279, 279, 275, 297, 110},


        };

        currentMap = new GameMap(spriteIds,skeletonsArrayList);
    }
}