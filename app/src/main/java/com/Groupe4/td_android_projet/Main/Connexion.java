package com.Groupe4.td_android_projet.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.util.HashMap;
import java.util.Map;

public class Connexion extends AppCompatActivity {
    private EditText login;
    private EditText passwordEdit;
    private Button connexion;
    private Button inscription;

    private TextView errorConnect;
    private String username;
    private String password;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
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

        this.login = findViewById(R.id.editTextTextPersonName2);
        this.passwordEdit = findViewById(R.id.editTextTextPassword3);
        this.connexion = findViewById(R.id.button2);
        this.inscription = findViewById(R.id.button4);

        databaseManager = new DatabaseManager(getApplicationContext());

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = login.getText().toString();
                password = passwordEdit.getText().toString();

                connectUser();
            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Connexion.this, Inscription.class);
                startActivity(intent);
            }
        });
    }

    public void onApiReponse(JSONObject reponse) {
        Boolean success = null;
        String error = "";

        try {
            success = reponse.getBoolean("success");

            if (success) {
                Intent intent = new Intent(Connexion.this, MainActivity.class);
                startActivity(intent);
            } else {
                error = reponse.getString("error");
                errorConnect.setVisibility(View.VISIBLE);
                errorConnect.setText(error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void connectUser() {
        String url = "http://10.0.2.2/projetAndroid/action/connexion.php";

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        databaseManager.queue.add(jsonObjectRequest);
    }
}
