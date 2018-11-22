package bsi.mpoo.istock;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Toast;

import bsi.mpoo.istock.data.StockContract.UserEntry;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditText;

    private EditText mPasswordEditText;

    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEmailEditText = (EditText) findViewById(R.id.editEmail);
        mPasswordEditText = (EditText) findViewById(R.id.editSenha);
        mLoginButton = (Button) findViewById(R.id.buttonEntrar);

        mLoginButton.setOnClickListener(Login);
    }

    View.OnClickListener Login = new View.OnClickListener() {
        public void onClick(View v) {
            login();
        }
    };

    private void login(){

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_USER_EMAIL,
                UserEntry.COLUMN_USER_PASSWORD
        };

        String selection = UserEntry.COLUMN_USER_EMAIL + "=?" +
                " AND " +
                UserEntry.COLUMN_USER_PASSWORD + "=?";

        String[] selectionArgs = new String[] {email, password};

        try{
            Cursor cursor = getContentResolver().query(UserEntry.CONTENT_URI, projection, selection, selectionArgs, "ASC");
            Intent intent = new Intent(this, TelaInicialActivity.class);
            startActivity(intent);
        } catch (IllegalArgumentException e){
            Toast.makeText(MainActivity.this, "Usuário não cadastrado.",Toast.LENGTH_LONG).show();
        }
    }

}
