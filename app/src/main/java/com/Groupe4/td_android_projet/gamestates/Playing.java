package com.Groupe4.td_android_projet.gamestates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;
import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.Main.GameLoop;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.environement.MapManager;
import com.Groupe4.td_android_projet.Main.Game;
import com.Groupe4.td_android_projet.helpers.interfaces.GameStateInterface;

import java.util.ArrayList;
import java.util.Random;


public class Playing extends BaseState implements GameStateInterface {

    private Paint redPaint = new Paint();

    private Random rand = new Random();
    private Paint yellowPaint = new Paint();
    private boolean movePlayer;
    private SurfaceHolder holder;
    private float buttonX,buttonY;
    private int newWidth, newHeight;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    private MapManager testMap;
    private Skeleton skeleton;
    private Allies allies;



    private ArrayList<Skeleton> skeletons;
    public Playing(Game game) {
        super(game);

        skeletons = new ArrayList<>();
        redPaint.setColor(Color.RED);
        yellowPaint.setColor(Color.rgb(255,140,0));
        skeleton = new Skeleton(new PointF(100,100));
        allies= new Allies(new PointF(500,500));
        testMap = new MapManager();


    }


    @Override
    public void update(double delta) {
        skeleton.update(delta);
        for (Skeleton skeleton : skeletons)
            skeleton.update(delta);

    }

    @Override
    public void render(Canvas c) {
        testMap.draw(c);

        float stripeWidth = 200f; // ajustez la largeur de la bande selon vos besoins
        float screenWidth = c.getWidth();
        float stripeLeft = screenWidth - stripeWidth;
        float stripeTop = 0;
        float stripeRight = screenWidth;
        float stripeBottom = c.getHeight();
        c.drawRect(stripeLeft, stripeTop, stripeRight, stripeBottom, yellowPaint);

        // Charger l'image drawable depuis les ressources
        Bitmap buttonImage = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.faceset_eskimoninja);

        // Redimensionner l'image pour s'adapter à l'intérieur du rectangle
        float aspectRatio = (float) buttonImage.getWidth() / buttonImage.getHeight();
        newWidth = 125; // Largeur souhaitée
        newHeight = (int) (newWidth / aspectRatio);

        // Ajuster la position du bouton à l'intérieur du rectangle
        buttonX = stripeLeft + 40; // Décalage de 10 pixels vers la gauche
        buttonY = stripeTop + 30;

        // Dessiner l'image redimensionnée dans le rectangle
        c.drawBitmap(Bitmap.createScaledBitmap(buttonImage, newWidth, newHeight, false),
                buttonX, buttonY, yellowPaint);

        drawCharacter(c,skeleton);
        drawCharacter(c,allies);
        }


    @Override
    public boolean touchEvents(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            // Ajoutez d'autres cas selon vos besoins, par exemple, ACTION_MOVE, ACTION_UP, etc.

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                // Vérifier si le clic est à l'intérieur des coordonnées de l'image
                if (x >= buttonX && x <= buttonX + newWidth && y >= buttonY && y <= buttonY + newHeight) {
                    Log.v("if marche", "TouchEvent : ca marche");
                    return true; // Indique que l'événement a été traité
                }
                break;
        }
        return false;
    }

    public void drawCharacter(Canvas canvas, Character c){
        canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);
}






}