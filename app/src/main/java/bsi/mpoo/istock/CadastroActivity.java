package bsi.mpoo.istock;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }

    //teste de design
    public void cadastrar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //
}