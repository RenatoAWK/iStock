package bsi.mpoo.istock.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.UserServices;
import bsi.mpoo.istock.services.AccountStatus;
import bsi.mpoo.istock.services.UserTypes;
import bsi.mpoo.istock.services.Validations;
import bsi.mpoo.istock.services.Exceptions.EmailAlreadyRegistered;


public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmationEditText;
    private EditText companyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");

        emailEditText = findViewById(R.id.editEmailRegister);
        passwordEditText = findViewById(R.id.editPasswordRegister);
        passwordConfirmationEditText = findViewById(R.id.editPasswordConfirmRegister);
        nameEditText = findViewById(R.id.editfullNameRegister);
        companyEditText = findViewById(R.id.editCompanyNameRegister);

        emailEditText.setText(email);

    }

    public void register(View view) {

        Validations validations = new Validations(getApplicationContext());

        if (!isAllFieldsValid(validations)){
            return;
        }

        UserServices userServices = new UserServices(this);

        User newUser = new User();
        newUser.setName(nameEditText.getText().toString());
        newUser.setEmail(emailEditText.getText().toString().trim().toUpperCase());
        newUser.setPassword(passwordEditText.getText().toString());
        newUser.setType(UserTypes.ADMINISTRATOR.getValue());
        newUser.setStatus(AccountStatus.ACTIVE.getValue());
        newUser.setCompany(companyEditText.getText().toString().trim());
        newUser.setAdministrator(-1);

        try {
            userServices.registerUser(newUser);

            String message = getString(R.string.register_done);

            new AlertDialogGenerator(this, message, true).invoke();

        } catch (EmailAlreadyRegistered error){

            String message = getString(R.string.email_already_registered);

            new AlertDialogGenerator(this, message, false).invoke();

            emailEditText.setText("");
            passwordEditText.setText("");
            passwordConfirmationEditText.setText("");

            emailEditText.requestFocus();

        } catch (Exception error){

            String message = getString(R.string.unknow_error);

            new AlertDialogGenerator(this, message, false);

            emailEditText.setText("");
            passwordEditText.setText("");
            passwordConfirmationEditText.setText("");

            emailEditText.requestFocus();

        }
    }

    private boolean isAllFieldsValid(Validations validations) {

        boolean valid = validations.editValdiade(nameEditText, companyEditText,
                emailEditText, passwordEditText, passwordConfirmationEditText);


        if (!validations.name(nameEditText.getText().toString())){
            if (nameEditText.getError() == null){
                nameEditText.requestFocus();
                nameEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.email(emailEditText.getText().toString())){
            if (emailEditText.getError() == null){
                emailEditText.requestFocus();
                emailEditText.setError(getString(R.string.invalid_email));
            }
            valid = false;
        }

        if (!validations.password(passwordEditText.getText().toString())){
            if (passwordEditText.getError() == null){
                passwordEditText.requestFocus();
                passwordEditText.setError(getString(R.string.invalid_password_weak));
            }
            valid = false;
        }

        if (!validations.password(passwordConfirmationEditText.getText().toString())){
            if (passwordConfirmationEditText.getError() == null){
                passwordConfirmationEditText.requestFocus();
                passwordConfirmationEditText.setError(getString(R.string.invalid_password_weak));
            }
            valid = false;
        }

        if (!validations.passwordEquals(
                passwordEditText.getText().toString(),
                passwordConfirmationEditText.getText().toString())){

                if (passwordEditText.getError() == null){
                    passwordEditText.requestFocus();
                    passwordEditText.setError(getString(R.string.invalid_password_not_equals));
                }
                if (passwordConfirmationEditText.getError() == null){
                    passwordConfirmationEditText.requestFocus();
                    passwordConfirmationEditText.setError(getString(R.string.invalid_password_not_equals));
                }
                valid = false;
        }
        return valid;
    }

}