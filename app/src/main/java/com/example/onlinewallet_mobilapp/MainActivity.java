package com.example.onlinewallet_mobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlinewallet_mobilapp.activities.CreditCardMain;
import com.example.onlinewallet_mobilapp.activities.PersonalCardMain;

public class MainActivity extends AppCompatActivity {
    Button creditCardButton;
    Button personalCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creditCardButton = findViewById(R.id.creditCardButton);
        personalCardButton = findViewById(R.id.personalCardButton);


        creditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditCardMain.class);
                startActivity(intent);
                finish();
            }
        });

        personalCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalCardMain.class);
                startActivity(intent);
                finish();
            }
        });
    }
}