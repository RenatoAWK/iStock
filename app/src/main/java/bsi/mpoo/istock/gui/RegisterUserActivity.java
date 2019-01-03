package bsi.mpoo.istock.gui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.Exceptions;
import bsi.mpoo.istock.services.RandomPassword;
import bsi.mpoo.istock.services.UserServices;
import bsi.mpoo.istock.services.Validations;

public class RegisterUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText nameEditText;
    private EditText emailEditText;
    private int selectedOption = Constants.UserTypes.ADMINISTRATOR;
    private User user;
    private String tempPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        user = Session.getInstance().getUser();
        spinner = findViewById(R.id.spinnerRegisterUser);
        spinner.setOnItemSelectedListener(this);
        ArrayList<String> functions = new ArrayList<>();
        functions.add(getString(R.string.administration));
        functions.add(getString(R.string.sales));
        functions.add(getString(R.string.production));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, functions);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        nameEditText = findViewById(R.id.editNameRegisterUser);
        emailEditText = findViewById(R.id.editEmailRegisterUser);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String option = parent.getItemAtPosition(position).toString();
        if (option.equals(getString(R.string.administration).intern())){
            selectedOption = Constants.UserTypes.ADMINISTRATOR;
        } else if (option.equals(getString(R.string.production).intern())){
            selectedOption = Constants.UserTypes.PRODUCER;
        } else {
            selectedOption = Constants.UserTypes.SALESMAN;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0){}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    public void register(View view) {
        Validations validations = new Validations(getApplicationContext());
        if (!isAllFieldsValid(validations)){
            return;
        }
        UserServices userServices = new UserServices(getApplicationContext());
        User newUser = new User();
        newUser.setName(nameEditText.getText().toString());
        newUser.setCompany(user.getCompany());
        newUser.setEmail(emailIsEmpty(emailEditText.getText().toString(), newUser.getName(), newUser.getCompany()));
        newUser.setAdministrator(user.getId());
        newUser.setStatus(Constants.Status.FIRST_ACCESS_FOR_USER);
        tempPassword = RandomPassword.generate();
        newUser.setPassword(tempPassword);
        newUser.setType(selectedOption);
        try {
            userServices.registerUser(newUser);
            String message = registeredMessage(newUser.getEmail(), tempPassword);
            new AlertDialogGenerator(this, getString(R.string.register_done), true).invokeShare(message);
        } catch (Exceptions.EmailAlreadyRegistered error){
            String message = getString(R.string.email_already_registered);
            new AlertDialogGenerator(this, message, false).invoke();
            validations.clearFields(emailEditText, nameEditText);
            nameEditText.requestFocus();
        } catch (Exception error){
            String message = getString(R.string.unknow_error);
            new AlertDialogGenerator(this, message, false).invoke();
            validations.clearFields(emailEditText, nameEditText);
            nameEditText.requestFocus();
        }
    }

    private boolean isAllFieldsValid(Validations validations){
        boolean valid = validations.editValidate(nameEditText);

        if (!validations.name(nameEditText.getText().toString())){
            validations.setErrorIfNull(nameEditText, getString(R.string.invalid_Name));
            valid = false;
        }

        if (!emailEditText.getText().toString().isEmpty()){
            if (!validations.email(emailEditText.getText().toString())){
                validations.setErrorIfNull(emailEditText,getString(R.string.invalid_email));
                valid = false;
            }
        }
        return valid;
    }

    private String emailIsEmpty(String email ,String name, String company){
        if (email.isEmpty()){
            return name+"."+company+getString(R.string.domain_email_istock);
        } else {
            return email;
        }
    }

    private String registeredMessage(String email, String tempPassword){
        return getString(R.string.email)+": "+email+"\n"+
                getString(R.string.password)+": "+tempPassword;
    }
}