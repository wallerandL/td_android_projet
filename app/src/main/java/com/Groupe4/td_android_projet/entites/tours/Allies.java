package com.Groupe4.td_android_projet.entites.tours;


import android.graphics.PointF;

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.GameSheet;

// correspond à "Player" dans le tuto, probablement faire un héritage pour
public class Allies extends Character {
    public Allies(PointF pos) {
        super(pos, GameSheet.PLAYER);
    }

    public void update(double delta, boolean ennemyInRange)
    {
        // booleen 'movePlayer' remplace par 'ennemyInRange' puisqu'on ne bouge pas le joueur
        // A voir si on màj l'animation de la tour, si on fait un autre traitement, sinon virer ca completement
        if (ennemyInRange)
            updateAnimation();

    }
}