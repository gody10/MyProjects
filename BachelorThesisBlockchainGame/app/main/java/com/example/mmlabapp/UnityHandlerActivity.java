package com.example.mmlabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.unity3d.player.UnityPlayerActivity;

public class UnityHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity_handler);

        Button buttonGame = findViewById(R.id.buttonGame);

        buttonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnityHandlerActivity.this, UnityPlayerActivity.class);
                startActivity(intent);
            }
        });

    }
}