package com.Groupe4.td_android_projet.entites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import com.Groupe4.td_android_projet.helpers.GameConstants;
import com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods;

import com.Groupe4.td_android_projet.MainActivity;
import com.Groupe4.td_android_projet.R;

import java.io.File;
import java.util.ArrayList;

public enum GameSheet implements BitmapMethods {

    //PLAYER,
    SKELETON(R.drawable.skeleton_spritesheet),
    PLAYER(R.drawable.player_spritesheet);
    private Bitmap spriteSheet;
    private Bitmap[][] sprites=new Bitmap[4][7];
    private BitmapFactory.Options options = new BitmapFactory.Options();

    GameSheet(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites.length; i++) {
                Log.v("boucle", "coordonner" + i + j);
                sprites[i][j] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE));
            }
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int yPos,int xPos){
        return sprites[yPos][xPos];
    }
    /*private Bitmap getScaledBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()*5,bitmap.getHeight()*5,false);
    }*/


}
