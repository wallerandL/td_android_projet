package com.Groupe4.td_android_projet.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Inscription extends AppCompatActivity {
    public static Connection conn = null;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView errorConnect;
    private Button inscriptionButton;
    private Button retour;
    private String username;
    private String password;
    private DatabaseManager databaseManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());


        errorConnect = findViewById(R.id.textViewError);
        usernameEditText = findViewById(R.id.editTextNomUtilisateur);
        passwordEditText = findViewById(R.id.editTextMotDePasse);
        inscriptionButton = findViewById(R.id.buttonInscription);
        retour  = findViewById(R.id.Retour);
        databaseManager = new DatabaseManager(getApplicationContext());

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inscription.this, Connexion.class);
                startActivity(intent);
            }
        });
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 username = usernameEditText.getText().toString();
                 password = passwordEditText.getText().toString();

                 createAccount();

            }
        });
    }


        public void onApiReponse(JSONObject reponse) {
            Boolean success = null;
            String error = "";

            try {
                success = reponse.getBoolean("success");

                if (success != null && success) {
                    Intent intent = new Intent(Inscription.this, Connexion.class);
                    startActivity(intent);
                    Log.d("Inscription", "Opération réussie");
                    Toast.makeText(getApplicationContext(), "Opération réussie", Toast.LENGTH_LONG).show();
                } else {
                    error = reponse.getString("error");
                    errorConnect.setVisibility(View.VISIBLE);
                    errorConnect.setText(error);
                    Log.e("Inscription", "Erreur : " + error);
                    // Toast.makeText(getApplicationContext(), "erreur de création du compte, Cet identifiant existe déjà", Toast.LENGTH_LONG).show();
                    // Affichez un message d'échec à l'utilisateur si nécessaire
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Erreur lors de la conversion JSON", Toast.LENGTH_LONG).show();
                Log.e("Inscription", "Erreur lors de la conversion JSON : " + e.getMessage());
                // Affichez un message d'échec à l'utilisateur si nécessaire
            }
        }

        public void createAccount(){
            String url = "http://10.0.2.2/projetAndroid/action/CretionUti.php";

            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            JSONObject parameters = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    onApiReponse(response);
                    Toast.makeText(getApplicationContext(), "Opération réussie", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
    }


