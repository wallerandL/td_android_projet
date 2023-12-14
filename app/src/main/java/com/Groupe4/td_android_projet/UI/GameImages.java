package com.Groupe4.td_android_projet.UI;

import static com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods.options;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods;
import com.Groupe4.td_android_projet.R;

public enum GameImages {

    MAINMENU_MENUBG(R.drawable.mainmenu_menubackground);
    private final Bitmap image;

    GameImages(int resID) {
        options.inScaled = false;
        image = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
    }

    public Bitmap getImage() {
        return image;
    }
}
