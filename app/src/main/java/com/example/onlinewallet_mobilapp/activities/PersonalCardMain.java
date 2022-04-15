package com.example.onlinewallet_mobilapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.onlinewallet_mobilapp.R;

public class PersonalCardMain extends AppCompatActivity {

    TextView textViewCreditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_card_main);

        textViewCreditCard = findViewById(R.id.credit_cardText);

        textViewCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditCardMain.class);
                startActivity(intent);
                finish();
            }
        });

    }

}