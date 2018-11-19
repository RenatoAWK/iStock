package bsi.mpoo.istock;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }
    // Teste de design
    public void criarConta(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void esqueciSenha(View view) {
        Intent intent = new Intent(this, EsqueciActivity.class);
        startActivity(intent);
    }

    public void entrarInicio(View view) {
        Intent intent = new Intent(this, TelaInicialActivity.class);
        finish();
        startActivity(intent);
    }
    //
}
