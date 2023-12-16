package com.Groupe4.td_android_projet.gamestates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.REPTIL;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.SKELLETON;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;
import com.Groupe4.td_android_projet.UI.ButtonImages;
import com.Groupe4.td_android_projet.UI.CustomButton;
import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.Main.GameLoop;
import com.Groupe4.td_android_projet.entites.Weapons;
import com.Groupe4.td_android_projet.entites.enemies.Reptil;
import com.Groupe4.td_android_projet.entites.enemies.Skeleton;
import com.Groupe4.td_android_projet.entites.tours.EskimoNinja;
import com.Groupe4.td_android_projet.entites.tours.Knight;
import com.Groupe4.td_android_projet.entites.tours.Spirit;
import com.Groupe4.td_android_projet.events.WaveManager;
import com.Groupe4.td_android_projet.entites.tours.Allies;
import com.Groupe4.td_android_projet.environement.MapManager;
import com.Groupe4.td_android_projet.Main.Game;
import com.Groupe4.td_android_projet.helpers.GameConstants;
import com.Groupe4.td_android_projet.helpers.interfaces.GameStateInterface;

import java.util.ArrayList;
import java.util.Random;


public class Playing extends BaseState implements GameStateInterface {

    private final Paint redPaint = new Paint();

    private final Random rand = new Random();
    boolean eskimo_selected, knight_selected, spirit_selected, player_selected;
    private final Paint yellowPaint = new Paint();
    private boolean movePlayer;
    private SurfaceHolder holder;
    private float buttonX,buttonY,buttonX2,buttonY2,buttonX3,buttonY3;
    private int newWidth, newHeight;
    private final ArrayList<PointF> sqarePos = new ArrayList<>() ;

    private GameLoop gameLoop;
    private final MapManager testMap;
    private final Skeleton skeleton;
    private final Reptil reptil;
    float x,y;
    private final Allies allies;
    private Knight knight;
    private Spirit spirit;
    private EskimoNinja eskimoNinja;

    private final int decalage_y_selection_tour = 200;



    private final ArrayList<Skeleton> skeletons;
    private final ArrayList<Reptil> reptils;
    private final ArrayList<EskimoNinja> eskimoNinjas;
    private final ArrayList<Knight> knights;
    private final ArrayList<Allies> players;
    private final ArrayList<Spirit> spirits;
    private final CustomButton btnMenu;
    private final WaveManager waveManager;
    private RectF attackBox;

    public Playing(Game game) {
        super(game);

        skeletons = new ArrayList<>();
        reptils = new ArrayList<>();
        eskimoNinjas = new ArrayList<>();
        knights = new ArrayList<>();
        spirits = new ArrayList<>();
        players = new ArrayList<>();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setColor(Color.rgb(255,140,0));
        skeleton = new Skeleton(new PointF(rand.nextInt(2220), rand.nextInt(1080)));
        reptil = new Reptil(new PointF(rand.nextInt(2220), rand.nextInt(1080)));
        allies= new Allies(new PointF(500,500));
        testMap = new MapManager();
        waveManager = new WaveManager(this);
        btnMenu = new CustomButton(2060, 900, ButtonImages.PLAYING_MENU.getWidth(), ButtonImages.PLAYING_MENU.getHeight());
    }


    @Override
    public void update(double delta) {
        updateWaveManager();
        if(isTimeForNewEnemy()){
            spawnEnemy();
        }

        for(Knight k : knights )
        {
            k.updateWebHitbox();
        }
        skeleton.update(delta);
        for (Skeleton skeleton : skeletons)
            skeleton.update(delta);
        reptil.update(delta);
        for (Reptil reptil : reptils)
            reptil.update(delta);

        if (isAllEnemiesDead()){
            if (isThereMoreWaves()){
                waveManager.increaseWaveIndex();
                waveManager.resetEnemyIndex();
            }
        }

    }

    private boolean isThereMoreWaves() {
        return WaveManager.isTherMoreWaves();
    }

    private boolean isAllEnemiesDead() {
        if (skeletons.isEmpty() && reptils.isEmpty()){
            if (waveManager.isThereMoreEnemiesInWaves()){
                return false;
            }
            return true;
        }
        return false;
    }

    public void kill(){
        skeletons.clear();
        reptils.clear();
    }

    //region vagues
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
            case REPTIL:
                spawnReptil(rand.nextInt(2220), rand.nextInt(1080));
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
    //endregion

    @Override
    public void render(Canvas c) {
        testMap.draw(c);

        //region ajout bar orange
        float stripeWidth = 200f; // ajustez la largeur de la bande selon vos besoins
        float screenWidth = c.getWidth();
        float stripeLeft = screenWidth - stripeWidth;
        float stripeTop = 0;
        float stripeRight = screenWidth;
        float stripeBottom = c.getHeight();
        c.drawRect(stripeLeft,stripeTop, stripeRight, stripeBottom, yellowPaint);
        //endregion

        //region draw tour / ennemies
        for (Skeleton skeleton : skeletons)
            drawCharacter(c, skeleton);

        for (Reptil reptil : reptils)
            drawCharacter(c, reptil);

        for(EskimoNinja e : eskimoNinjas)
            drawCharacter(c, e);

        for(Knight k : knights)
            drawCharacter(c, k);

        for(Spirit s : spirits)
            drawCharacter(c, s);
        //endregion


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
        buttonY2 = stripeTop + decalage_y_selection_tour + 30;

        // Dessiner l'image redimensionnée dans le rectangle
        c.drawBitmap(Bitmap.createScaledBitmap(buttonImage2, newWidth, newHeight, false),
                buttonX2, buttonY2, yellowPaint);
        //endregion

        //region Affichage knight
        // Charger l'image drawable depuis les ressources
        Bitmap buttonImage3 = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.faceset_knight);


        // Ajuster la position du bouton à l'intérieur du rectangle
        buttonX3 = stripeLeft + 40; // Décalage de 10 pixels vers la gauche
        buttonY3 = stripeTop + 2* decalage_y_selection_tour + 30;

        // Dessiner l'image redimensionnée dans le rectangle
        c.drawBitmap(Bitmap.createScaledBitmap(buttonImage3, newWidth, newHeight, false),
                buttonX3, buttonY3, yellowPaint);
        //endregion

        drawUI(c);
        }

    private void drawUI(Canvas c) {
        c.drawBitmap(
                ButtonImages.PLAYING_MENU.getBtnImg(btnMenu.isPushed()),
                btnMenu.getHitbox().left,
                btnMenu.getHitbox().top,
                null);
    }
    @Override
    public boolean touchEvents(MotionEvent event) {

        x = event.getX();
        y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            kill();
            if (isIn(event, btnMenu))
                btnMenu.setPushed(true);
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnMenu))
                if (btnMenu.isPushed())
                    game.setCurrentGameState(Game.GameState.MENU);

            btnMenu.setPushed(false);
        }




        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                //region Selection eskimo
                if (x >= buttonX && x <= buttonX + newWidth && y >= buttonY && y <= buttonY + newHeight) {
                    Log.v("if marche", "TouchEvent : ca marche eskimo");
                    eskimo_selected = true;
                    return true;
                }
                //endregion
                //region Selection spirit
                if (x >= buttonX2 && x <= buttonX2 + newWidth && y >= buttonY2 && y <= buttonY2 + newHeight) {
                    Log.v("if marche", "TouchEvent : ca marche spirit ");
                    spirit_selected = true;
                    return true;
                }
                //endregion
                //region Selection knight
                if (x >= buttonX3 && x <= buttonX3 + newWidth && y >= buttonY3 && y <= buttonY3 + newHeight) {
                    Log.v("if marche", "TouchEvent : ca marche knight");
                    knight_selected = true;
                    return true;
                }
                //endregion

                break;

            // Ajoutez d'autres cas selon vos besoins, par exemple, ACTION_MOVE, ACTION_UP, etc.

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:




                // Vérifier si le clic est à l'intérieur des coordonnées de l'image
                if (eskimo_selected) {
                    spawnEskimo(x, y);
                    eskimo_selected = false;
                }
                if (knight_selected) {
                    spawnKnight(x, y);
                    knight_selected = false;
                }
                if (spirit_selected) {
                    spawnSpirit(x, y);
                    spirit_selected = false;
                }

                break;


        }
        return false;
    }

    private boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }

    public void drawCharacter(Canvas canvas, Character c){
        canvas.drawBitmap(c.getGameSheetType().getSprite(c.getFaceDir(),c.getAniIndex()), c.getHitbox().left,c.getHitbox().top,null);
        canvas.drawRect(c.getHitbox().left,c.getHitbox().top,c.getHitbox().right,c.getHitbox().bottom,redPaint);

        if(c instanceof Knight){

            Knight k = (Knight) c;

            if(k != null){
                Log.d("hitbox chevalier", String.valueOf(k.getHitbox()));
                k.drawWeapon(canvas);
            }

        }

}
    public WaveManager getWaveManager(){
        return waveManager;
    }

    //region Méthodes spawn
    public void spawnSkeleton(float spawnX, float spawnY) {
        synchronized (skeletons) {
            skeletons.add(new Skeleton(new PointF(spawnX,spawnY)));
            System.out.println("Spawned skeleton at: (" + spawnX + ", " + spawnY + ")");

        }

    }
    public void spawnReptil(float spawnX, float spawnY) {
        synchronized (reptils) {
            reptils.add(new Reptil(new PointF(spawnX,spawnY)));
            System.out.println("Spawned reptil at: (" + spawnX + ", " + spawnY + ")");

        }

    }
    public void spawnEskimo(float localx, float localy) {
        synchronized (eskimoNinjas) {
            eskimoNinjas.add(new EskimoNinja(new PointF(localx,localy)));
            System.out.println("Spawned eskimo at: (" + localx + ", " + localy + ")");

        }

    }
    public void spawnKnight(float localx, float localy) {
        synchronized (knights) {
            knights.add(new Knight(new PointF(localx,localy)));
            System.out.println("Spawned knight at: (" + localx + ", " + localy + ")");

        }

    }
    public void spawnSpirit(float localx, float localy) {
        synchronized (spirits) {
            spirits.add(new Spirit(new PointF(localx,localy)));
            System.out.println("Spawned spirit at: (" + localx + ", " + localy + ")");

        }

    }
    //endregion
}