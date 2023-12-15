package com.Groupe4.td_android_projet.entites.tours;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.GameSheet;
import com.Groupe4.td_android_projet.entites.Weapons;
import com.Groupe4.td_android_projet.helpers.GameConstants;

public class Knight extends Character {
    protected RectF attackBox;
    private final Paint redPaint = new Paint();



    public Knight(PointF pos)
    {
        super(pos, GameSheet.KNIGHT);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
    }

    public void update(double delta, boolean ennemyInRange)
    {

    }
    public void drawWeapon(Canvas c, Knight k)
    {
        if(attackBox != null){
            c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(),
                    attackBox.left,
                    attackBox.top,
                    null);


            c.drawRect(attackBox, redPaint);

        }
    }
    public void updateWebHitbox() {
        PointF pos = getWepPos();
        float w = getWepWidth();
        float h = getWepHeight();

        attackBox = new RectF(pos.x, pos.y, pos.x+ w,pos.y+h);

    }

    private float getWepWidth() {

        return switch(this.getFaceDir()){
            case GameConstants.Face_Dir.LEFT , GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected Value : " + this.getFaceDir());

        };
    }

    private float getWepHeight() {
        return switch(this.getFaceDir()){
            case GameConstants.Face_Dir.UP , GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected Value : " + this.getFaceDir());

        };
    }

    private PointF getWepPos() {

        return switch(this.getFaceDir())
        {
            case GameConstants.Face_Dir.UP ->
                    new PointF(this.getHitbox().left + 1.75f * GameConstants.Sprite.SCALE_MULTIPLIER,
                            this.getHitbox().top - Weapons.BIG_SWORD.getHeight());

            case GameConstants.Face_Dir.DOWN ->new PointF(this.getHitbox().left + 5f * GameConstants.Sprite.SCALE_MULTIPLIER,
                    this.getHitbox().bottom + 15f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.LEFT -> new PointF(this.getHitbox().left - Weapons.BIG_SWORD.getHeight(),
                    this.getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.RIGHT ->
                    new PointF(this.getHitbox().right,
                            this.getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value : " + this.getFaceDir());


        };


    }
}
