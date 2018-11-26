package bsi.mpoo.istock;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEmailEditText = findViewById(R.id.editEmail);
        mPasswordEditText = findViewById(R.id.editPassword);

    }

    public void login(View view){

        // Isso precisa ir para a parte de validação
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

        // Não tá disparando o erro abaixo
        ///////////////////////////////////////
        ///////////////////////////////////////
        ///////////////////////////////////////

        try{
            Cursor cursor = getContentResolver().query(UserEntry.CONTENT_URI, projection, selection, selectionArgs, "ASC");
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        } catch (IllegalArgumentException e){
            Toast.makeText(LoginActivity.this, "Usuário não cadastrado.",Toast.LENGTH_LONG).show();
        }
    }

    public void CreateAccount(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
}
