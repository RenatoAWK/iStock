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
import bsi.mpoo.istock.services.Exceptions.ProductNotRegistered;
import bsi.mpoo.istock.services.ProductServices;
import bsi.mpoo.istock.services.Validations;

public class EditProductActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText priceEditText;
    EditText quantityEditText;
    EditText minimumEditText;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.product = bundle.getParcelable("product");

        nameEditText = findViewById(R.id.editTextNameEditProduct);
        priceEditText = findViewById(R.id.editTextPriceEditProduct);
        quantityEditText = findViewById(R.id.editTextQuantityEditProduct);
        minimumEditText = findViewById(R.id.editTextMinimumEditProduct);

        nameEditText.setText(product.getName());
        priceEditText.setText(String.valueOf(product.getPrice()));
        quantityEditText.setText(String.valueOf(product.getQuantity()));
        if (product.getMinimumQuantity() != 0){
            minimumEditText.setText(String.valueOf(product.getMinimumQuantity()));
        }



    }

    public void edit(View view) {

        Validations validations = new Validations(getApplicationContext());

        if (!isAllFieldsValid(validations)) return;

        ProductServices productServices = new ProductServices(getApplicationContext());

        Product newProduct = new Product();
        newProduct.setName(nameEditText.getText().toString().trim().toUpperCase());
        newProduct.setPrice(new BigDecimal(priceEditText.getText().toString()));
        newProduct.setQuantity(Long.parseLong(quantityEditText.getText().toString()));

        if (minimumEditText.getText().toString().isEmpty()){
            newProduct.setMinimumQuantity(0);
        }
        else {
            newProduct.setMinimumQuantity(Long.parseLong(minimumEditText.getText().toString()));
        }

        newProduct.setStatus(product.getStatus());
        newProduct.setAdministrator(product.getAdministrator());
        newProduct.setId(product.getId());

        try {
            productServices.updateProduct(newProduct);

            String message = getString(R.string.edit_successful);

            new AlertDialogGenerator(this, message, true).invoke();


        } catch (ProductNotRegistered error){

            String message = getString(R.string.product_not_registered);

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