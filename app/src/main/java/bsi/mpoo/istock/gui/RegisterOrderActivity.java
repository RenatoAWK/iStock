package bsi.mpoo.istock.gui;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Producer;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.client.ClientServices;
import bsi.mpoo.istock.services.product.ProductCartListAdapter;
import bsi.mpoo.istock.services.product.ProductListAdapter;
import bsi.mpoo.istock.services.product.ProductServices;

public class RegisterOrderActivity extends AppCompatActivity {

    private AutoCompleteTextView nameTextView;
    private EditText dateTextView;
    private TextInputLayout textInputLayout;
    private boolean switchState;
    private Switch switchButton;
    private Calendar myCalendar = Calendar.getInstance();
    private Object account;
    private ProductCartListAdapter adapter;
    private ClientServices clientServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        dateTextView = findViewById(R.id.dateCart);
        textInputLayout = findViewById(R.id.dateCartTextInputLayout);
        switchButton = findViewById(R.id.switchButtonCart);
        nameTextView = findViewById(R.id.editNameCart);
        switchState = switchButton.isChecked();
        setUpDatePicker();
        setUpSwtich();
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = Session.getInstance().getAccount();
        if (account instanceof Administrator || account instanceof Salesman || account instanceof Producer){
            clientServices = new ClientServices(this);
            ArrayList<String> clientsName = clientServices.getActiveClientsName(Session.getInstance().getAdministrator());
            ArrayAdapter<String> adapterComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientsName);
            nameTextView.setAdapter(adapterComplete);
            RecyclerView recyclerView;
            recyclerView = findViewById(R.id.recyclerviewCart);
            adapter = new ProductCartListAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void setUpDatePicker(){


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterOrderActivity.this,date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = Constants.Date.FORMAT_DATE;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale(Locale.getDefault().toString()));
        dateTextView.setText(sdf.format(myCalendar.getTime()));
    }

    private void setUpSwtich(){
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchState) {
                    switchState = false;
                    textInputLayout.setVisibility(View.GONE);
                }
                else {
                    switchState = true;
                    textInputLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
