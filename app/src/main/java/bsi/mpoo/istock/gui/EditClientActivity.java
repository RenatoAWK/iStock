package bsi.mpoo.istock.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Address;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.services.AccountStatus;
import bsi.mpoo.istock.services.ClientServices;
import bsi.mpoo.istock.services.Exceptions.ClientNotRegistered;
import bsi.mpoo.istock.services.Validations;

public class EditClientActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText streetEditText;
    EditText numberEditText;
    EditText districtEditText;
    EditText cityEditText;
    EditText stateEditText;
    EditText phoneEditText;
    Button registerButton;

    Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        nameEditText = findViewById(R.id.editTextNameEditClient);
        streetEditText = findViewById(R.id.editTextStreetEditClient);
        numberEditText = findViewById(R.id.editTextNumberEditClient);
        districtEditText = findViewById(R.id.editTextDistrictEditClient);
        cityEditText = findViewById(R.id.editTextCityEditClient);
        stateEditText = findViewById(R.id.editTextStateEditClient);
        phoneEditText = findViewById(R.id.editTextPhoneEditClient);
        registerButton = findViewById(R.id.buttonRegisterEditClient);

        nameEditText.setText(client.getName());
        streetEditText.setText(client.getAddress().getStreet());
        numberEditText.setText(client.getAddress().getNumber());
        districtEditText.setText(client.getAddress().getDistrict());
        cityEditText.setText(client.getAddress().getCity());
        stateEditText.setText(client.getAddress().getState());
        phoneEditText.setText(client.getPhone());

    }

    public void edit(View view) {

        Validations validations = new Validations(getApplicationContext());

        if (!isAllFieldsValid(validations)) return;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        client = bundle.getParcelable("client");

        ClientServices clientServices = new ClientServices(getApplicationContext());

        Client client = new Client();
        client.setName(nameEditText.getText().toString().trim().toUpperCase());
        client.setPhone(phoneEditText.getText().toString().trim());
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

            clientServices.updateClient(client, client.getIdAdm());

            String message = getString(R.string.edit_successful);

            new AlertDialogGenerator(this, message, true).invoke();

        } catch (ClientNotRegistered error){

            String message = getString(R.string.client_not_registered);

            new AlertDialogGenerator(this, message, false).invoke();

        } catch (Exception error){

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
