package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.gui.fragments.ClientsFragment;
import bsi.mpoo.istock.gui.fragments.HistoricFragment;
import bsi.mpoo.istock.gui.fragments.HomeFragment;
import bsi.mpoo.istock.gui.fragments.ProductsFragment;
import bsi.mpoo.istock.gui.fragments.SalesFragment;
import bsi.mpoo.istock.gui.fragments.UsersFragment;
import bsi.mpoo.istock.services.ImageServices;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textViewCompany;
        TextView textViewName;
        ImageView companyImageView;
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
                if (fragment instanceof HomeFragment){

                } else if (fragment instanceof SalesFragment){

                } else if (fragment instanceof ProductsFragment){
                    Intent intent = new Intent(getApplicationContext(), RegisterProductActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);

                } else if (fragment instanceof ClientsFragment){
                    Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);

                } else if (fragment instanceof  UsersFragment){

                } else if (fragment instanceof HistoricFragment){

                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        textViewName = headerView.findViewById(R.id.textViewNameUserHeaderHome);
        textViewCompany = headerView.findViewById(R.id.textViewCompanyHeaderHome);
        companyImageView = headerView.findViewById(R.id.imageViewHeader);
        textViewName.setText(user.getName());
        textViewCompany.setText(user.getCompany());

        if (user.getImage() != null){
            ImageServices imageServices = new ImageServices();
            setImageOnImageView(companyImageView, imageServices);

        }
        displaySelectedScreen(R.id.nav_home);
    }

    private void setImageOnImageView(ImageView companyImageView, ImageServices imageServices) {
        companyImageView.setImageBitmap(imageServices.byteToImage(user.getImage()));
        companyImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    public void displaySelectedScreen(int id){
        Fragment fragment = null;
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",user);

        switch (id){
            case R.id.nav_home:
                fragment = new HomeFragment();
                fragment.setArguments(bundle);
                floatingActionButton.hide();
                break;
            case R.id.nav_sales:
                fragment = new SalesFragment();
                fragment.setArguments(bundle);
                floatingActionButton.show();
                break;
            case R.id.nav_products:
                fragment = new ProductsFragment();
                fragment.setArguments(bundle);
                floatingActionButton.show();
                break;
            case R.id.nav_clients:
                fragment = new ClientsFragment();
                fragment.setArguments(bundle);
                floatingActionButton.show();
                break;
            case R.id.nav_users:
                fragment = new UsersFragment();
                fragment.setArguments(bundle);
                floatingActionButton.show();
                break;
            case R.id.nav_historic:
                fragment = new HistoricFragment();
                fragment.setArguments(bundle);
                floatingActionButton.show();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                finish();
                startActivity(intent);
        }

        if (fragment != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
