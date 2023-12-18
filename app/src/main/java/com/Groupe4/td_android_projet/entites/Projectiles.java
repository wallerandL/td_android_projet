package com.Groupe4.td_android_projet.entites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;
import com.Groupe4.td_android_projet.helpers.GameConstants;
import com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods;

public enum Projectiles implements BitmapMethods {

    ICE_SPIKE(R.drawable.spritesheet_icespike),
    ENERGY_BALL(R.drawable.spritesheet_bigenergyball);
    Bitmap projectileImg;



    Projectiles(int resId)
    {
        options.inScaled=false;
        projectileImg= getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options));

    }

    public Bitmap getProjectileImg()
    {
        return projectileImg;
    }
    public int getWidth()
    {
        return projectileImg.getWidth();
    }
    public int getHeight()
    {
        return projectileImg.getHeight();
    }
}