package com.example.onlinewallet_mobilapp.activities;

import com.example.onlinewallet_mobilapp.Register;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.example.onlinewallet_mobilapp.R;
import com.example.onlinewallet_mobilapp.data.CreditCard;
import com.example.onlinewallet_mobilapp.databinding.ActivityMainBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class CreditCardMain extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private TextInputLayout obCreditCardNumber;
    private TextInputLayout obExpirationDate;
    private TextInputLayout obCvv;
    private TextInputLayout obFirstName;
    private TextInputLayout obLastName;
    private TextInputEditText etCreditCardNumber;
    private TextInputEditText etExpirationDate;
    private TextInputEditText etCvv;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;

    TextView textViewPersonalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        obCreditCardNumber = mBinding.creditCardInputPanel.tilCreditCardNumber;
        obExpirationDate = mBinding.creditCardInputPanel.tilExpirationDate;
        obCvv = mBinding.creditCardInputPanel.tilCvv;
        obFirstName = mBinding.creditCardInputPanel.tilFirstName;
        obLastName = mBinding.creditCardInputPanel.tilLastName;
        etCreditCardNumber = mBinding.creditCardInputPanel.etCreditCardNumber;
        etExpirationDate = mBinding.creditCardInputPanel.etExpirationDate;
        etCvv = mBinding.creditCardInputPanel.etCvv;
        etFirstName = mBinding.creditCardInputPanel.etFirstName;
        etLastName = mBinding.creditCardInputPanel.etLastName;

        textViewPersonalCard = findViewById(R.id.personalText);

        textViewPersonalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalCardMain.class);
                startActivity(intent);
                finish();
            }
        });

        mBinding.creditCardInputPanel.btnSubmitPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCard(v);
            }
        });
    }


    public void validateCard(View view) {
        clearAnyPreviousErrorMessages();
        String creditCardNumber = etCreditCardNumber.getText().toString();
        String expirationDate = etExpirationDate.getText().toString();
        String cvv = etCvv.getText().toString();
        String firstName = CreditCard.cleanName(etFirstName.getText().toString());
        etFirstName.setText(firstName);
        String lastName = CreditCard.cleanName(etLastName.getText().toString());
        etLastName.setText(lastName);

        if (!CreditCard.isValidCardNumber(creditCardNumber)) {
            obCreditCardNumber.setError(getString(R.string.invalid_card_number));
            etCreditCardNumber.requestFocus();
        } else if (!CreditCard.isValidExpirationDate(expirationDate)) {
            obExpirationDate.setError(getString(R.string.invalid_expiration_date));
            etExpirationDate.requestFocus();
        } else if (!CreditCard.isValidCvv(creditCardNumber, cvv)) {
            obCvv.setError(getString(R.string.invalid_cvv));
            etCvv.requestFocus();
        } else if (firstName.isEmpty()) {
            obFirstName.setError(getString(R.string.please_enter_first_name));
            etFirstName.requestFocus();
        } else if (lastName.isEmpty()) {
            obLastName.setError(getString(R.string.please_enter_last_name));
            etLastName.requestFocus();
        } else {
            closeSoftKeyboard(view);
            CreditCard creditCard = new CreditCard(creditCardNumber, expirationDate, cvv, firstName, lastName);
            alertDialog(submitCreditCard(creditCard), null, getString(R.string.ok));
        }
    }

    private String submitCreditCard(CreditCard creditCard) {
        return getString(R.string.successful);
    }

    private void clearAnyPreviousErrorMessages() {
        obCreditCardNumber.setError(null);
        obExpirationDate.setError(null);
        obCvv.setError(null);
        obFirstName.setError(null);
        obLastName.setError(null);
    }

    public void closeSoftKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void alertDialog(String title, String message, String buttonText) {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, null)
                .show();
    }

}
