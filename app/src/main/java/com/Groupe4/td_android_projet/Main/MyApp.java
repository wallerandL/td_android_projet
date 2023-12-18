package com.Groupe4.td_android_projet.Main;

import android.app.Application;

public class MyApp extends Application {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }
}

