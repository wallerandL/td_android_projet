package com.Groupe4.td_android_projet.UI;

import static com.Groupe4.td_android_projet.helpers.interfaces.BitmapMethods.options;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;

public enum ButtonImages {


    MENU_START(R.drawable.mainmenu_button_start, 300, 140),
    PLAYING_MENU(R.drawable.playing_button_menu, 140, 140);

    private int width, height;
    private Bitmap normal, pushed;

    ButtonImages(int resID, int width, int height) {
        options.inScaled = false;
        this.width = width;
        this.height = height;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        normal = Bitmap.createBitmap(buttonAtlas, 0, 0, width, height);
        pushed = Bitmap.createBitmap(buttonAtlas, width, 0, width, height);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBtnImg(boolean isBtnPushed) {
        return isBtnPushed ? pushed : normal;
    }
}
