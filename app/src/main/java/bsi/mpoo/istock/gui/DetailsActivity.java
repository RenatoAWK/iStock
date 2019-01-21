package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.gui.user.EditUserActivity;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.ImageServices;
import bsi.mpoo.istock.services.user.UserServices;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView imageViewEdit;
    ImageView imageViewDelete;
    TextView nameTextView;
    TextView title1;
    TextView title2;
    TextView title3;
    TextView title4;
    TextView title5;
    TextView title6;
    TextView subTitle1;
    TextView subTitle2;
    TextView subTitle3;
    TextView subTitle4;
    TextView subTitle5;
    TextView subTitle6;
    RecyclerView recyclerView;
    ImageServices imageServices;
    Object object;
    UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setUpMenuToolbar();
        setUpViews();
        imageServices = new ImageServices();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        object = bundle.get(Constants.BundleKeys.OBJECT);
        userServices = new UserServices(this);
        setUpData();
    }

    private void setUpViews() {
        imageView = findViewById(R.id.detailsImage);
        imageViewEdit = findViewById(R.id.detailsEditImage);
        imageViewDelete = findViewById(R.id.detailsDeleteImage);
        nameTextView = findViewById(R.id.nameDetails);
        title1 = findViewById(R.id.titleDetails1);
        title2 = findViewById(R.id.titleDetails2);
        title3 = findViewById(R.id.titleDetails3);
        title4 = findViewById(R.id.titleDetails4);
        title5 = findViewById(R.id.titleDetails5);
        title6 = findViewById(R.id.titleDetails6);
        subTitle1 = findViewById(R.id.subTitleDetails1);
        subTitle2 = findViewById(R.id.subTitleDetails2);
        subTitle3 = findViewById(R.id.subTitleDetails3);
        subTitle4 = findViewById(R.id.subTitleDetails4);
        subTitle5 = findViewById(R.id.subTitleDetails5);
        subTitle6 = findViewById(R.id.subTitleDetails6);
        recyclerView = findViewById(R.id.recyclerviewDetails);
    }

    private void setUpOrder() {

    }

    private void setUpProduct() {

    }

    private void setUpClient() {

    }

    private void showUsedViews(View...views) {
        for (View view:views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setUpUser(final User user) {
        imageViewEdit.setImageResource(R.drawable.user_edit);
        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
                intent.putExtra(Constants.BundleKeys.USER, user);
                getApplicationContext().startActivity(intent);
            }
        });
        if (user.getImage() != null){
            imageView.setImageBitmap(imageServices.byteToImage(user.getImage()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView.setImageResource(R.drawable.user);
        }
        nameTextView.setText(user.getName());
        title1.setText(getString(R.string.function));
        String currentFunction;
        if (user.getType() == Constants.UserTypes.SALESMAN){
            currentFunction = getString(R.string.sales);
        } else {
            currentFunction = getString(R.string.production);
        }
        subTitle1.setText(currentFunction);
        title2.setText(getString(R.string.email));
        subTitle2.setText(user.getEmail());
        title3.setText(getString(R.string.status_account));
        String currentStatus;
        if (user.getStatus() == Constants.Status.ACTIVE){
            currentStatus = getString(R.string.active);
        } else if (user.getStatus() == Constants.Status.INACTIVE){
            currentStatus = getString(R.string.inactive);
        } else {
            currentStatus = getString(R.string.first_access);
        }
        subTitle3.setText(currentStatus);
        showUsedViews(imageView,imageViewEdit, nameTextView, title1, subTitle1, title2, subTitle2, title3, subTitle3);
    }

    private void setUpMenuToolbar() {
        View view = getLayoutInflater().inflate(R.layout.menu_actionbar_search, null);
        getSupportActionBar().setCustomView(view);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        TextView textView = findViewById(R.id.title_toolbar);
        ImageView imageViewBack = findViewById(R.id.back_toolbar);
        ImageView searchView = findViewById(R.id.search_toolbar);
        searchView.setVisibility(View.GONE);

        textView.setText(R.string.details);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void setUpData(){
        if (object instanceof User){
            setUpUser((User) object);
        } else if (object instanceof Client){
            setUpClient();
        } else if (object instanceof Product){
            setUpProduct();
        } else if (object instanceof Order){
            setUpOrder();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (object instanceof User){
            object = userServices.getUserById(((User) object).getId());
            setUpData();
        } else if (object instanceof Client){
            setUpClient();
        } else if (object instanceof Product){
            setUpProduct();
        } else if (object instanceof Order){
            setUpOrder();
        }

    }
}
