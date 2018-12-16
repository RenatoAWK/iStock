package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.math.BigDecimal;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.Exceptions.ProductAlreadyRegistered;
import bsi.mpoo.istock.services.ProductServices;
import bsi.mpoo.istock.services.Validations;

public class RegisterProductActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText priceEditText;
    EditText quantityEditText;
    EditText minimumEditText;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        nameEditText = findViewById(R.id.editTextNameRegisterProduct);
        priceEditText = findViewById(R.id.editTextPriceRegisterProduct);
        quantityEditText = findViewById(R.id.editTextQuantityRegisterProduct);
        minimumEditText = findViewById(R.id.editTextMinimumRegisterProduct);
    }

    public void register(View view) {
        Validations validations = new Validations(getApplicationContext());
        if (!isAllFieldsValid(validations)) return;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("user");
        ProductServices productServices = new ProductServices(getApplicationContext());
        Product newProduct = new Product();
        newProduct.setName(nameEditText.getText().toString().trim().toUpperCase());
        newProduct.setPrice(new BigDecimal(priceEditText.getText().toString()));
        newProduct.setQuantity(Long.parseLong(quantityEditText.getText().toString()));

        if (minimumEditText.getText().toString().isEmpty()){
            newProduct.setMinimumQuantity(0);
        } else {
            newProduct.setMinimumQuantity(Long.parseLong(minimumEditText.getText().toString()));
        }

        newProduct.setStatus(Constants.Status.ACTIVE);
        newProduct.setAdministrator(user);
        try {
            productServices.registerProduct(newProduct);
            String message = getString(R.string.register_done);
            new AlertDialogGenerator(this, message, true).invoke();

        } catch (ProductAlreadyRegistered error){
            String message = getString(R.string.product_already_registered);
            new AlertDialogGenerator(this, message, false).invoke();

        } catch (Exception error){
            String message = getString(R.string.unknow_error);
            new AlertDialogGenerator(this, message, false).invoke();

        }
    }

    private boolean isAllFieldsValid(Validations validations){
        boolean valid = validations.editValidate(nameEditText, quantityEditText, priceEditText);

        if (!validations.name(nameEditText.getText().toString())){
            if (nameEditText.getError() == null){
                nameEditText.requestFocus();
                nameEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.price(priceEditText.getText().toString())){
            if (priceEditText.getError() == null){
                priceEditText.requestFocus();
                priceEditText.setError(getString(R.string.invalid_value));
            }
            valid = false;
        }

        if (!validations.quantity(quantityEditText.getText().toString())){
            if (quantityEditText.getError() == null){
                quantityEditText.requestFocus();
                quantityEditText.setError(getString(R.string.invalid_value));
            }
            valid = false;
        }

        if (!validations.minimum(minimumEditText.getText().toString())){
            minimumEditText.requestFocus();
            minimumEditText.setError(getString(R.string.invalid_quantity));
            valid = false;
        }
        return valid;
    }
}
