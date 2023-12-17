package com.Groupe4.td_android_projet.entites.enemies;

import android.app.Notification;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.GameSheet;
import com.Groupe4.td_android_projet.environement.Cell;
import com.Groupe4.td_android_projet.helpers.GameConstants;
import static com.Groupe4.td_android_projet.Main.MainActivity.GAME_HEIGHT;
import static com.Groupe4.td_android_projet.Main.MainActivity.GAME_WIDTH;
//import static com.Groupe4.td_android_projet.environement.pathfinding.TowerDefensePathfinding.findPath;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Animation.TILE;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Animation.TILEHAUT;
import static com.Groupe4.td_android_projet.helpers.GameConstants.Face_Dir.RIGHT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Skeleton extends Character {
    public Notification.Builder redPaint;
    private long lastDirChange = System.currentTimeMillis();
    private Random rand = new Random();
    PointF nextWaypoint;
    private Cell[][] grid;
    public int invincibilityFrame;
    private List<PointF> waypoints;
    private int currentWaypointIndex=0;
    private int[] faceDir2= {3,1,3,1};
    int facDireX;


    public Skeleton(PointF pos) {
        super(pos, GameSheet.SKELETON);
        //faceDir=RIGHT;
        //initializeGrid();
        //updatePath();
        this.invincibilityFrame = 10;
        waypoints = new ArrayList<>();
//        waypoints.add(new PointF(5*160,0)); // sur place

        waypoints.add(new PointF(6*TILE, 5*TILE)); // Vers la droite
        waypoints.add(new PointF(TILE*6, 1*TILEHAUT)); // Vers le haut
        waypoints.add(new PointF(TILE*12, 1*TILE)); // Vers la droite
        waypoints.add(new PointF(TILE*12, 0*TILE)); // Vers le haut
        Log.v("nexwaypointif","currentWaypointIndex : "+waypoints);

    }

    private List<Cell> path;
    private int currentPathIndex = 0;

    public void update(double delta){

        updateMove(delta);
        updateAnimation();
        //exampleUsage();

    }
    private void initializeGrid() {
        // Example: Create a 2D array of cells, where true represents walkable cells
        grid = new Cell[][]{
                {new Cell(0, 0, false), new Cell(0, 1, false), new Cell(0, 2, false), new Cell(0, 3, false), new Cell(0, 4, false), new Cell(0, 5, false),new Cell(0, 6, false), new Cell(0, 7, false), new Cell(0, 8, false),new Cell(0, 9, false), new Cell(0, 10, true), new Cell(0, 11, false),new Cell(0, 12, false)},
                {new Cell(1, 0, false), new Cell(1, 1, false), new Cell(1, 2, false), new Cell(1, 3, false), new Cell(1, 4, true), new Cell(1, 5, true),new Cell(1, 6, true), new Cell(1, 7, true), new Cell(1, 8, true),new Cell(1, 9, true), new Cell(1, 10, true), new Cell(1, 11, false),new Cell(1, 12, false)},
                {new Cell(2, 0, false), new Cell(2, 1, false), new Cell(2, 2, false), new Cell(2, 3, false), new Cell(2, 4, true), new Cell(2, 5, false),new Cell(2, 6, false), new Cell(2, 7, false), new Cell(2, 8, false),new Cell(2, 9, false), new Cell(2, 10, false), new Cell(2, 11, false),new Cell(2, 12, false)},
                {new Cell(3, 0, false), new Cell(3, 1, false), new Cell(3, 2, false), new Cell(3, 3, false), new Cell(3, 4, true), new Cell(3, 5, false),new Cell(3, 6, false), new Cell(3, 7, false), new Cell(3, 8, false),new Cell(3, 9, false), new Cell(3, 10, false), new Cell(3, 11, false),new Cell(3, 12, false)},
                {new Cell(4, 0, false), new Cell(4, 1, false), new Cell(4, 2, false), new Cell(4, 3, false), new Cell(4, 4, true), new Cell(4, 5, false),new Cell(4, 6, false), new Cell(4, 7, false), new Cell(4, 8, false),new Cell(4, 9, false), new Cell(4, 10, false), new Cell(4, 11, false),new Cell(4, 12, false)},
                {new Cell(5, 0, true), new Cell(5, 1, true), new Cell(5, 2, true), new Cell(5, 3, true), new Cell(5, 4, true), new Cell(5, 5, false),new Cell(5, 6, false), new Cell(5, 7, false), new Cell(5, 8, false),new Cell(5, 9, false), new Cell(5, 10, false), new Cell(5, 11, false),new Cell(5, 12, false)},
                {new Cell(6, 0, false), new Cell(6, 1, false), new Cell(6, 2, false), new Cell(60, 3, false), new Cell(6, 4, false), new Cell(6, 5, false),new Cell(6, 6, false), new Cell(6, 7, false), new Cell(6, 8, false),new Cell(6, 9, false), new Cell(6, 10, false), new Cell(6, 11, false),new Cell(6, 12, false)},
        };
    }

    private void updateMove(double delta) {
 //       int previousWayPointIndex=10;

        if (currentWaypointIndex < waypoints.size()) {

            nextWaypoint = waypoints.get(currentWaypointIndex);
            Log.v("nexwaypoint","nextWaypoint : "+nextWaypoint);
            Log.v("nexwaypoint","FaceDIreX : "+facDireX);
            Log.v("nexwaypoint","currentWaypointIndex : "+currentWaypointIndex);
            moveTowards(nextWaypoint, delta);
            Log.v("ditancebetween","avant");
            if (distanceBetween(hitbox, nextWaypoint) < 160) {
                Log.v("ditancebetween","apres");
                Log.v("nexwaypointif","currentWaypointIndex : "+currentWaypointIndex);
//                previousWayPointIndex=currentWaypointIndex;
                currentWaypointIndex++;
                facDireX++;
                Log.v("nexwaypointif","currentWaypointIndex : "+currentWaypointIndex);
//                if (currentWaypointIndex==1)
//                    facDireX=1;
//                if (currentWaypointIndex==2)
//                    facDireX=2;
//                if (currentWaypointIndex==3)
//                    facDireX=3;
            }
/*            if (previousWayPointIndex==currentWaypointIndex) {
                Log.v("nexwaypoint","previousWayPointIndex : "+currentWaypointIndex);
                currentWaypointIndex++;
            }*/
        }
//        updateAnimation();
    }

    private void moveTowards(PointF target, double delta) {
        faceDir=faceDir2[currentWaypointIndex];
        System.out.println("faeDir "+faceDir);
        switch (faceDir){
            case GameConstants.Face_Dir.DOWN:
                hitbox.top += delta * 160;
                hitbox.bottom += delta * 160;
//                if (hitbox.top >= GAME_HEIGHT)
//                    faceDir = GameConstants.Face_Dir.UP;
                break;

            case GameConstants.Face_Dir.UP:
                hitbox.top -= delta * 160;
                hitbox.bottom -= delta * 160;
//                if (hitbox.top <= 0)
//                    faceDir = GameConstants.Face_Dir.DOWN;
                break;

            case RIGHT:
                hitbox.left += delta * 160;
                hitbox.right += delta * 160;
//                if (hitbox.left >= GAME_WIDTH)
//                    faceDir = GameConstants.Face_Dir.LEFT;
                break;

            case GameConstants.Face_Dir.LEFT:
                hitbox.left -= delta * 160;
                hitbox.right -= delta * 160;
//                if (hitbox.left <= 0)
//                    faceDir = RIGHT;
                break;
        }
    }

    public float distanceBetween(RectF point1, PointF point2) {
        float dx = point2.x - point1.right;
        float dy = point2.y - point1.top;
        Log.v("distanceBetween"," "+dy);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
    private int determineDirection(PointF target) {
        float deltaX = target.x - hitbox.centerX();
        float deltaY = target.y - hitbox.centerY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Si la différence en x est plus grande, déplacer horizontalement
            if (deltaX > 0) {
                return GameConstants.Face_Dir.RIGHT;
            } else if(deltaX<0){
                return GameConstants.Face_Dir.LEFT;
            }
        } else {
            // Sinon, déplacer verticalement
            if (deltaY > 0) {
                return GameConstants.Face_Dir.DOWN;
            } else if(deltaY<0){
                return GameConstants.Face_Dir.UP;
            }
        }
        return GameConstants.Face_Dir.UP;
    }
}
