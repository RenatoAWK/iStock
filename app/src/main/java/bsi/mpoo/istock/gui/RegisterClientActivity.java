package bsi.mpoo.istock.gui;

import android.content.DialogInterface;
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
import bsi.mpoo.istock.services.ClientServices;
import bsi.mpoo.istock.services.Exceptions.ClientAlreadyRegistered;
import bsi.mpoo.istock.services.Validations;

public class RegisterClientActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextStreet;
    EditText editTextNumber;
    EditText editTextDistrict;
    EditText editTextCity;
    EditText editTextState;
    EditText editTextPhone;
    Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        editTextName = findViewById(R.id.editTextNameRegisterClient);
        editTextStreet = findViewById(R.id.editTextStreetRegisterClient);
        editTextNumber = findViewById(R.id.editTextNumberRegisterClient);
        editTextDistrict = findViewById(R.id.editTextDistrictRegisterClient);
        editTextCity = findViewById(R.id.editTextCityRegisterClient);
        editTextState = findViewById(R.id.editTextStateRegisterClient);
        editTextPhone = findViewById(R.id.editTextPhoneRegisterClient);
        buttonRegister = findViewById(R.id.buttonRegisterRegisterClient);

    }

    public void register(View view) {

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

        if (!valid){
            return;
        }

        ClientServices clientServices = new ClientServices(getApplicationContext());

        Client client = new Client();
        client.setName(editTextName.getText().toString().trim().toUpperCase());
        client.setPhone(editTextPhone.getText().toString().trim());

        Address address = new Address();
        address.setStreet(editTextStreet.getText().toString().trim().toUpperCase());
        address.setNumber(Integer.parseInt(editTextNumber.getText().toString()));
        address.setDistrict(editTextDistrict.getText().toString().trim().toUpperCase());
        address.setCity(editTextCity.getText().toString().trim().toUpperCase());
        address.setState(editTextState.getText().toString().trim().toUpperCase());

        client.setAddress(address);

        try {
            clientServices.registerClient(client);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.register_done);
            builder.setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            builder.show();
        }
        catch (ClientAlreadyRegistered error){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.client_already_registered));
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
