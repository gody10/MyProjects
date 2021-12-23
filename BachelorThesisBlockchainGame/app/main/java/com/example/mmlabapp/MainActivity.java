package com.example.mmlabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    String text;
    String text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password = (EditText)findViewById(R.id.Password);
        username = (EditText)findViewById(R.id.Username);
    }



    public void send(View v){

        com.example.mmlabapp.MessageSender messageSender = new com.example.mmlabapp.MessageSender();
        text = username.getText().toString();
        text2 = password.getText().toString();
        messageSender.execute(text,text2);

        startActivity(new Intent(this, UnityHandlerActivity.class));


    }
}