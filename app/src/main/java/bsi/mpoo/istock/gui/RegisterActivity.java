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
import bsi.mpoo.istock.services.UserServices;
import bsi.mpoo.istock.services.UserStatus;
import bsi.mpoo.istock.services.UserTypes;
import bsi.mpoo.istock.services.Validations;


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

        Validations validations = new Validations();

        boolean valid = true;

        if (!validations.editValidate(companyEditText)){
            companyEditText.requestFocus();
            companyEditText.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(nameEditText)){
            nameEditText.requestFocus();
            nameEditText.setError(getString(R.string.requiredField));
        }

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

        if (!validations.editValidate(passwordConfirmationEditText)){
            passwordConfirmationEditText.requestFocus();
            passwordConfirmationEditText.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!valid){
            return;
        }

        if (!validations.companyName(companyEditText.getText().toString())){
            companyEditText.requestFocus();
            companyEditText.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(nameEditText.getText().toString())){
            nameEditText.requestFocus();
            nameEditText.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.email(emailEditText.getText().toString())){
            emailEditText.requestFocus();
            emailEditText.setError(getString(R.string.invalid_email));
            valid = false;
        }

        if (!validations.password(passwordEditText.getText().toString())){
            passwordEditText.requestFocus();
            passwordEditText.setError(getString(R.string.invalid_password_weak));
            valid = false;
        }

        if (!validations.password(passwordConfirmationEditText.getText().toString())){
            passwordConfirmationEditText.requestFocus();
            passwordConfirmationEditText.setError(getString(R.string.invalid_password_weak));
            valid = false;
        }

        if (!valid){
            return;
        }

        if (!validations.passwordEquals(
                passwordEditText.getText().toString(),
                passwordConfirmationEditText.getText().toString())){

                passwordEditText.setError(getString(R.string.invalid_password));
                passwordConfirmationEditText.requestFocus();
                passwordConfirmationEditText.setError(getString(R.string.invalid_password));

        }

        if (!valid){
            return;
        }

        UserServices userServices = new UserServices(this);

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString().trim().toUpperCase();
        String password = passwordEditText.getText().toString();
        String type = UserTypes.ADMINISTRATOR.name();
        String status = UserStatus.ACTIVE.name();
        String company = companyEditText.getText().toString().trim();
        long administrator = -1;

        try {
            userServices.registerUser(name, email, password, type, status, company, administrator);
        }
        catch (Exception error){

            String errorMessage;
            if (error.getMessage().equals("Email já cadastrado")){
                errorMessage = (getString(R.string.email_already_used));
            }
            else {
                errorMessage = getString(R.string.unknow_error);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(errorMessage);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

            emailEditText.setText("");
            passwordEditText.setText("");
            passwordConfirmationEditText.setText("");

            emailEditText.requestFocus();

        }



    }
}