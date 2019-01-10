package bsi.mpoo.istock.gui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Order;

public class RegisterOrderActivity extends AppCompatActivity {

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
