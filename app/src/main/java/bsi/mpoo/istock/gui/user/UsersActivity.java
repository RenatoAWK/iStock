package bsi.mpoo.istock.gui.user;

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
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.gui.MainActivity;
import bsi.mpoo.istock.services.user.UserListAdapter;
import bsi.mpoo.istock.services.user.UserServices;

public class UsersActivity extends AppCompatActivity {

    private Object account;
    private UserListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        this.setTitle(getString(R.string.users));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.users));
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButtonUser);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
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
        UserServices userServices = new UserServices(this);
        ArrayList<User> userArrayList;

        if (account instanceof Administrator){
            userArrayList = userServices.getAcitiveUsersAsc(Session.getInstance().getAdministrator());
            recyclerView = findViewById(R.id.recyclerviewUser);
            adapter = new UserListAdapter(this, userArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
