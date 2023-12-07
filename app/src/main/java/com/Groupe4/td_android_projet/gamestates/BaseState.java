package com.Groupe4.td_android_projet.gamestates;

import com.Groupe4.td_android_projet.Main.Game;

public abstract class BaseState {
    protected Game game;

    public BaseState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}