package bsi.mpoo.istock.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Address;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.AccountStatus;
import bsi.mpoo.istock.services.ClientServices;
import bsi.mpoo.istock.services.Exceptions.ClientAlreadyRegistered;
import bsi.mpoo.istock.services.Validations;

public class RegisterClientActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText streetEditText;
    EditText numberEditText;
    EditText districtEditText;
    EditText cityEditText;
    EditText stateEditText;
    EditText phoneEditText;
    Button registerButton;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        nameEditText = findViewById(R.id.editTextNameRegisterClient);
        streetEditText = findViewById(R.id.editTextStreetRegisterClient);
        numberEditText = findViewById(R.id.editTextNumberRegisterClient);
        districtEditText = findViewById(R.id.editTextDistrictRegisterClient);
        cityEditText = findViewById(R.id.editTextCityRegisterClient);
        stateEditText = findViewById(R.id.editTextStateRegisterClient);
        phoneEditText = findViewById(R.id.editTextPhoneRegisterClient);
        registerButton = findViewById(R.id.buttonRegisterRegisterClient);

    }

    public void register(View view) {

        Validations validations = new Validations(getApplicationContext());

        if (!isAllFieldsValid(validations)) return;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("user");

        ClientServices clientServices = new ClientServices(getApplicationContext());

        Client client = new Client();
        client.setName(nameEditText.getText().toString().trim().toUpperCase());
        client.setPhone(phoneEditText.getText().toString().trim());
        client.setIdAdm(user.getId());
        client.setStatus(AccountStatus.ACTIVE.getValue());

        Address address = new Address();
        address.setStreet(streetEditText.getText().toString().trim().toUpperCase());
        address.setNumber(Integer.parseInt(numberEditText.getText().toString()));
        address.setDistrict(districtEditText.getText().toString().trim().toUpperCase());
        address.setCity(cityEditText.getText().toString().trim().toUpperCase());
        address.setState(stateEditText.getText().toString().trim().toUpperCase());
        address.setStatus(AccountStatus.ACTIVE.getValue());

        client.setAddress(address);

        try {
            clientServices.registerClient(client, user.getId());

            String message = getString(R.string.register_done);

            new AlertDialogGenerator(this, message, true ).invoke();

        }
        catch (ClientAlreadyRegistered error){

            String message = getString(R.string.client_already_registered);

            new AlertDialogGenerator(this, message, false ).invoke();

            nameEditText.setText("");

        }
        catch (Exception error){

            String message = getString(R.string.unknow_error);

            new AlertDialogGenerator(this, message, false).invoke();
        }

    }

    private boolean isAllFieldsValid(Validations validations) {

        boolean valid = validations.editValdiade(nameEditText, streetEditText,
                numberEditText, districtEditText, cityEditText, stateEditText, phoneEditText
        );

        if (!validations.name(nameEditText.getText().toString())){
            if (nameEditText.getError() == null){
                nameEditText.requestFocus();
                nameEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.name(streetEditText.getText().toString())){
            if (streetEditText.getError() == null) {
                streetEditText.requestFocus();
                streetEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.name(districtEditText.getText().toString())){
            if (districtEditText.getError() == null){
                districtEditText.requestFocus();
                districtEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.name(cityEditText.getText().toString())){
            if (cityEditText.getError() == null) {
                cityEditText.requestFocus();
                cityEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.name(stateEditText.getText().toString())){
            if (stateEditText.getError() == null) {
                stateEditText.requestFocus();
                stateEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.phone(phoneEditText.getText().toString())){
            if (phoneEditText.getError() == null) {
                phoneEditText.requestFocus();
                phoneEditText.setError(getString(R.string.invalid_phone));
            }
            valid = false;
        }

        return valid;
    }
}
