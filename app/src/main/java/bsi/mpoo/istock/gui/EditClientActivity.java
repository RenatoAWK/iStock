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
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.AccountStatus;
import bsi.mpoo.istock.services.ClientServices;
import bsi.mpoo.istock.services.Exceptions.ClientNotRegistered;
import bsi.mpoo.istock.services.Exceptions.ClientAlreadyRegistered;
import bsi.mpoo.istock.services.Validations;

public class EditClientActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextStreet;
    EditText editTextNumber;
    EditText editTextDistrict;
    EditText editTextCity;
    EditText editTextState;
    EditText editTextPhone;
    Button buttonRegister;

    Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        editTextName = findViewById(R.id.editTextNameEditClient);
        editTextStreet = findViewById(R.id.editTextStreetEditClient);
        editTextNumber = findViewById(R.id.editTextNumberEditClient);
        editTextDistrict = findViewById(R.id.editTextDistrictEditClient);
        editTextCity = findViewById(R.id.editTextCityEditClient);
        editTextState = findViewById(R.id.editTextStateEditClient);
        editTextPhone = findViewById(R.id.editTextPhoneEditClient);
        buttonRegister = findViewById(R.id.buttonRegisterEditClient);

        editTextName.setText(client.getName());
        editTextStreet.setText(client.getAddress().getStreet());
        editTextNumber.setText(client.getAddress().getNumber());
        editTextDistrict.setText(client.getAddress().getDistrict());
        editTextCity.setText(client.getAddress().getCity());
        editTextState.setText(client.getAddress().getState());
        editTextPhone.setText(client.getPhone());

    }

    public void edit(View view) {

        Validations validations = new Validations();

        boolean valid = true;

        if (!validations.editValidate(editTextName)){
            editTextName.requestFocus();
            editTextName.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(editTextStreet)){
            editTextStreet.requestFocus();
            editTextStreet.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(editTextNumber)){
            editTextNumber.requestFocus();
            editTextNumber.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(editTextDistrict)){
            editTextDistrict.requestFocus();
            editTextDistrict.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(editTextCity)){
            editTextCity.requestFocus();
            editTextCity.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(editTextState)){
            editTextState.requestFocus();
            editTextState.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.editValidate(editTextPhone)){
            editTextPhone.requestFocus();
            editTextPhone.setError(getString(R.string.requiredField));
            valid = false;
        }

        if (!validations.name(editTextName.getText().toString())){
            editTextName.requestFocus();
            editTextName.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(editTextStreet.getText().toString())){
            editTextStreet.requestFocus();
            editTextStreet.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(editTextDistrict.getText().toString())){
            editTextDistrict.requestFocus();
            editTextDistrict.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(editTextCity.getText().toString())){
            editTextCity.requestFocus();
            editTextCity.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(editTextState.getText().toString())){
            editTextState.requestFocus();
            editTextState.setError(getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.phone(editTextPhone.getText().toString())){
            editTextPhone.requestFocus();
            editTextPhone.setError(getString(R.string.invalid_phone));
            valid = false;
        }

        if (!valid){
            return;
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        client = bundle.getParcelable("client");

        ClientServices clientServices = new ClientServices(getApplicationContext());

        Client client = new Client();
        client.setName(editTextName.getText().toString().trim().toUpperCase());
        client.setPhone(editTextPhone.getText().toString().trim());
        client.setStatus(AccountStatus.ACTIVE.getValue());

        Address address = new Address();
        address.setStreet(editTextStreet.getText().toString().trim().toUpperCase());
        address.setNumber(Integer.parseInt(editTextNumber.getText().toString()));
        address.setDistrict(editTextDistrict.getText().toString().trim().toUpperCase());
        address.setCity(editTextCity.getText().toString().trim().toUpperCase());
        address.setState(editTextState.getText().toString().trim().toUpperCase());
        address.setStatus(AccountStatus.ACTIVE.getValue());

        client.setAddress(address);

        try {
            clientServices.updateClient(client, client.getIdAdm());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.edit_successful);
            builder.setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            builder.show();
        }
        catch (ClientNotRegistered error){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.client_not_registered);
            builder.setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();


        }
        catch (Exception error){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.unknow_error));
            builder.setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }




    }
}
