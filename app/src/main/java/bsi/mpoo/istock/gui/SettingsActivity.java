package bsi.mpoo.istock.gui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Producer;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.ImageServices;

public class SettingsActivity extends AppCompatActivity {

    private User user;
    private EditText companyNameEditText;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        companyNameEditText = findViewById(R.id.editCompanyNameSettings);
        companyNameEditText =findViewById(R.id.editCompanyNameSettings);
        nameEditText = findViewById(R.id.editFullNameSettings);
        emailEditText = findViewById(R.id.editEmailSettings);
        passwordEditText = findViewById(R.id.editPasswordSettings);
        imageView = findViewById(R.id.pictureSettings);
        if (Session.getInstance().getAccount() instanceof Salesman){
            user = ((Salesman) Session.getInstance().getAccount()).getUser();
            companyNameEditText.setVisibility(View.GONE);
        } else if (Session.getInstance().getAccount() instanceof Producer){
            user = ((Producer) Session.getInstance().getAccount()).getUser();
            companyNameEditText.setVisibility(View.GONE);
        } else {
            user = ((Administrator)Session.getInstance().getAccount()).getUser();
        }
        companyNameEditText.setText(user.getCompany());
        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        ImageServices imageServices = new ImageServices();
        if (user.getImage() != null){
            imageView.setImageBitmap(imageServices.byteToImage(user.getImage()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }
}
