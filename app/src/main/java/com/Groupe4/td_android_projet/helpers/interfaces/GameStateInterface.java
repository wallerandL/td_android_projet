package com.Groupe4.td_android_projet.helpers.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface GameStateInterface {

    void update(double delta);
    void render(Canvas c);
    boolean touchEvents(MotionEvent event);
}
