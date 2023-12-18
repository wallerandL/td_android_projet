package com.Groupe4.td_android_projet.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Groupe4.td_android_projet.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GameOver extends AppCompatActivity {
    private Button buttonReplay;
    private TextView textViewScore;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        buttonReplay = findViewById(R.id.buttonReplay);
        textViewScore = findViewById(R.id.textViewScore);

        databaseManager = new DatabaseManager(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SCORE")) {
            final int score = intent.getIntExtra("SCORE", 0);
            Log.d("GameOver", "Score: " + score);
            textViewScore.setText("Score: " + score);

            buttonReplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Envoyer le score à la base de données sans le nom d'utilisateur
                    sendScoreToDatabase(score);

                    // Rediriger vers l'activité du jeu
                    Intent intent = new Intent(GameOver.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Fermer l'activité Game Over
                }
            });
        }
    }

    private void sendScoreToDatabase(int score) {
        String url = "http://10.0.2.2/projetAndroid/action/score.php";

        // Ajouter le paramètre score
        Map<String, String> params = new HashMap<>();
        params.put("score", String.valueOf(score));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onApiResponse(response);

                        Toast.makeText(getApplicationContext(), "Opération réussie", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);

                        Log.e("Volley Error", "Erreur lors de la requête", error);
                        Toast.makeText(getApplicationContext(), "Erreur lors de la requête", Toast.LENGTH_LONG).show();

                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                Log.e("Volley Error Response", responseBody);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        // Utilisation d'une queue de requêtes (vous devez initialiser cette queue dans votre gestionnaire de base de données)
        databaseManager.queue.add(jsonObjectRequest);
    }

    private void onApiResponse(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");

            if (success) {
                Toast.makeText(getApplicationContext(), "Score envoyé avec succès", Toast.LENGTH_LONG).show();
            } else {
                String error = response.getString("error");
                Toast.makeText(getApplicationContext(), "Erreur : " + error, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Toast.makeText(getApplicationContext(), "Erreur lors de la requête : " + responseBody, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Erreur lors de la requête", Toast.LENGTH_LONG).show();
        }
    }
}
