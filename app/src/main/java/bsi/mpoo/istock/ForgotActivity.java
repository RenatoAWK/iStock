package bsi.mpoo.istock;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    //Teste design
    public void confirmForgot(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }
    //
}
