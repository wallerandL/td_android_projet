package com.Groupe4.td_android_projet.Main;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.Groupe4.td_android_projet.R;

public class GameOver extends AppCompatActivity {
    private Button buttonReplay;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game_over);

            buttonReplay = findViewById(R.id.buttonReplay);
            buttonReplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Rediriger vers l'activité du jeu
                    Intent intent = new Intent(GameOver.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Fermer l'activité Game Over
                }
            });
        }
    }

