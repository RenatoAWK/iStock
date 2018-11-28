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

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        emailEditText = findViewById(R.id.editEmailLogin);
        passwordEditText = findViewById(R.id.editPasswordLogin);

        emailEditText.requestFocus();

    }

    public void login(View view){

        Validations validations = new Validations();

        boolean valid = true;

        if (!validations.editValidate(emailEditText)){
            emailEditText.requestFocus();
            emailEditText.setError(getString(R.string.requiredField));
            valid = false;
        }
        if (!validations.editValidate(passwordEditText)){
            passwordEditText.requestFocus();
            passwordEditText.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!valid){
            return;
        }

        if (!validations.email(emailEditText.getText().toString())){
            emailEditText.requestFocus();
            emailEditText.setError(getString(R.string.invalid_email));
            valid = false;
        }

        if (!valid){
            return;
        }

        UserServices userServices = new UserServices(getApplicationContext());

        String email = emailEditText.getText().toString().trim().toUpperCase();
        String password = passwordEditText.getText().toString();
        User user = userServices.login(email, password);
        if (user == null){
            emailEditText.setError(getString(R.string.invalid_email_or_password));
            passwordEditText.setError(getString(R.string.invalid_email_or_password));

        }
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            finish();

            startActivity(intent);

        }

    }

    public void CreateAccount(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        Bundle bundle = new Bundle();
        String email = emailEditText.getText().toString();
        bundle.putString("email",email);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
}
