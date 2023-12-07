package com.Groupe4.td_android_projet.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.Groupe4.td_android_projet.helpers.interfaces.GameStateInterface;

public class Menu implements GameStateInterface {
    private Paint paint;
    public Menu( ) {
        super();
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawText("MENU!", 800, 200, paint);

    }

    @Override
    public void touchEvents(MotionEvent event) {

    }
}
