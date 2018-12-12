package bsi.mpoo.istock.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.ImageServices;
import bsi.mpoo.istock.services.UserServices;
import bsi.mpoo.istock.services.AccountStatus;
import bsi.mpoo.istock.services.UserTypes;
import bsi.mpoo.istock.services.Validations;
import bsi.mpoo.istock.services.Exceptions.EmailAlreadyRegistered;


public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmationEditText;
    private EditText companyEditText;
    private ImageView imageRegister;
    private Bitmap reducedImageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");

        emailEditText = findViewById(R.id.editEmailRegister);
        passwordEditText = findViewById(R.id.editPasswordRegister);
        passwordConfirmationEditText = findViewById(R.id.editPasswordConfirmRegister);
        nameEditText = findViewById(R.id.editfullNameRegister);
        companyEditText = findViewById(R.id.editCompanyNameRegister);
        imageRegister = findViewById(R.id.editImageRegister);

        emailEditText.setText(email);

    }

    public void register(View view) {

        Validations validations = new Validations(getApplicationContext());

        if (!isAllFieldsValid(validations)){
            return;
        }

        UserServices userServices = new UserServices(this);

        User newUser = new User();
        newUser.setName(nameEditText.getText().toString());
        newUser.setEmail(emailEditText.getText().toString().trim().toUpperCase());
        newUser.setPassword(passwordEditText.getText().toString());
        newUser.setType(UserTypes.ADMINISTRATOR.getValue());
        newUser.setStatus(AccountStatus.ACTIVE.getValue());
        newUser.setCompany(companyEditText.getText().toString().trim());
        newUser.setAdministrator(-1);

        ImageServices imageServices = new ImageServices();

        newUser.setImage(imageServices.imageToByte(reducedImageProfile));

        try {

            userServices.registerUser(newUser);
            String message = getString(R.string.register_done);

            new AlertDialogGenerator(this, message, true).invoke();

        } catch (EmailAlreadyRegistered error){

            String message = getString(R.string.email_already_registered);

            new AlertDialogGenerator(this, message, false).invoke();

            emailEditText.setText("");
            passwordEditText.setText("");
            passwordConfirmationEditText.setText("");

            emailEditText.requestFocus();

        } catch (Exception error){

            String message = getString(R.string.unknow_error);

            new AlertDialogGenerator(this, message, false);

            emailEditText.setText("");
            passwordEditText.setText("");
            passwordConfirmationEditText.setText("");

            emailEditText.requestFocus();

        }
    }

    private boolean isAllFieldsValid(Validations validations) {

        boolean valid = validations.editValidate(nameEditText, companyEditText,
                emailEditText, passwordEditText, passwordConfirmationEditText);


        if (!validations.name(nameEditText.getText().toString())){
            if (nameEditText.getError() == null){
                nameEditText.requestFocus();
                nameEditText.setError(getString(R.string.invalid_Name));
            }
            valid = false;
        }

        if (!validations.email(emailEditText.getText().toString())){
            if (emailEditText.getError() == null){
                emailEditText.requestFocus();
                emailEditText.setError(getString(R.string.invalid_email));
            }
            valid = false;
        }

        if (!validations.password(passwordEditText.getText().toString())){
            if (passwordEditText.getError() == null){
                passwordEditText.requestFocus();
                passwordEditText.setError(getString(R.string.invalid_password_weak));
            }
            valid = false;
        }

        if (!validations.password(passwordConfirmationEditText.getText().toString())){
            if (passwordConfirmationEditText.getError() == null){
                passwordConfirmationEditText.requestFocus();
                passwordConfirmationEditText.setError(getString(R.string.invalid_password_weak));
            }
            valid = false;
        }

        if (!validations.passwordEquals(
                passwordEditText.getText().toString(),
                passwordConfirmationEditText.getText().toString())){

                if (passwordEditText.getError() == null){
                    passwordEditText.requestFocus();
                    passwordEditText.setError(getString(R.string.invalid_password_not_equals));
                }
                if (passwordConfirmationEditText.getError() == null){
                    passwordConfirmationEditText.requestFocus();
                    passwordConfirmationEditText.setError(getString(R.string.invalid_password_not_equals));
                }
                valid = false;
        }
        return valid;
    }

    public void pickImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK){

            if (requestCode == 1){

                Uri pickedImage = data.getData();

                imageRegister = findViewById(R.id.editImageRegister);

                try {

                    ImageServices imageServices = new ImageServices();

                    Bitmap imageProfile = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                    byte[] imageProfileByte = imageServices.imageToByte(imageProfile);
                    reducedImageProfile = imageServices.byteToImage(imageServices.reduceBitmap(imageProfileByte));


                    imageRegister.setImageBitmap(reducedImageProfile);
                    imageRegister.setScaleType(ImageView.ScaleType.CENTER_CROP);

                }catch (IOException error){

                    String message = getString(R.string.unknow_error);
                    new AlertDialogGenerator(this,message,false).invoke();

                }
            }
        }
    }
}