package com.example.onlinewallet_mobilapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinewallet_mobilapp.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinewallet_mobilapp.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class CreditCardMain extends AppCompatActivity {
    //Database layout
    TextInputEditText textInputLayoutcardNumber, textInputLayoutDate, textInputLayoutSecurityCode, textInputLayoutFirstName, textInputLayoutLastName;
    //Validation text input
    TextInputLayout textInputSecurityCode, textInputCardNumber, textInputExpirationDate, textInputFirstName, textInputLastName;
    //Upload button
    Button creditCardUpload;
    //Back to main
    TextView textViewPersonalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_main);

        //Database layout
        textInputLayoutcardNumber = findViewById(R.id.et_credit_card_number);
        textInputLayoutDate = findViewById(R.id.et_expiration_date);
        textInputLayoutSecurityCode = findViewById(R.id.et_cvv);
        textInputLayoutFirstName = findViewById(R.id.et_first_name);
        textInputLayoutLastName = findViewById(R.id.et_last_name);

        //Validation text input
        textInputSecurityCode = findViewById(R.id.til_cvv);
        textInputCardNumber = findViewById(R.id.til_credit_card_number);
        textInputExpirationDate = findViewById(R.id.til_expiration_date);
        textInputFirstName = findViewById(R.id.til_first_name);
        textInputLastName = findViewById(R.id.til_last_name);

        //Upload button
        creditCardUpload = findViewById(R.id.btn_upload);
        //Back to main
        textViewPersonalCard = findViewById(R.id.personalText);

        //Car number insertion -
        textInputLayoutcardNumber.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19; // sample 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // the maximum number of digits in the sample is 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means that the position of the dealer is a symbol starting with every 5.1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means that the position of the divisor is a symbol starting with 4.0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; //ellenőrizze a beírt karakterlánc méretét
                for (int i = 0; i < s.length(); i++) { // ellenőrizze, hogy minden elem megfelelő-e
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }
                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        //Month and Year insertion /
        textInputLayoutDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int len=s.toString().length();

                    if (before == 0 && len == 2)
                        textInputLayoutDate.append("/");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        //Back to main
        textViewPersonalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Database upload code
        creditCardUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Database variable
                String cardNumber, date, securityCode, firstName, lastName;
                cardNumber = String.valueOf(textInputLayoutcardNumber.getText());
                date = String.valueOf(textInputLayoutDate.getText());
                securityCode = String.valueOf(textInputLayoutSecurityCode.getText());
                firstName = String.valueOf(textInputLayoutFirstName.getText());
                lastName = String.valueOf(textInputLayoutLastName.getText());

                //Validation invitation
                if (!validationSecurityCode() && !validationCardNumber() && !validationExpirationDate() && !validationFirstName() && !validationLastName()) {
                    return;
                }
                //Variable invitation
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
                            PutData putData = new PutData("http://IPCIM/onlinewallet/bankkartyafeltoltes.php", "POST", field, data);
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

    //Validation CardNumber
    private Boolean validationCardNumber() {
        String val = textInputCardNumber.getEditText().getText().toString();
        if (val.isEmpty()) {
            textInputCardNumber.setError("Missing card number");
            return false;
        } else if (val.length() < 19) {
            textInputCardNumber.setError("Missing card number");
            return false;
        } else if (val.length() > 19) {
            textInputCardNumber.setError("Missing card number");
            return false;
        } else {
            textInputCardNumber.setError(null);
            return true;
        }
    }

    //Validation SecurityCode
    private Boolean validationSecurityCode() {
        String val = textInputSecurityCode.getEditText().getText().toString();
        if (val.isEmpty()) {
            textInputSecurityCode.setError("Missing cvv");
            return false;
        } else if (val.length() < 3) {
            textInputSecurityCode.setError("Missing cvv");
            return false;
        } else {
            textInputSecurityCode.setError(null);
            return true;
        }
    }

    //Validation ExpirationDate
    private Boolean validationExpirationDate() {
        String val = textInputExpirationDate.getEditText().getText().toString();
        String noSlashCharacter = "($+?=\\+$)";
        if (val.isEmpty()) {
            textInputExpirationDate.setError("Missing expiration date");
            return false;
        } else if (val.length() < 5) {
            textInputExpirationDate.setError("Missing expiration date");
            return false;
        } else {
            textInputExpirationDate.setError(null);
            return true;
        }
    }

    //Validation FirstName
    private Boolean validationFirstName() {
        String val = textInputFirstName.getEditText().getText().toString();
        if (val.isEmpty()) {
            textInputFirstName.setError("Please enter first name");
            return false;
        } else {
            textInputFirstName.setError(null);
            return true;
        }
    }

    //Validation LastName
    private Boolean validationLastName() {
        String val = textInputLastName.getEditText().getText().toString();
        if (val.isEmpty()) {
            textInputLastName.setError("Please enter last name");
            return false;
        } else {
            textInputLastName.setError(null);
            return true;
        }
    }
}







