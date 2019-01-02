package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.SessionServices;
import bsi.mpoo.istock.services.UserServices;
import bsi.mpoo.istock.services.Validations;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        emailEditText = findViewById(R.id.editEmailLogin);
        passwordEditText = findViewById(R.id.editPasswordLogin);
        switchButton = findViewById(R.id.switchButtonLogin);
        SessionServices sessionServices = new SessionServices(getApplicationContext());
        Session session =  sessionServices.getSession();
        if (session != null){
            if (session.getRemember() == Constants.Session.NOT_TO_REMEMBER){
                sessionServices.clearSession();
            } else {
                Session.getInstance().setId(session.getId());
                Session.getInstance().setRemember(session.getRemember());
                Session.getInstance().setId_user(session.getId_user());
                Session.getInstance().setUser(session.getUser());
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);

            }
        }
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
            Session.getInstance().setUser(searchedUser);
            Session.getInstance().setId_user(searchedUser.getId());
            boolean switchState = switchButton.isChecked();
            int remember;
            if (switchState){
                remember = Constants.Session.REMEMBER;
            } else {
                remember = Constants.Session.NOT_TO_REMEMBER;
            }
            Session.getInstance().setRemember(remember);
            SessionServices sessionServices = new SessionServices(getApplicationContext());
            sessionServices.updateSession(Session.getInstance());
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    private boolean isAllFieldsValid(Validations validations) {
        boolean valid = validations.editValidate(emailEditText, passwordEditText);

        if (!validations.email(emailEditText.getText().toString())){
            validations.setErrorIfNull(emailEditText, getString(R.string.invalid_email));
            valid = false;
        }

        return valid;
    }

    public void CreateAccount(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        String email = emailEditText.getText().toString();
        bundle.putString(Constants.BundleKeys.EMAIL,email);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
}
