package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import bsi.mpoo.istock.domain.User;

import bsi.mpoo.istock.R;

public class HomeActivity extends AppCompatActivity {

    private TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        hello = findViewById(R.id.textView);
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");

        hello.setText("Bem Vindo:\n " + user.getName());

    }
}
