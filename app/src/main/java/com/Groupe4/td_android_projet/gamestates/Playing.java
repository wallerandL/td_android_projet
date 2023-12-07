package com.Groupe4.td_android_projet.gamestates;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;


import com.Groupe4.td_android_projet.Main.Game;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.helpers.GameConstants;
import com.Groupe4.td_android_projet.helpers.interfaces.GameStateInterface;

import java.util.ArrayList;

public class Playing extends BaseState implements GameStateInterface {
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    //private MapManager mapManager;
    private Allies allies;
    private ArrayList<Skeleton> skeletons;

    //For UI
    private float xCenter = 250, yCenter = 800, radius = 150;
    private Paint circlePaint;
    private float xTouch, yTouch;
    private boolean touchDown;

    public Playing(Game game) {
        super(game);

        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);

        //mapManager = new MapManager();
        allies  = new Allies();
        skeletons = new ArrayList<>();

        for (int i = 0; i < 50; i++)
            skeletons.add(new Skeleton(new PointF(100, 100)));
    }

    @Override
    public void update(double delta) {
        updatePlayerMove(delta);
        allies.update(delta, movePlayer);
        for (Skeleton skeleton : skeletons)
            skeleton.update(delta);
        mapManager.setCameraValues(cameraX, cameraY);
    }

    @Override
    public void render(Canvas c) {
        mapManager.draw(c);
        drawUI(c);

        drawPlayer(c);
        for (Skeleton skeleton : skeletons)
            drawCharacter(c, skeleton);
    }

    private void drawUI(Canvas c) {
        c.drawCircle(xCenter, yCenter, radius, circlePaint);
    }

    private void drawPlayer(Canvas c) {
        c.drawBitmap(
                allies.getGameCharType().getSprite(allies.getAniIndex(), allies.getFaceDir()),
                allies.getHitbox().left,
                allies.getHitbox().top,
                null);
    }

    public void drawCharacter(Canvas canvas, Character c) {
        canvas.drawBitmap(
                c.getGameCharType().getSprite(c.getAniIndex(), c.getFaceDir()),
                c.getHitbox().left + cameraX,
                c.getHitbox().top + cameraY,
                null);
    }

    private void updatePlayerMove(double delta) {
        if (!movePlayer)
            return;

        float baseSpeed = (float) (delta * 300);
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if (lastTouchDiff.x > 0) allies.setFaceDir(GameConstants.Face_Dir.RIGHT);
            else allies.setFaceDir(GameConstants.Face_Dir.LEFT);
        } else {
            if (lastTouchDiff.y > 0) allies.setFaceDir(GameConstants.Face_Dir.DOWN);
            else allies.setFaceDir(GameConstants.Face_Dir.UP);
        }

        if (lastTouchDiff.x < 0)
            xSpeed *= -1;
        if (lastTouchDiff.y < 0)
            ySpeed *= -1;

        int pWidth = GameConstants.Sprite.SIZE;
        int pHeight = GameConstants.Sprite.SIZE;

        if (xSpeed <= 0)
            pWidth = 0;
        if (ySpeed <= 0)
            pHeight = 0;


        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        if (mapManager.canMoveHere(allies.getHitbox().left + cameraX * -1 + deltaX * -1 + pWidth, allies.getHitbox().top + cameraY * -1 + deltaY * -1 + pHeight)) {
            cameraX += deltaX;
            cameraY += deltaY;
        }
    }

    public void setPlayerMoveTrue(PointF lastTouchDiff) {
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse() {
        movePlayer = false;
        allies.resetAnimation();
    }

    /*@Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN -> {

                float x = event.getX();
                float y = event.getY();

                float a = Math.abs(x - xCenter);
                float b = Math.abs(y - yCenter);
                float c = (float) Math.hypot(a, b);

                if (c <= radius) {
                    touchDown = true;
                    xTouch = x;
                    yTouch = y;
                } else
                    game.setCurrentGameState(Game.GameState.MENU);
            }

            case MotionEvent.ACTION_MOVE -> {
                if (touchDown) {
                    xTouch = event.getX();
                    yTouch = event.getY();

                    float xDiff = xTouch - xCenter;
                    float yDiff = yTouch - yCenter;

                    setPlayerMoveTrue(new PointF(xDiff, yDiff));

                }

            }
            case MotionEvent.ACTION_UP -> {
                touchDown = false;
                setPlayerMoveFalse();

            }
        }
    }*/
}