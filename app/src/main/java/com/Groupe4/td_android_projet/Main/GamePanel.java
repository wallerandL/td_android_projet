package com.Groupe4.td_android_projet.Main;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.environement.GameMap;
import com.Groupe4.td_android_projet.environement.MapManager;

public class    GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();

    private Random rand = new Random();
    private Paint yellowPaint = new Paint();
    private boolean movePlayer;
    private SurfaceHolder holder;
    private float x,y;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    private GameMap testMap;
    private Skeleton skeleton;
    private Allies allies;

    private ArrayList<Skeleton> skeletons;
    private MapManager mapManager;
    private final Game game;
    public GamePanel(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        game = new Game(holder);
        mapManager = new MapManager();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return game.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {game.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
