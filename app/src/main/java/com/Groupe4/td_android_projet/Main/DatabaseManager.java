package com.Groupe4.td_android_projet.Main;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class DatabaseManager {

    private Context context;

    public RequestQueue queue;
    public DatabaseManager(Context context)
    {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

}
