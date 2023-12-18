package com.Groupe4.td_android_projet.gamestates;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.Groupe4.td_android_projet.helpers.GameConstants.Animation.TILE;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.REPTIL;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Enemies.SKELLETON;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Face_Dir.LEFT;
import static com.Groupe4.td_android_projet.helpers.GameConstants.PrixTour.PRIXESKIMONIJA;
import static com.Groupe4.td_android_projet.helpers.GameConstants.PrixTour.PRIXKNIGHT;
import static com.Groupe4.td_android_projet.helpers.GameConstants.PrixTour.PRIXSPIRIT;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.Groupe4.td_android_projet.Main.GameOver;
import com.Groupe4.td_android_projet.Main.MainActivity;
import com.Groupe4.td_android_projet.R;
import com.Groupe4.td_android_projet.UI.ButtonImages;
import com.Groupe4.td_android_projet.UI.CustomButton;
import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.Main.GameLoop;
import com.Groupe4.td_android_projet.entites.Projectiles;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



public class Playing extends BaseState implements GameStateInterface {

    private final Paint redPaint = new Paint();

    private final Random rand = new Random();

    boolean eskimo_selected, knight_selected, spirit_selected;
    private final Paint yellowPaint = new Paint();
    private boolean movePlayer;
    private SurfaceHolder holder;
    private float buttonX, buttonY, buttonX2, buttonY2, buttonX3, buttonY3;
    private int newWidth, newHeight;
    private final ArrayList<PointF> sqarePos = new ArrayList<>();
    private GameLoop gameLoop;
    private final MapManager testMap;
    float x, y;
    private int currentWaypointIndex=0;
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

    public static int PV=10;
    public static int Argent=25;
    public static int SPEEDSKELETON = 300;
    public static int SPEEDREPIL = 400;


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
        yellowPaint.setColor(Color.rgb(255, 140, 0));
        testMap = new MapManager();
        waveManager = new WaveManager(this);
        btnMenu = new CustomButton(2060, 900, ButtonImages.PLAYING_MENU.getWidth(), ButtonImages.PLAYING_MENU.getHeight());
    }


    @Override
    public void update(double delta) {


        if (PV <= 0) {
            // Le nombre de vies est épuisé, passer à la page "Game Over"
            goToGameOverPage();
            return;
        }

        updateWaveManager();
        if (isTimeForNewEnemy()) {
            spawnEnemy();
        }

        checkAttack();
        for (Knight k : knights) {
            k.updateWebHitbox();
        }
        for (Spirit s : spirits) {
            updateEnergyBallPosition(s);
            if (s.intervalDetection < 15) {
                s.projectileActive = false;
            }
            s.intervalDetection--;
        }
        for(EskimoNinja e : eskimoNinjas)
        {
            updateIceSpikePosition(e);
            if(e.intervalDetection < 17)
            {
                e.projectileActive = false;
            }
            e.intervalDetection--;

        }

        for (Skeleton skeleton : skeletons) {
            if (skeleton.isActive()) {
                skeleton.update(delta);
                skeleton.invincibilityFrame--;

            }

        }
        for (Reptil reptil : reptils) {
            if (reptil.isActive()) {
                reptil.update(delta);

            }
        }

        if (isAllEnemiesDead()) {
            if (isThereMoreWaves()) {
                waveManager.increaseWaveIndex();
                waveManager.resetEnemyIndex();
            }
        }
        NettoyageTableau(skeletons);
        NettoyageTableau(reptils);

    }

    private <T extends Character> void NettoyageTableau(ArrayList<T> characters) {
        Iterator<T> iterator = characters.iterator();

        while (iterator.hasNext()) {
            T character = iterator.next();

            if (!character.isActive()) {
                iterator.remove();
            }
        }
    }

    //region detection d'ennemi, attaque et collision
    private void checkAttack() {
        for (Knight k : knights) {
            if (k.getAttackBox() != null) {
                for (Skeleton skel : skeletons) {
                    if (k.getAttackBox().intersects(skel.getHitbox().left, skel.getHitbox().top, skel.getHitbox().right, skel.getHitbox().bottom)) {
                        if(skel.invincibilityFrame <= 0)
                        {
                            Argent++;
                            skel.setActive(false);
                        }
                    }
                }

                for (Reptil rept : reptils) {
                    if (k.getAttackBox().intersects(rept.getHitbox().left, rept.getHitbox().top, rept.getHitbox().right, rept.getHitbox().bottom)) {
                        addEnemy(SKELLETON, rept.getHitbox().left, rept.getHitbox().top);
                        Argent++;
                        rept.setActive(false);
                    }
                }
            }
        }
        for (Spirit s : spirits) {
            for (Skeleton skel : skeletons) {
                s.detectEnnemy(skel);
                if (s.hurtbox != null && s.projectileActive) {
                    if (s.hurtbox.intersects(skel.getHitbox().left, skel.getHitbox().top, skel.getHitbox().right, skel.getHitbox().bottom)) {
                        if (skel.invincibilityFrame <= 0) {
                            skel.setActive(false);
                            Argent++;
                            s.projectileActive = false;
                        }
                    }
                }
            }


            for (Reptil rept : reptils) {
                s.detectEnnemy(rept);
                if (s.hurtbox != null && s.projectileActive) {
                    if (s.hurtbox.intersects(rept.getHitbox().left, rept.getHitbox().top, rept.getHitbox().right, rept.getHitbox().bottom)) {
                        addEnemy(SKELLETON, rept.getHitbox().left, rept.getHitbox().top);
                        rept.setActive(false);
                        Argent++;
                        s.projectileActive = false;
                    }
                }
            }
        }
        for (EskimoNinja e : eskimoNinjas) {
            for (Skeleton skel : skeletons) {
                e.detectEnnemy(skel);
                if (e.hurtbox != null && e.projectileActive) {
                    if (e.hurtbox.intersects(skel.getHitbox().left, skel.getHitbox().top, skel.getHitbox().right, skel.getHitbox().bottom)) {
                        if (skel.invincibilityFrame <= 0) {
                            skel.setActive(false);
                            Argent++;
                        }

                    }
                }
            }


            for (Reptil rept : reptils) {
                e.detectEnnemy(rept);
                if (e.hurtbox != null && e.projectileActive) {
                    if (e.hurtbox.intersects(rept.getHitbox().left, rept.getHitbox().top, rept.getHitbox().right, rept.getHitbox().bottom)) {
                        currentWaypointIndex=rept.getCurrentWaypointIndex();
                        spawnSkeleton(rept.getHitbox().left, rept.getHitbox().top,currentWaypointIndex);
                        rept.setActive(false);
                        Argent++;
                    }
                }
            }
        }
    }

    private void updateEnergyBallPosition(Spirit s) {
        // Calcule les composantes x et y de la vitesse en utilisant l'angle
        if (s.projectileActive) {

            float velocityX = s.getProjectileSpeed() * (float) Math.cos(Math.toRadians(s.angle));
            float velocityY = s.getProjectileSpeed() * (float) Math.sin(Math.toRadians(s.angle));

            // Met à jour la position du projectile
            s.projectilePos.x += velocityX;
            s.projectilePos.y += velocityY;

            Log.d("position hurtbox", "projectilePos.x : " + s.projectilePos.x + " ; projectilePos.y : " + s.projectilePos.y);

            // Met à jour la boîte de collision du projectile
            float w = Projectiles.ENERGY_BALL.getWidth();
            float h = Projectiles.ENERGY_BALL.getHeight();
            s.hurtbox.set(s.projectilePos.x, s.projectilePos.y, s.projectilePos.x + w, s.projectilePos.y + h);
        }
    }

    private void updateIceSpikePosition(EskimoNinja e) {
        // Calcule les composantes x et y de la vitesse en utilisant l'angle
        if (e.projectileActive) {

            float velocityX = e.getProjectileSpeed() * (float) Math.cos(Math.toRadians(e.angle));
            float velocityY = e.getProjectileSpeed() * (float) Math.sin(Math.toRadians(e.angle));

            // Met à jour la position du projectile
            e.projectilePos.x += velocityX;
            e.projectilePos.y += velocityY;

            Log.d("position hurtbox", "projectilePos.x : " + e.projectilePos.x + " ; projectilePos.y : " + e.projectilePos.y);

            // Met à jour la boîte de collision du projectile
            float w = Projectiles.ENERGY_BALL.getWidth();
            float h = Projectiles.ENERGY_BALL.getHeight();
            e.hurtbox.set(e.projectilePos.x, e.projectilePos.y, e.projectilePos.x + w, e.projectilePos.y + h);
        }
    }

    //endregion
    private boolean isThereMoreWaves() {
        return WaveManager.isTherMoreWaves();
    }

    private boolean isAllEnemiesDead() {
        if (skeletons.isEmpty() && reptils.isEmpty()) {
            if (waveManager.isThereMoreEnemiesInWaves()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public void kill() {
        skeletons.clear();
        reptils.clear();
    }

    //region vagues
    private void updateWaveManager() {
        this.getWaveManager().update();
    }

    private void spawnEnemy() {
        addEnemy(this.getWaveManager().getNextEnemy(), 0, 800);
    }

    public void addEnemy(int enemyType, float x, float y) {
        Log.d("Playing", "Adding enemy of type: " + enemyType);
        switch (enemyType) {
            case SKELLETON:
                currentWaypointIndex=0;
                spawnSkeleton(x,y,currentWaypointIndex);
                break;
            case REPTIL:
                spawnReptil(0, 800);
                break;
        }
    }

    private boolean isTimeForNewEnemy() {
        //Log.d("isTimeForNewEnemy", "Adding enemy of type: ");
        if (this.getWaveManager().isThereMoreEnemiesInWaves() && this.getWaveManager().isTimeForNewEnemyq()) {
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
        c.drawRect(stripeLeft, stripeTop, stripeRight, stripeBottom, yellowPaint);
        //endregion

        //region draw tour / ennemies
        for (Skeleton skeleton : skeletons)
            if (skeleton.isActive())
                drawCharacter(c, skeleton);

        for (Reptil reptil : reptils)
            if (reptil.isActive())
                drawCharacter(c, reptil);

        for (EskimoNinja e : eskimoNinjas)
            drawCharacter(c, e);

        for (Knight k : knights)
            drawCharacter(c, k);

        for (Spirit s : spirits)
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
        buttonY3 = stripeTop + 2 * decalage_y_selection_tour + 30;

        // Dessiner l'image redimensionnée dans le rectangle
        c.drawBitmap(Bitmap.createScaledBitmap(buttonImage3, newWidth, newHeight, false),
                buttonX3, buttonY3, yellowPaint);
        //endregion
        Bitmap start = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.start);
        c.drawBitmap(Bitmap.createScaledBitmap(start, 100, 100, false), 0, 830, yellowPaint);
        drawUI(c);
        Bitmap end = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.end);
        c.drawBitmap(Bitmap.createScaledBitmap(end, 100, 100, false), 1620, 10, yellowPaint);

        //region affichage PV
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);  // Taille du texte
        textPaint.setTextAlign(Paint.Align.CENTER);  // Alignement du texte
        String TextPV = PV+"\u2764";
        float xPVText = 100;
        float yPVText = 55;
        Typeface typeface = Typeface.create("Sans", Typeface.BOLD);
        textPaint.setTypeface(typeface);
        c.drawText(TextPV, xPVText, yPVText, textPaint);
        //endregion

        //region affichage argent
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);  // Taille du texte
        textPaint.setTextAlign(Paint.Align.CENTER);  // Alignement du texte
        String TextArgent = Argent+"\uD83D\uDCB0";
        float xArgentText = 100;
        float yArgentText = 120;
        textPaint.setTypeface(typeface);
        c.drawText(TextArgent, xArgentText, yArgentText, textPaint);
        //endregion

        //region affichage cout tour
        String coutEskimo = PRIXESKIMONIJA+" \uD83D\uDCB0";
        float xPrixEskimo = (stripeLeft + stripeRight) / 2;;
        float yPrixEskimo = 205;
        textPaint.setTypeface(typeface);
        c.drawText(coutEskimo, xPrixEskimo, yPrixEskimo, textPaint);

        String coutKnight = PRIXKNIGHT+"\uD83D\uDCB0";
        float xPrixKnight = (stripeLeft + stripeRight) / 2;;
        float yPrixKnight = 605;

        textPaint.setTypeface(typeface);
        c.drawText(coutKnight, xPrixKnight, yPrixKnight, textPaint);

        String coutSpirit = PRIXSPIRIT+" \uD83D\uDCB0";
        float xPrixSpirit = (stripeLeft + stripeRight) / 2;;
        float yPrixSpirit = 405;

        textPaint.setTypeface(typeface);
        c.drawText(coutSpirit, xPrixSpirit, yPrixSpirit, textPaint);
        //endregion

        drawUI(c);
        for (Spirit s : spirits)
            drawEnergyBall(c, s);

        for(EskimoNinja e :eskimoNinjas)
            drawIceSpike(c, e);

    }

    public void drawEnergyBall(Canvas c,Spirit s)
    {
        if(s.hurtbox != null && s.projectileActive){
            c.drawBitmap(Projectiles.ENERGY_BALL.getProjectileImg(),
                    s.hurtbox.left,
                    s.hurtbox.top,
                    null);
            Log.d("hurtboxDraw", "hurtbox après dessin " + s.hurtbox);
            c.drawRect(s.hurtbox, redPaint);
        }
    }
    public void drawIceSpike(Canvas c,EskimoNinja e)
    {
        if(e.hurtbox != null && e.projectileActive){
            c.drawBitmap(Projectiles.ICE_SPIKE.getProjectileImg(),
                    e.hurtbox.left,
                    e.hurtbox.top,
                    null);
            Log.d("hurtboxDraw eskimo", "hurtbox après dessin " + e.hurtbox);
            c.drawRect(e.hurtbox, redPaint);
        }
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
            //kill();
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


            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:




                // Vérifier si le clic est à l'intérieur des coordonnées de l'image
                if (eskimo_selected) {
                    if(Argent>=PRIXESKIMONIJA) {
                        spawnEskimo(x, y);
                        Argent-=PRIXESKIMONIJA;
                    }
                    eskimo_selected = false;
                }
                if (knight_selected) {
                    if(Argent>=PRIXKNIGHT) {
                        spawnKnight(x, y);
                        Argent-=PRIXKNIGHT;
                    }
                    knight_selected = false;
                }
                if (spirit_selected) {
                    if(Argent>=PRIXSPIRIT) {
                        spawnSpirit(x, y);
                        Argent-=PRIXSPIRIT;
                    }
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
                k.drawWeapon(canvas);
            }

        }
    }
    public WaveManager getWaveManager(){
        return waveManager;
    }

    //region Méthodes spawn
    public void spawnSkeleton(float spawnX, float spawnY,int currentWaypointIndex) {
        synchronized (skeletons) {
            skeletons.add(new Skeleton(new PointF(spawnX,spawnY),currentWaypointIndex));
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

    private void goToGameOverPage() {
        Intent intent = new Intent(MainActivity.getGameContext(), GameOver.class);
        startActivity(MainActivity.getGameContext(), intent, null);
    }

}