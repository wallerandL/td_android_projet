package com.Groupe4.td_android_projet.helpers;

public class GameConstants {

    public static final class Face_Dir{
        public static final int DOWN = 0;
        public static final int UP = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }


    public static final class Sprite{
        public static final int DEFAULT_SIZE = 16;
        public static final int SCALE_MULTIPLIER = 10;
        public static final int SIZE = DEFAULT_SIZE * SCALE_MULTIPLIER;
    }

    public static final class Animation{
        public static final int SPEED = 10;
        public static final int AMOUNT = 4;
        public static int TILE=160;
        public static int TILEHAUT=135;

    }
    public static final class Enemies{
        public static final int SKELLETON = 0;
        public static final int REPTIL = 1;

    }
}
