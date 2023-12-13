package com.Groupe4.td_android_projet.gamestates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.SKELLETON;

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
import com.Groupe4.td_android_projet.events.WaveManager;
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
    private float buttonX,buttonY,buttonX2,buttonY2,buttonX3,buttonY3;
    private int newWidth, newHeight;
    private ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    private MapManager testMap;
    private Skeleton skeleton;
    private Allies allies;

    private int decalage_y__selection_tour = 200;



    private ArrayList<Skeleton> skeletons;
    private WaveManager waveManager;
    public Playing(Game game) {
        super(game);

        skeletons = new ArrayList<>();
        redPaint.setColor(Color.RED);
        yellowPaint.setColor(Color.rgb(255,140,0));
        skeleton = new Skeleton(new PointF(rand.nextInt(2220), rand.nextInt(1080)));
        allies= new Allies(new PointF(500,500));
        testMap = new MapManager();
        waveManager = new WaveManager(this);

    }


    @Override
    public void update(double delta) {
        updateWaveManager();
        if(isTimeForNewEnemy()){
            spawnEnemy();
        }

        skeleton.update(delta);
        for (Skeleton skeleton : skeletons)
            skeleton.update(delta);

    }
    private void updateWaveManager(){
        this.getWaveManager().update();
    }

    private void spawnEnemy() {
        addEnemy(this.getWaveManager().getNextEnemy());
    }
    public void addEnemy(int enemyType) {
        Log.d("Playing", "Adding enemy of type: " + enemyType);
        switch (enemyType) {
            case SKELLETON:
                spawnSkeleton(rand.nextInt(2220), rand.nextInt(1080));
                break;
        }
    }

    private boolean isTimeForNewEnemy() {
        Log.d("isTimeForNewEnemy", "Adding enemy of type: ");
        if(this.getWaveManager().isThereMoreEnemiesInWaves() && this.getWaveManager().isTimeForNewEnemyq()){
                Log.d("isTimeForNewEnemy3", "Adding enemy of type: ");
                return true;
        }
        return false;
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
        c.drawRect(stripeLeft,stripeTop, stripeRight, stripeBottom, yellowPaint);
        for (Skeleton skeleton : skeletons)
            drawCharacter(c, skeleton);

        c.drawRect(stripeLeft, stripeTop, stripeRight, stripeBottom, yellowPaint);

        //region Affichage eskimoninja
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
        //endregion

        //region Affichage spirit
        // Charger l'image drawable depuis les ressources
        Bitmap buttonImage2 = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.faceset_spirit);

        // Ajuster la position du bouton à l'intérieur du rectangle
        buttonX2 = stripeLeft + 40; // Décalage de 10 pixels vers la gauche
        buttonY2 = stripeTop + decalage_y__selection_tour + 30;

        // Dessiner l'image redimensionnée dans le rectangle
        c.drawBitmap(Bitmap.createScaledBitmap(buttonImage2, newWidth, newHeight, false),
                buttonX2, buttonY2, yellowPaint);
        //endregion

        //region Affichage knight
        // Charger l'image drawable depuis les ressources
        Bitmap buttonImage3 = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.faceset_knight);


        // Ajuster la position du bouton à l'intérieur du rectangle
        buttonX3 = stripeLeft + 40; // Décalage de 10 pixels vers la gauche
        buttonY3 = stripeTop + 2*decalage_y__selection_tour + 30;

        // Dessiner l'image redimensionnée dans le rectangle
        c.drawBitmap(Bitmap.createScaledBitmap(buttonImage3, newWidth, newHeight, false),
                buttonX3, buttonY3, yellowPaint);
        //endregion

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

                //region Selection eskimo
                if (x >= buttonX && x <= buttonX + newWidth && y >= buttonY && y <= buttonY + newHeight) {
                    Log.v("if marche", "TouchEvent : ca marche");
                    return true;
                }
                //endregion

                break;
        }
        return false;
    }

    public void drawCharacter(Canvas canvas, Character c){
        canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);
}
    public WaveManager getWaveManager(){
        return waveManager;
    }
    public void spawnSkeleton(float spawnX, float spawnY) {
        synchronized (skeletons) {
            skeletons.add(new Skeleton(new PointF(spawnX,spawnY)));
            System.out.println("Spawned skeleton at: (" + spawnX + ", " + spawnY + ")");

        }

    }

}