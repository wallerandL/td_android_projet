package com.Groupe4.td_android_projet.entites;

import android.graphics.PointF;

import com.Groupe4.td_android_projet.helpers.GameConstants;

public abstract class Character extends Entity{
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstants.Face_Dir.DOWN;

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    protected float range;

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    protected int projectileSpeed;
    protected final GameSheet gameSheetType;
    public Character(PointF pos, GameSheet gameSheetType) {
        super(pos, GameConstants.Sprite.SIZE,GameConstants.Sprite.SIZE );
        this.gameSheetType = gameSheetType;
    }

    protected void updateAnimation(){
        aniTick++;
        if (aniTick >= GameConstants.Animation.SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GameConstants.Animation.AMOUNT)
                aniIndex = 0;
        }
    }

    public void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
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
