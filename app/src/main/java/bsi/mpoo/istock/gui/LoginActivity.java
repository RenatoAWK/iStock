package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.UserServices;
import bsi.mpoo.istock.services.Validations;

public class LoginActivity extends AppCompatActivity {

    private EditText EmailEditText;
    private EditText PasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        EmailEditText = findViewById(R.id.editEmailLogin);
        PasswordEditText = findViewById(R.id.editPasswordLogin);

    }

    public void login(View view){

        Validations validations = new Validations();

        boolean valid = true;

        if (!validations.editValidate(EmailEditText)){
            EmailEditText.requestFocus();
            EmailEditText.setError(getString(R.string.requiredField));
            valid = false;
        }
        if (!validations.editValidate(PasswordEditText)){
            PasswordEditText.requestFocus();
            PasswordEditText.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!valid){
            return;
        }

        if (!validations.email(EmailEditText.getText().toString())){
            EmailEditText.requestFocus();
            EmailEditText.setError(getString(R.string.invalid_email));
            valid = false;
        }
        if (!validations.password(PasswordEditText.getText().toString())){
            PasswordEditText.setError(getString(R.string.invalid_password));
        }

        if (!valid){
            return;
        }

        UserServices userServices = new UserServices(getApplicationContext());

        String email = EmailEditText.getText().toString().trim().toUpperCase();
        String password = PasswordEditText.getText().toString();
        User user = userServices.login(email, password);
        if (user == null){
            // Mostrar alguma mensagem que é inválido
        }
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
            // precisa levar o usuário
        }

    }

    public void CreateAccount(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
}
