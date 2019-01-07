package bsi.mpoo.istock;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Producer;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.UserServices;

public class SettingsHelperActivity extends AppCompatActivity {

    private TextView textView;
    private EditText companyEditTextShow;
    private EditText companyEditText;
    private EditText nameEditTextShow;
    private EditText nameEditText;
    private EditText emailEditTextShow;
    private EditText emailEditText;
    private EditText passwordEditTextOld;
    private EditText passwordEditText;
    private EditText passwordEditTextConfirmation;
    private TextInputLayout companyTextInputLayoutShow;
    private TextInputLayout companyTextInputLayout;
    private TextInputLayout nameTextInputLayoutShow;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout emailTextInputLayoutShow;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayoutOld;
    private TextInputLayout passwordTextInputLayout;
    private TextInputLayout passwordTextInputLayoutConfirmation;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_helper);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int editOption = bundle.getInt(Constants.BundleKeys.SETTINGS);
        textView = findViewById(R.id.textViewSettingsSettingsHelper);
        companyEditTextShow = findViewById(R.id.editCompanyNameSettingsHelperShow);
        companyEditText = findViewById(R.id.editCompanyNameSettingsHelper);
        nameEditTextShow = findViewById(R.id.editFullNameSettingsHelperShow);
        nameEditText = findViewById(R.id.editFullNameSettingsHelper);
        emailEditTextShow = findViewById(R.id.editEmailSettingsHelperShow);
        emailEditText = findViewById(R.id.editEmailSettingsHelper);
        passwordEditTextOld = findViewById(R.id.editPasswordSettingsHelperOld);
        passwordEditText = findViewById(R.id.editPasswordSettingsHelper);
        passwordEditTextConfirmation = findViewById(R.id.editPasswordSettingsHelperConfirmation);
        companyTextInputLayoutShow = findViewById(R.id.companyInputLayoutSettingsHelperShow);
        companyTextInputLayout = findViewById(R.id.companyInputLayoutSettingsHelper);
        nameTextInputLayoutShow = findViewById(R.id.nameInputLayoutSettingsHelperShow);
        nameTextInputLayout = findViewById(R.id.nameInputLayoutSettingsHelper);
        emailTextInputLayoutShow = findViewById(R.id.emailInputLayoutSettingsHelperShow);
        emailTextInputLayout = findViewById(R.id.emailInputLayoutSettingsHelper);
        passwordTextInputLayoutOld = findViewById(R.id.passwordInputLayoutSettingsHelperOld);
        passwordTextInputLayout = findViewById(R.id.passwordInputLayoutSettingsHelper);
        passwordTextInputLayoutConfirmation = findViewById(R.id.passwordInputLayoutSettingsHelperConfirmation);



        UserServices userServices = new UserServices(getApplicationContext());
        user = userServices.getUserFromDimainType(Session.getInstance().getAccount());
        switch (editOption){
            case Constants.SettingsHelper.COMPANY:
                hideInputLayout(nameTextInputLayoutShow, nameTextInputLayout,
                        emailTextInputLayoutShow, emailTextInputLayout, passwordTextInputLayoutOld,
                        passwordTextInputLayout, passwordTextInputLayoutConfirmation);
                textView.setText(getString(R.string.edition_of_company_name));
                break;
            case Constants.SettingsHelper.EMAIL:
                hideInputLayout(companyTextInputLayoutShow, companyTextInputLayout, nameTextInputLayoutShow,
                        nameTextInputLayout, passwordTextInputLayoutOld, passwordTextInputLayout,
                        passwordTextInputLayoutConfirmation);
                textView.setText(getString(R.string.edition_of_email));
                break;

            case Constants.SettingsHelper.NAME:
                hideInputLayout(companyTextInputLayoutShow, companyTextInputLayout, passwordTextInputLayoutOld,
                        emailTextInputLayoutShow, emailTextInputLayout, passwordTextInputLayout,
                        passwordTextInputLayoutConfirmation);
                textView.setText(getString(R.string.edition_of_name));
                break;
            case Constants.SettingsHelper.PASSWORD:
                hideInputLayout(companyTextInputLayoutShow, companyTextInputLayout, nameTextInputLayoutShow,
                        nameTextInputLayout, emailTextInputLayoutShow, emailTextInputLayout);
                textView.setText(getString(R.string.edition_of_password));
                break;
        }

    }

    private void hideInputLayout(TextInputLayout...textInputLayouts){
        for (TextInputLayout textInputLayout: textInputLayouts){
            textInputLayout.setVisibility(View.GONE);
        }
    }

}
