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
    public RectF getAttackBox() {
        return attackBox;
    }

    protected RectF attackBox;
    private final Paint redPaint = new Paint();
    int compteFace = 0;
    boolean Rotation = true;


    public Knight(PointF pos)
    {
        super(pos, GameSheet.KNIGHT);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
    }

    public void update(double delta, boolean ennemyInRange)
    {

    }
    public void drawWeapon(Canvas c)
    {
        if(attackBox != null){

            c.save(); // sauvegarde du canvas pour ne pas tourner tous les éléments

            c.rotate(getWepRot(), attackBox.left, attackBox.top); // rotation de l'attack box
            c.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(),
                    attackBox.left + wepRotAdjustmentLeft(),
                    attackBox.top + wepRotAdjustmentTop(),
                    null);
            c.rotate(getWepRot() * -1 , attackBox.left, attackBox.top);

            c.drawRect(attackBox, redPaint);

            c.restore(); // restoration du canvas pour remettre les éléments tournés à la bonne rotation
        }
    }

    private float wepRotAdjustmentLeft() {
        return switch (this.getFaceDir())
        {
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.RIGHT ->
                    -Weapons.BIG_SWORD.getWidth();

            default -> 0;
        };
    }
    private float wepRotAdjustmentTop() {
        return switch (this.getFaceDir())
        {
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.LEFT ->
                    -Weapons.BIG_SWORD.getHeight();

            default -> 0;
        };
    }

    private float getWepRot()
    {
        return switch (this.getFaceDir())
        {

            case GameConstants.Face_Dir.LEFT -> 90;
            case GameConstants.Face_Dir.UP -> 180;
            case GameConstants.Face_Dir.RIGHT -> 270;

            default -> 0;
        };
    }

    public void updateWebHitbox() {

        if(Rotation)
        {
            //region Test rotation chevalier
            if (compteFace % 100 == 25) {
                this.setFaceDir(GameConstants.Face_Dir.LEFT);
            }

            if (compteFace % 100 == 50) {
                this.setFaceDir(GameConstants.Face_Dir.UP);
            }
            if (compteFace % 100 == 75) {
                this.setFaceDir(GameConstants.Face_Dir.RIGHT);
            }
            if (compteFace % 100 == 99) {
                this.setFaceDir(GameConstants.Face_Dir.DOWN);
                compteFace = 0;
            }

            compteFace++;
            //endregion
        }

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

            case GameConstants.Face_Dir.DOWN ->new PointF(this.getHitbox().left + 2.5f * GameConstants.Sprite.SCALE_MULTIPLIER,
                    this.getHitbox().bottom);

            case GameConstants.Face_Dir.LEFT -> new PointF(this.getHitbox().left - Weapons.BIG_SWORD.getHeight(),
                    this.getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.RIGHT ->
                    new PointF(this.getHitbox().right,
                            this.getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value : " + this.getFaceDir());


        };


    }
}
