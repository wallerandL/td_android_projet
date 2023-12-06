package com.Groupe4.td_android_projet.entites;


import android.graphics.PointF;

// correspond à "Player" dans le tuto, probablement faire un héritage pour
public class Allies extends Character{
    public Allies(PointF pos, GameSheet gameSheetType) {
        super(pos, gameSheetType);
    }

    public void update(double delta, boolean ennemyInRange)
    {
        // booleen 'movePlayer' remplace par 'ennemyInRange' puisqu'on ne bouge pas le joueur
        // A voir si on màj l'animation de la tour, si on fait un autre traitement, sinon virer ca completement
        if (ennemyInRange)
            updateAnimation();

    }
}
