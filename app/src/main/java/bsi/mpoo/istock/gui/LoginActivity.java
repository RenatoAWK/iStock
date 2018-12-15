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
    }

    public void login(View view){

        Validations validations = new Validations(getApplicationContext());
        if (!isAllFieldsValid(validations)) return;
        UserServices userServices = new UserServices(getApplicationContext());
        String email = emailEditText.getText().toString().trim().toUpperCase();
        String password = passwordEditText.getText().toString();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User searchedUser = userServices.login(user);

        if (searchedUser == null){
            emailEditText.setError(getString(R.string.invalid_email_or_password));
            passwordEditText.setError(getString(R.string.invalid_email_or_password));
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", searchedUser);
            finish();
            startActivity(intent);
        }
    }

    private boolean isAllFieldsValid(Validations validations) {
        boolean valid = validations.editValidate(emailEditText, passwordEditText);

        if (!validations.email(emailEditText.getText().toString())){
            if (emailEditText.getError() == null){
                emailEditText.requestFocus();
                emailEditText.setError(getString(R.string.invalid_email));
            }
            valid = false;
        }

        return valid;
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
