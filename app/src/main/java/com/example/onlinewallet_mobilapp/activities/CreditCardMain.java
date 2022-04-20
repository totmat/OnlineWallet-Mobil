package com.example.onlinewallet_mobilapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinewallet_mobilapp.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinewallet_mobilapp.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class CreditCardMain extends AppCompatActivity {

    TextInputEditText textInputLayoutcardNumber, textInputLayoutDate, textInputLayoutSecurityCode, textInputLayoutFirstName, textInputLayoutLastName;
    Button creditCardUpload;
    TextView textViewPersonalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_main);

        textInputLayoutcardNumber = findViewById(R.id.et_credit_card_number);
        textInputLayoutDate = findViewById(R.id.et_expiration_date);
        textInputLayoutSecurityCode = findViewById(R.id.et_cvv);
        textInputLayoutFirstName = findViewById(R.id.et_first_name);
        textInputLayoutLastName = findViewById(R.id.et_last_name);
        textViewPersonalCard = findViewById(R.id.personalText);
        creditCardUpload = findViewById(R.id.btn_upload);

        if (!validationSecurityCode())
        {
            return;
        }

        textViewPersonalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        creditCardUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber, date, securityCode, firstName, lastName;
                cardNumber = String.valueOf(textInputLayoutcardNumber.getText());
                date = String.valueOf(textInputLayoutDate.getText());
                securityCode = String.valueOf(textInputLayoutSecurityCode.getText());
                firstName = String.valueOf(textInputLayoutFirstName.getText());
                lastName = String.valueOf(textInputLayoutLastName.getText());

                if (!cardNumber.equals("") && !date.equals("") && !securityCode.equals("") && !firstName.equals("") && !lastName.equals("")) {
                    ;
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
                                    if (result.equals("Card upload Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private Boolean validationSecurityCode()
    {
        String val = textInputLayoutSecurityCode.getText().toString();
        if(val.isEmpty())
        {
            textInputLayoutSecurityCode.setError("Hiba");
            return false;
        }
        else if (val.length()>3)
        {
            textInputLayoutSecurityCode.setError("Hiba");
            return false;
        }
        else
        {
            textInputLayoutSecurityCode.setError(null);
            return true;
        }
    }

}





