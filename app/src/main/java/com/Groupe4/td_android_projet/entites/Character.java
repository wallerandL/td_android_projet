package com.Groupe4.td_android_projet.entites;

import android.graphics.PointF;

import com.Groupe4.td_android_projet.helpers.GameConstants;

public abstract class Character extends Entity{
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstants.Face_Dir.DOWN;
    protected final GameSheet gameSheetType;
    public Character(PointF pos, GameSheet gameSheetType) {
        super(pos, 1, 1);
        this.gameSheetType = gameSheetType;
    }

    protected void updateAnimation(){
        aniTick++;
        if (aniTick >= GameConstants.Animation.speed) {
            aniTick = 0;
            aniIndex++;
            if (GameConstants.Animation.AMOUNT >= 4)
                aniIndex = 0;
        }
    }


    public int getAniIndex() {
        return aniIndex;
    }

    public int getFaceDir() {
        return faceDir;
    }

    public void setFaceDir(int faceDir){
        this.faceDir = faceDir;
    }


    public GameSheet getGameSheetType() {
        return gameSheetType;
    }
}
