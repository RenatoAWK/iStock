package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.SettingsHelperActivity;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Producer;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.ImageServices;

public class SettingsActivity extends AppCompatActivity {

    private User user;
    private EditText companyNameEditText;
    private TextInputLayout companyTextInputLayout;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView imageView;
    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        companyNameEditText = findViewById(R.id.editCompanyNameSettings);
        companyTextInputLayout =findViewById(R.id.companyInputLayoutSettings);
        nameEditText = findViewById(R.id.editFullNameSettings);
        emailEditText = findViewById(R.id.editEmailSettings);
        passwordEditText = findViewById(R.id.editPasswordSettings);
        imageView = findViewById(R.id.pictureSettings);
        buttonDelete = findViewById(R.id.deleteButtonSettings);
        if (Session.getInstance().getAccount() instanceof Salesman){
            user = ((Salesman) Session.getInstance().getAccount()).getUser();
            companyTextInputLayout.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);

        } else if (Session.getInstance().getAccount() instanceof Producer){
            user = ((Producer) Session.getInstance().getAccount()).getUser();
            companyTextInputLayout.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        } else {
            user = ((Administrator)Session.getInstance().getAccount()).getUser();
        }
        companyNameEditText.setText(user.getCompany());
        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        passwordEditText.setText(R.string.any_text);
        ImageServices imageServices = new ImageServices();
        if (user.getImage() != null){
            imageView.setImageBitmap(imageServices.byteToImage(user.getImage()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }

    public void editCompany(View view) {
        Intent intent = new Intent(this, SettingsHelperActivity.class);
        Bundle bundle = new Bundle();
        int optionSelected = Constants.SettingsHelper.COMPANY;
        bundle.putInt(Constants.BundleKeys.SETTINGS,optionSelected);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editName(View view) {
        Intent intent = new Intent(this, SettingsHelperActivity.class);
        Bundle bundle = new Bundle();
        int optionSelected = Constants.SettingsHelper.NAME;
        bundle.putInt(Constants.BundleKeys.SETTINGS,optionSelected);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editEmail(View view) {
        Intent intent = new Intent(this, SettingsHelperActivity.class);
        Bundle bundle = new Bundle();
        int optionSelected = Constants.SettingsHelper.EMAIL;
        bundle.putInt(Constants.BundleKeys.SETTINGS,optionSelected);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editPassword(View view) {
        Intent intent = new Intent(this, SettingsHelperActivity.class);
        Bundle bundle = new Bundle();
        int optionSelected = Constants.SettingsHelper.PASSWORD;
        bundle.putInt(Constants.BundleKeys.SETTINGS,optionSelected);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
