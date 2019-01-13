package bsi.mpoo.istock.gui.client;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.gui.MainActivity;
import bsi.mpoo.istock.gui.user.RegisterUserActivity;
import bsi.mpoo.istock.services.client.ClientListAdapter;
import bsi.mpoo.istock.services.client.ClientServices;

public class ClientsActivity extends AppCompatActivity {

    private Object account;
    RecyclerView recyclerView;
    private ClientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        this.setTitle(getString(R.string.clients));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.users));
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButtonClient);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
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
        ClientServices clientServices = new ClientServices(this);
        ArrayList<Client> clientArrayList;
        if (account instanceof Administrator || account instanceof Salesman) {
            clientArrayList = clientServices.getAcitiveClientsAsc(Session.getInstance().getAdministrator());
            recyclerView = findViewById(R.id.recyclerviewClient);
            adapter = new ClientListAdapter(this, clientArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
