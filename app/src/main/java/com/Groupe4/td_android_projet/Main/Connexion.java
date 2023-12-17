package com.Groupe4.td_android_projet.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Groupe4.td_android_projet.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Connexion extends AppCompatActivity {
    public static Connection conn = null;
    private EditText login = null;
    private EditText password = null;
    private Button connexion = null;
    private Button inscription = null;


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


        MysqlConnexion();

        this.login = (EditText) findViewById(R.id.editTextTextPersonName2);
        this.password = (EditText) findViewById(R.id.editTextTextPassword3);
        this.connexion = (Button) findViewById(R.id.button2);
        this.inscription = (Button) findViewById(R.id.button4);



        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ident = login.getText().toString();
                String pass = password.getText().toString();

                if (ident.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(Connexion.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Connexion.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void MysqlConnexion() {
        String jdbcURL = "jdbc:mysql://localhost:3306/android";
        String user = "user";
        String passwd = "user";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, user, passwd);
            Toast.makeText(Connexion.this, "Connexion reussi a la base de bonnées.", Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(Connexion.this, "Driver manquant." + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.v("marche pas ",""+ e.getMessage());
        } catch (java.sql.SQLException ex) {
            Toast.makeText(Connexion.this, "Connexion au serveur impossible." + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.v("marche pas sans raison",""+ ex.getMessage());

            Log.d("error1", "SQLException: " + ex.getMessage());
            Log.d("error2", "SQLState: " + ex.getSQLState());
            Log.d("error3", "VendorError: " + ex.getErrorCode());
        }
    }


}
