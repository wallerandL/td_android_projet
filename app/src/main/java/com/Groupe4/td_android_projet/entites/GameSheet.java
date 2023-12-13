package com.Groupe4.td_android_projet.entites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.Groupe4.td_android_projet.helpers.GameConstants;
import com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;

public enum GameSheet implements BitmapMethods {

    //PLAYER,
    SKELETON(R.drawable.skeleton_spritesheet),
    PLAYER(R.drawable.player_spritesheet);
//    FACEESKIMONINJA(R.drawable.faceset_eskimoninja),
//    ESKIMONINJA(R.drawable.spritesheet_eskimoninja),
//    FACESPIRIT(R.drawable.faceset_spirit),
//    SPIRIT(R.drawable.spritesheet_spirit); // pour ajouter un élément à la liste, enlever le point virgule juste à gauche et ajouter un élément après une virgule

    private Bitmap spriteSheet;
    private Bitmap[][] sprites=new Bitmap[4][4];
    private BitmapFactory.Options options = new BitmapFactory.Options();

    GameSheet(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites.length; i++) {
                Log.v("boucle", "coordonnees" + i + j);
                sprites[i][j] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE));
            }
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int yPos,int xPos){
        return sprites[yPos][xPos];
    }

}
