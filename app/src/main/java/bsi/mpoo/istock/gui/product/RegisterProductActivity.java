package bsi.mpoo.istock.gui.product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Producer;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.gui.AlertDialogGenerator;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.Exceptions.ProductAlreadyRegistered;
import bsi.mpoo.istock.services.ImageServices;
import bsi.mpoo.istock.services.product.ProductServices;
import bsi.mpoo.istock.services.Validations;

public class RegisterProductActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText minimumEditText;
    private Object account;
    private ImageView imageRegister;
    private Bitmap reducedImageProfile;

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
        account = Session.getInstance().getAccount();
        ProductServices productServices = new ProductServices(getApplicationContext());
        Product newProduct = new Product();
        if (account instanceof Administrator || account instanceof Producer){
            newProduct.setAdministrator(Session.getInstance().getAdministrator());
        } else { return; }
        newProduct.setName(nameEditText.getText().toString().trim().toUpperCase());
        newProduct.setPrice(new BigDecimal(priceEditText.getText().toString()));
        newProduct.setQuantity(Long.parseLong(quantityEditText.getText().toString()));
        ImageServices imageServices = new ImageServices();
        newProduct.setImage(imageServices.imageToByte(reducedImageProfile));

        if (minimumEditText.getText().toString().isEmpty()){
            newProduct.setMinimumQuantity(0);
        } else {
            newProduct.setMinimumQuantity(Long.parseLong(minimumEditText.getText().toString()));
        }

        newProduct.setStatus(Constants.Status.ACTIVE);

        try {
            productServices.registerProduct(newProduct, Session.getInstance().getAdministrator());
            String message = getString(R.string.register_done);
            new AlertDialogGenerator(this, message, true).invoke();

        } catch (ProductAlreadyRegistered error){
            String message = getString(R.string.product_already_registered);
            new AlertDialogGenerator(this, message, false).invoke();
            validations.clearFields(nameEditText);

        } catch (Exception error){
            String message = getString(R.string.unknow_error);
            new AlertDialogGenerator(this, message, false).invoke();

        }
    }

    private boolean isAllFieldsValid(Validations validations){
        boolean valid = validations.editValidate(nameEditText, quantityEditText, priceEditText);

        if (!validations.name(nameEditText.getText().toString())){
            validations.setErrorIfNull(nameEditText, getString(R.string.invalid_Name));
            valid = false;
        }

        if (!validations.price(priceEditText.getText().toString())){
            validations.setErrorIfNull(priceEditText, getString(R.string.invalid_value));
            valid = false;
        }

        if (!validations.quantity(quantityEditText.getText().toString())){
            validations.setErrorIfNull(quantityEditText, getString(R.string.invalid_value));
            valid = false;
        }

        if (!validations.minimum(minimumEditText.getText().toString())){
            minimumEditText.requestFocus();
            minimumEditText.setError(getString(R.string.invalid_quantity));
            valid = false;
        }
        return valid;
    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_a_image)),Constants.Image.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.Image.REQUEST_CODE) {

            Uri pickedImage = data.getData();
            imageRegister = findViewById(R.id.editImageRegisterProduct);

            try {
                ImageServices imageServices = new ImageServices();
                InputStream inputStream = getContentResolver().openInputStream(pickedImage);
                ExifInterface exifInterface = new ExifInterface(inputStream);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                Bitmap imageProfile = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                byte[] imageProfileByte = imageServices.imageToByte(imageProfile);
                reducedImageProfile = imageServices.byteToImage(imageServices.reduceBitmap(imageProfileByte));

                if (orientation < Constants.Image.ORIENTATION_OUT_OF_BOUNDS) {
                    reducedImageProfile = imageServices.rotate(reducedImageProfile, orientation);
                }
                setImageOnImageView();

            } catch (IOException error) {
                String message = getString(R.string.unknow_error);
                new AlertDialogGenerator(this, message, false).invoke();

            }
        }
    }

    private void setImageOnImageView() {
        imageRegister.setImageBitmap(reducedImageProfile);
        imageRegister.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
