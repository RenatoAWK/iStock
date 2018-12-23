package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Address;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.ClientServices;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.Exceptions.ClientAlreadyRegistered;
import bsi.mpoo.istock.services.MaskGenerator;
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
        MaskGenerator.mask(phoneEditText, Constants.MaskTypes.PHONE);
        registerButton = findViewById(R.id.buttonRegisterRegisterClient);
    }

    public void register(View view) {
        Validations validations = new Validations(getApplicationContext());
        if (!isAllFieldsValid(validations)) return;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable(Constants.BundleKeys.USER);
        ClientServices clientServices = new ClientServices(getApplicationContext());
        Client newClient = new Client();
        newClient.setName(nameEditText.getText().toString().trim().toUpperCase());
        newClient.setPhone(MaskGenerator.unmask(phoneEditText.getText().toString()));
        newClient.setStatus(Constants.Status.ACTIVE);
        newClient.setAdministrator(user);
        Address newAddress = new Address();
        newAddress.setStreet(streetEditText.getText().toString().trim().toUpperCase());
        newAddress.setNumber(Integer.parseInt(numberEditText.getText().toString()));
        newAddress.setDistrict(districtEditText.getText().toString().trim().toUpperCase());
        newAddress.setCity(cityEditText.getText().toString().trim().toUpperCase());
        newAddress.setState(stateEditText.getText().toString().trim().toUpperCase());
        newAddress.setStatus(Constants.Status.ACTIVE);
        newClient.setAddress(newAddress);

        try {
            clientServices.registerClient(newClient);
            String message = getString(R.string.register_done);
            new AlertDialogGenerator(this, message, true ).invoke();

        } catch (ClientAlreadyRegistered error){
            String message = getString(R.string.client_already_registered);
            new AlertDialogGenerator(this, message, false ).invoke();
            validations.clearFields(nameEditText);

        } catch (Exception error){
            String message = getString(R.string.unknow_error);
            new AlertDialogGenerator(this, message, false).invoke();

        }

    }

    private boolean isAllFieldsValid(Validations validations) {
        boolean valid = validations.editValidate(nameEditText, streetEditText,
                numberEditText, districtEditText, cityEditText, stateEditText, phoneEditText
        );

        if (!validations.name(nameEditText.getText().toString())){
            validations.setErrorIfNull(nameEditText,getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(streetEditText.getText().toString())){
            validations.setErrorIfNull(streetEditText, getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(districtEditText.getText().toString())){
            validations.setErrorIfNull(districtEditText, getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(cityEditText.getText().toString())){
            validations.setErrorIfNull(cityEditText, getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.name(stateEditText.getText().toString())){
            validations.setErrorIfNull(stateEditText, getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.phone(phoneEditText.getText().toString())){
            validations.setErrorIfNull(phoneEditText, getString(R.string.invalid_phone));
            valid = false;
        }
        return valid;
    }
}
