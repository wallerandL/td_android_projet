package com.Groupe4.td_android_projet.entites.tours;

import android.graphics.PointF;

import com.Groupe4.td_android_projet.entites.Character;
import com.Groupe4.td_android_projet.entites.GameSheet;

public class Spirit extends Character {
    public Spirit(PointF pos){super(pos, GameSheet.SPIRIT);}

    public void update(double delta, boolean ennemyInRange)
    {
        // booleen 'movePlayer' remplace par 'ennemyInRange' puisqu'on ne bouge pas le joueur
        // A voir si on màj l'animation de la tour, si on fait un autre traitement, sinon virer ca completement
        if (ennemyInRange)
            updateAnimation();

    }
}
