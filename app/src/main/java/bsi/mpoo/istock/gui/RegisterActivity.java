package bsi.mpoo.istock.gui;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.net.Uri;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.gui.LoginActivity;


public class RegisterActivity extends AppCompatActivity {

    private EditText mNomeEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordConfirmationEditText;
    private EditText mCompanyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEmailEditText = findViewById(R.id.editEmailCadastro);
        mPasswordEditText = findViewById(R.id.editSenhaCadastro);
        mPasswordConfirmationEditText = findViewById(R.id.editSenhaConfirmarCadastro);
        mNomeEditText = findViewById(R.id.editNomeCadastro);
        mCompanyEditText = findViewById(R.id.editNomeEmpresaCadastro);

    }




    public void register(View view) {

        // Isso deve ter uma validao + não está adicionando o nome ao cadastro
        String name = mNomeEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String passwordConfirmation = mPasswordConfirmationEditText.getText().toString();
        String company = mCompanyEditText.getText().toString();

        if(!password.equals(passwordConfirmation)){
            Toast.makeText(this, "Senha não confirmada.",Toast.LENGTH_LONG).show();
        } else {

            // Tá dando erro mesmo um usuário válido
            ////////////////////////////////////////
            ////////////////////////////////////////
            ////////////////////////////////////////
            ////////////////////////////////////////
            try{
                ContentValues values = new ContentValues();
                values.put(UserEntry.TABLE_NAME, email);
                values.put(UserEntry.TABLE_NAME, password);
                values.put(UserEntry.TABLE_NAME, company);

                Uri uri = getContentResolver().insert(UserEntry.CONTENT_URI, values);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } catch(IllegalArgumentException e){
                Toast.makeText(this, "Não é possível inserir usuário.",Toast.LENGTH_LONG).show();
            }
        }
    }
}