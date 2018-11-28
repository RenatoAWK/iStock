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

    private EditText NameEditText;
    private EditText EmailEditText;
    private EditText PasswordEditText;
    private EditText PasswordConfirmationEditText;
    private EditText CompanyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");

        EmailEditText = findViewById(R.id.editEmailRegister);
        PasswordEditText = findViewById(R.id.editPasswordRegister);
        PasswordConfirmationEditText = findViewById(R.id.editPasswordConfirmRegister);
        NameEditText = findViewById(R.id.editfullNameRegister);
        CompanyEditText = findViewById(R.id.editCompanyNameRegister);

        EmailEditText.setText(email);

    }

    public void register(View view) {

        Validations validations = new Validations();

        boolean valid = true;

        if (!validations.editValidate(CompanyEditText)){
            CompanyEditText.requestFocus();
            CompanyEditText.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(NameEditText)){
            NameEditText.requestFocus();
            NameEditText.setError(getString(R.string.requiredField));
        }

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

        if (!validations.editValidate(PasswordConfirmationEditText)){
            PasswordConfirmationEditText.requestFocus();
            PasswordConfirmationEditText.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!valid){
            return;
        }

        if (!validations.companyName(CompanyEditText.getText().toString())){
            CompanyEditText.requestFocus();
            CompanyEditText.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(NameEditText.getText().toString())){
            NameEditText.requestFocus();
            NameEditText.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.email(EmailEditText.getText().toString())){
            EmailEditText.requestFocus();
            EmailEditText.setError(getString(R.string.invalid_email));
            valid = false;
        }

        if (!validations.password(PasswordEditText.getText().toString())){
            PasswordEditText.requestFocus();
            PasswordEditText.setError(getString(R.string.invalid_password_weak));
            valid = false;
        }

        if (!validations.password(PasswordConfirmationEditText.getText().toString())){
            PasswordConfirmationEditText.requestFocus();
            PasswordConfirmationEditText.setError(getString(R.string.invalid_password_weak));
            valid = false;
        }

        if (!valid){
            return;
        }

        if (!validations.passwordEquals(
                PasswordEditText.getText().toString(),
                PasswordConfirmationEditText.getText().toString())){

                PasswordEditText.setError(getString(R.string.invalid_password));
                PasswordConfirmationEditText.requestFocus();
                PasswordConfirmationEditText.setError(getString(R.string.invalid_password));

        }

        if (!valid){
            return;
        }

        UserServices userServices = new UserServices(this);

        String name = NameEditText.getText().toString();
        String email = EmailEditText.getText().toString().trim().toUpperCase();
        String password = PasswordEditText.getText().toString();
        String type = UserTypes.ADMINISTRATOR.name();
        String status = UserStatus.ACTIVE.name();
        String company = CompanyEditText.getText().toString().trim();
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

            EmailEditText.setText("");
            PasswordEditText.setText("");
            PasswordConfirmationEditText.setText("");

            EmailEditText.requestFocus();

        }



    }
}