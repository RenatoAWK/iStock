package bsi.mpoo.istock.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Temp;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.gui.client.EditClientActivity;
import bsi.mpoo.istock.gui.product.EditProductActivity;
import bsi.mpoo.istock.gui.user.EditUserActivity;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.DateServices;
import bsi.mpoo.istock.services.ImageServices;
import bsi.mpoo.istock.services.MaskGenerator;
import bsi.mpoo.istock.services.product.ItemOrderListAdapter;
import bsi.mpoo.istock.services.user.UserServices;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    protected ImageView imageViewEdit;
    private ImageView imageViewDelete;
    private TextView nameTextView;
    private TextView title1;
    private TextView title2;
    private TextView title3;
    private TextView title4;
    private TextView title5;
    private TextView title6;
    private TextView subTitle1;
    private TextView subTitle2;
    private TextView subTitle3;
    private TextView subTitle4;
    private TextView subTitle5;
    private TextView subTitle6;
    private RecyclerView recyclerView;
    private ImageServices imageServices;
    private Object object;
    private UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setUpMenuToolbar();
        setUpViews();
        imageServices = new ImageServices();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            object = bundle.get(Constants.BundleKeys.OBJECT);
        } catch (Exception e){
            object = new Order();
        }

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

    private void setUpOrder(final Order order) {

        byte[] b = null;
        if (order.getDelivered() == Constants.Order.NOT_DELIVERED){
            setImageIfNotNull(b, R.drawable.calendar_warning);
            imageViewEdit.setImageResource(R.drawable.calendar_edit);
            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialogGenerator( DetailsActivity.this, getString(R.string.are_you_sure_for_finish_order),true).invokeDeleteChoose(order);

                }
            });
            showUsedViews(imageViewEdit);
        } else {
            setImageIfNotNull(b, R.drawable.cart);
        }
        nameTextView.setText(order.getClient().getName());
        if (order.getDateDelivery() != null){
            title1.setText(getString(R.string.delivery_date));
            subTitle1.setText(DateServices.localDateToFormatedToString(order.getDateDelivery()));
            showUsedViews(title1, subTitle1);
        }
        title2.setText(getString(R.string.realized));
        subTitle2.setText(DateServices.localDateToFormatedToString(order.getDateCreation()));
        title3.setText(getString(R.string.total));
        subTitle3.setText(NumberFormat.getCurrencyInstance().format(order.getTotal()));
        ItemOrderListAdapter adapter = new ItemOrderListAdapter(getApplicationContext(), order.getItems());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        showUsedViews(nameTextView, title2, subTitle2, title3, subTitle3, recyclerView);
    }


    private void setUpProduct(final Product product) {
        imageViewEdit.setImageResource(R.drawable.product_edit);
        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProductActivity.class);
                intent.putExtra(Constants.BundleKeys.PRODUCT, product);
                getApplicationContext().startActivity(intent);
            }
        });
        imageViewDelete.setImageResource(R.drawable.product_remove);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialogGenerator( DetailsActivity.this, getString(R.string.are_you_sure_to_delete_this_item),true).invokeDeleteChoose(object);
            }
        });
        nameTextView.setText(product.getName());
        title1.setText(getString(R.string.price));
        subTitle1.setText(NumberFormat.getCurrencyInstance().format(product.getPrice()));
        title2.setText(getString(R.string.at_stock));
        subTitle2.setText(String.valueOf(product.getQuantity()));
        title3.setText(getString(R.string.minimum_quantity));
        if (product.getMinimumQuantity() == 0){
            subTitle3.setText(getString(R.string.without_info));
        } else {
            subTitle3.setText(String.valueOf(product.getMinimumQuantity()));
        }
        setImageIfNotNull(product.getImage(), R.drawable.product);
        showUsedViews(nameTextView, imageViewEdit, imageViewDelete, title1, subTitle1, title2, subTitle2, title3, subTitle3);

    }

    private void setUpClient(final Client client) {
        imageViewEdit.setImageResource(R.drawable.user_edit);
        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditClientActivity.class);
                intent.putExtra(Constants.BundleKeys.CLIENT, client);
                getApplicationContext().startActivity(intent);
            }
        });
        imageViewDelete.setImageResource(R.drawable.user_remove);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialogGenerator( DetailsActivity.this, getString(R.string.are_you_sure_to_delete_this_item),true).invokeDeleteChoose(object);
            }
        });
        byte[] b = null;
        setImageIfNotNull(b, R.drawable.user);
        nameTextView.setText(client.getName());
        title1.setText(getString(R.string.street));
        subTitle1.setText(client.getAddress().getStreet());
        title2.setText(getString(R.string.number));
        subTitle2.setText(String.valueOf(client.getAddress().getNumber()));
        title3.setText(getString(R.string.district));
        subTitle3.setText(client.getAddress().getDistrict());
        title4.setText(getString(R.string.city));
        subTitle4.setText(client.getAddress().getCity());
        title5.setText(getString(R.string.state));
        subTitle5.setText(client.getAddress().getState());
        title6.setText(getString(R.string.phone));
        subTitle6.setText(MaskGenerator.unmaskedTextToStringMasked(client.getPhone(), Constants.MaskTypes.PHONE));
        showUsedViews(imageViewEdit, imageViewDelete, nameTextView, title1, subTitle1,
                title2, subTitle2, title3, subTitle3, title4, subTitle4, title5, subTitle5,
                title6, subTitle6);

    }

    private void showUsedViews(View...views) {
        for (View view:views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setImageIfNotNull(byte[] b, int imageId){
        if (b != null){
            imageView.setImageBitmap(imageServices.byteToImage(b));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView.setImageResource(imageId);
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
        setImageIfNotNull(user.getImage(), R.drawable.user);
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
            setUpClient((Client) object);
        } else if (object instanceof Product){
            setUpProduct((Product) object);
        } else if (object instanceof Order){
            setUpOrder(Temp.getOrder());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (object instanceof User){
            object = userServices.getUserById(((User) object).getId());
            setUpData();
        } else if (object instanceof Client){
            setUpClient((Client) object);
        } else if (object instanceof Product){
            setUpProduct((Product) object);
        } else if (object instanceof Order){
            setUpOrder(Temp.getOrder());
        }

    }
}
