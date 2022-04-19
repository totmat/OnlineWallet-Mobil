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

public class PersonalCardInput extends AppCompatActivity {
    TextInputEditText til_personal_card_fullname, til_personal_card_document_iD,til_personal_card_gender,til_personal_card_date_year,til_personal_card_date_month,til_personal_card_date_day;
    Button btn_submit_upload_personal_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_card_input);
        til_personal_card_fullname = findViewById(R.id.et_personal_card_fullname);
        til_personal_card_document_iD = findViewById(R.id.et_personal_card_document_iD);
        til_personal_card_gender = findViewById(R.id.et_personal_card_gender);
        til_personal_card_date_year = findViewById(R.id.et_personal_date_year);
        til_personal_card_date_month = findViewById(R.id.et_personal_date_month);
        til_personal_card_date_day = findViewById(R.id.et_personal_date_day);

        btn_submit_upload_personal_card = findViewById(R.id.btn_submit_upload_personal_card);

        btn_submit_upload_personal_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doucmentId, fullname, gender, year, month, day;
                doucmentId = String.valueOf(til_personal_card_fullname.getText());
                fullname = String.valueOf(til_personal_card_document_iD.getText());
                gender = String.valueOf(til_personal_card_gender.getText());
                year = String.valueOf(til_personal_card_date_year.getText());
                month = String.valueOf(til_personal_card_date_month.getText());
                day = String.valueOf(til_personal_card_date_day.getText());
                if(!doucmentId.equals("") && !fullname.equals("") && !gender.equals("") && !year.equals("") && !month.equals("") && !day.equals("")) { ;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "doucmentId";
                            field[1] = "fullname";
                            field[2] = "gender";
                            field[3] = "year";
                            field[4] = "month";
                            field[5] = "day";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = doucmentId;
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