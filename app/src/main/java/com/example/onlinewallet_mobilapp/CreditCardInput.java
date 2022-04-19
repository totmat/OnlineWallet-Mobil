package com.example.onlinewallet_mobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onlinewallet_mobilapp.activities.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class CreditCardInput extends AppCompatActivity {

    TextInputEditText til_credit_card_number, til_expiration_date,til_cvv,til_first_name,til_last_name;
    Button btn_submit_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card_input);
        til_credit_card_number = findViewById(R.id.et_credit_card_number);
        til_expiration_date = findViewById(R.id.et_expiration_date);
        til_cvv = findViewById(R.id.et_cvv);
        til_first_name = findViewById(R.id.et_first_name);
        til_last_name = findViewById(R.id.et_last_name);
        btn_submit_payment = findViewById(R.id.btn_submit_payment);

        btn_submit_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber, date, securityCode, firstName, lastName;
                cardNumber = String.valueOf(til_credit_card_number.getText());
                date = String.valueOf(til_expiration_date.getText());
                securityCode = String.valueOf(til_cvv.getText());
                firstName = String.valueOf(til_first_name.getText());
                lastName = String.valueOf(til_last_name.getText());
                if(!cardNumber.equals("") && !date.equals("") && !securityCode.equals("") && !firstName.equals("") && !lastName.equals("")) { ;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "cardNumber";
                            field[1] = "date";
                            field[2] = "securityCode";
                            field[3] = "firstName";
                            field[4] = "lastName";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = cardNumber;
                            data[1] = date;
                            data[2] = securityCode;
                            data[3] = firstName;
                            data[4] = lastName;
                            PutData putData = new PutData("http://192.168.0.27/onlinewallet/bankkartyafeltoltes.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Card upload Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}