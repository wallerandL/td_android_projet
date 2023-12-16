package com.Groupe4.td_android_projet.entites;

import android.graphics.*;
public abstract class Entity {

    protected RectF hitbox;

    protected boolean active = true;

    public Entity(PointF pos, float width, float height)
    {
        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public RectF getHitbox() {
        return hitbox;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }


}
