package com.Groupe4.td_android_projet.entites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;
import com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods;

public enum Weapons implements BitmapMethods {

    BIG_SWORD(R.drawable.big_spear);
    Bitmap weaponImg;
    Weapons(int resId){
        options.inScaled=false;
        weaponImg= getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options));
    }

    public Bitmap getWeaponImg()
    {
        return weaponImg;
    }
    public int getWidth()
    {
        return weaponImg.getWidth();
    }
    public int getHeight()
    {
        return weaponImg.getHeight();
    }

}
