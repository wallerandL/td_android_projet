package com.Groupe4.td_android_projet.entites;

import android.graphics.*;
public class Entity {

    protected RectF hitbox;

    public Entity(PointF pos, float width, float height)
    {
        this.hitbox = new RectF(pos.x, pos.y, width, height);
    }

    public RectF getHitbox() {
        return hitbox;
    }
}
