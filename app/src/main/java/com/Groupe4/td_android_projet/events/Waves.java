package com.Groupe4.td_android_projet.events;

import java.util.ArrayList;

public class Waves {
    private ArrayList<Integer> enemyList;
    public Waves(ArrayList<Integer> enemyList  ){
        this.enemyList=enemyList;
    }


    public ArrayList<Integer> getEnemyList(){
        return enemyList;
    }
}
