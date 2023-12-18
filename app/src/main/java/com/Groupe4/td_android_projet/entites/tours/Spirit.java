package com.Groupe4.td_android_projet.entites.tours;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.GameSheet;
import com.Groupe4.td_android_projet.entites.Projectiles;
import com.Groupe4.td_android_projet.entites.Weapons;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.helpers.GameConstants;

public class Spirit extends Character {
    public RectF hurtbox;
    private Paint redPaint = new Paint();
    public PointF projectilePos;
    public int intervalDetection;
    public boolean projectileActive = true;
    public float angle;


    public Spirit(PointF pos)
    {
        super(pos, GameSheet.SPIRIT);
        this.range = 10.0f;
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        this.range = 2000;
        this.projectileSpeed = 120;
        this.projectilePos = new PointF(this.getHitbox().left, this.getHitbox().top);
        this.hurtbox = new RectF(projectilePos.x, projectilePos.y, projectilePos.x+ Projectiles.ENERGY_BALL.getWidth(),projectilePos.y+Projectiles.ENERGY_BALL.getHeight());
        this.intervalDetection = 0;
    }

    public void update(double delta)
    {

    }

    public void detectEnnemy(Character k)
    {
        float distance = PointF.length(k.getHitbox().right - this.getHitbox().right, k.getHitbox().top - this.getHitbox().top);

        if (distance <= range && this.intervalDetection <= 0 ) {
            this.intervalDetection =70;
            float angleRadians = (float) Math.atan2(k.getHitbox().top - this.getHitbox().top, k.getHitbox().right - this.getHitbox().right);
            this.angle = (float) Math.toDegrees(angleRadians);
            launchProjectile(angle);
        }
    }
    private void launchProjectile(float angle) {

            // Calcule les composantes x et y de la vitesse en utilisant l'angle
            float velocityX = projectileSpeed * (float) Math.cos(Math.toRadians(angle));
            float velocityY = projectileSpeed * (float) Math.sin(Math.toRadians(angle));

            if(this.hurtbox != null && this.projectileActive) {
                // Met à jour la position du projectile
                this.projectilePos.x = hurtbox.left + velocityX;
                this.projectilePos.y = hurtbox.top + velocityY;

                float w = Projectiles.ENERGY_BALL.getWidth();
                float h = Projectiles.ENERGY_BALL.getHeight();

                this.hurtbox.set(projectilePos.x, projectilePos.y, projectilePos.x + w, projectilePos.y + h);
            }
            else
            {
                this.projectileActive = true;
                this.hurtbox = new RectF(this.getHitbox().left, this.getHitbox().top, this.getHitbox().left+ Projectiles.ENERGY_BALL.getWidth(),this.getHitbox().top+Projectiles.ENERGY_BALL.getHeight());
                Log.d("echec projectile ", "launchProjectile: projectile X : " + projectilePos.x + "; projectile POS y : "+ projectilePos.y);
                this.projectilePos.x = hurtbox.left + velocityX;
                this.projectilePos.y = hurtbox.top + velocityY;

                float w = Projectiles.ENERGY_BALL.getWidth();
                float h = Projectiles.ENERGY_BALL.getHeight();

                this.hurtbox.set(projectilePos.x, projectilePos.y, projectilePos.x + w, projectilePos.y + h);

            }
        }
    }
