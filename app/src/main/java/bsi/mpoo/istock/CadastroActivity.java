package bsi.mpoo.istock;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.net.Uri;

import bsi.mpoo.istock.data.StockContract.UserEntry;


public class CadastroActivity extends AppCompatActivity {

    private EditText mNameEditText;

    private EditText mEmailEditText;

    private EditText mPasswordEditText;

    private EditText mPasswordConfirmationEditText;

    private EditText mCompanyEditText;

    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEmailEditText = (EditText) findViewById(R.id.editEmailCadastro);
        mPasswordEditText = (EditText) findViewById(R.id.editSenhaCadastro);
        mPasswordConfirmationEditText = (EditText) findViewById(R.id.editSenhaConfirmarCadastro);
        mNameEditText = (EditText) findViewById(R.id.editNomeCadastro);
        mCompanyEditText = (EditText) findViewById(R.id.editNomeEmpresaCadastro);
        mRegisterButton = (Button) (Button) findViewById(R.id.buttonCadastrarCadastro);

        mRegisterButton.setOnClickListener(Register);

    }

    View.OnClickListener Register = new View.OnClickListener() {
        public void onClick(View v) {
            cadastrar();
        }
    };

    //teste de design
    public void cadastrar() {

        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String passwordConfirmation = mPasswordConfirmationEditText.getText().toString();
        String company = mCompanyEditText.getText().toString();

        if(password != passwordConfirmation){
            Toast.makeText(this, "Senha não confirmada.",Toast.LENGTH_LONG).show();
        } else {
            try{
                ContentValues values = new ContentValues();
                values.put(UserEntry.TABLE_NAME, email);
                values.put(UserEntry.TABLE_NAME, password);
                values.put(UserEntry.TABLE_NAME, company);

                Uri uri = getContentResolver().insert(UserEntry.CONTENT_URI, values);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } catch(IllegalArgumentException e){
                Toast.makeText(this, "Não é possível inserir usuário.",Toast.LENGTH_LONG).show();
            }
        }
    }
}