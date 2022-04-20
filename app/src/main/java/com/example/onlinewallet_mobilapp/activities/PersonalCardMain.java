package com.example.onlinewallet_mobilapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinewallet_mobilapp.MainActivity;
import com.example.onlinewallet_mobilapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PersonalCardMain extends AppCompatActivity {

    TextInputEditText textInputLayoutdocumentId, textInputLayoutfullname,textInputLayoutGender,textInputLayoutYear,textInputLayoutMonth,textInputLayoutDay;
    Button DocumentIdUpload;

    TextView textViewCreditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_card_main);

        textInputLayoutdocumentId = findViewById(R.id.et_personal_card_document_iD);
        textInputLayoutfullname = findViewById(R.id.et_personal_card_fullname);
        textInputLayoutGender = findViewById(R.id.et_personal_card_gender);
        textInputLayoutYear = findViewById(R.id.et_personal_date_year);
        textInputLayoutMonth = findViewById(R.id.et_personal_date_month);
        textInputLayoutDay = findViewById(R.id.et_personal_date_day);

        DocumentIdUpload = findViewById(R.id.btn_upload);

        textViewCreditCard = findViewById(R.id.credit_cardText);

        textViewCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DocumentIdUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String documentId, fullname, gender, year, month, day;
                documentId = String.valueOf(textInputLayoutdocumentId.getText());
                fullname = String.valueOf(textInputLayoutfullname.getText());
                gender = String.valueOf(textInputLayoutGender.getText());
                year = String.valueOf(textInputLayoutYear.getText());
                month = String.valueOf(textInputLayoutMonth.getText());
                day = String.valueOf(textInputLayoutDay.getText());
                if(!documentId.equals("") && !fullname.equals("") && !gender.equals("") && !year.equals("") && !month.equals("") && !day.equals("")) { ;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "documentId";
                            field[1] = "fullname";
                            field[2] = "gender";
                            field[3] = "year";
                            field[4] = "month";
                            field[5] = "day";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = documentId;
                            data[1] = fullname;
                            data[2] = gender;
                            data[3] = year;
                            data[4] = month;
                            data[5] = day;
                            PutData putData = new PutData("http://192.168.0.27/onlinewallet/igazolvanyfeltoltes.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("ID upload Success")){
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
