package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Producer;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.services.ProductListAdapter;
import bsi.mpoo.istock.services.ProductServices;

public class ProductsActivity extends AppCompatActivity {

    private Object account;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.products));
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButtonProduct);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterProductActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = Session.getInstance().getAccount();
        ProductServices productServices = new ProductServices(this);
        ArrayList<Product> productArrayList;

        if (account instanceof Administrator || account instanceof Salesman || account instanceof Producer){
            productArrayList = productServices.getAcitiveProductsAsc(Session.getInstance().getAdministrator());
            RecyclerView recyclerView = findViewById(R.id.recyclerviewProductT);
            adapter = new ProductListAdapter(this, productArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
