package bsi.mpoo.istock.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Product;

public class DialogDetails extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private Context context;

    public DialogDetails(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("OK",null);

    }

    public void invoke(Client client){

        View view = LayoutInflater.from(context).inflate(R.layout.details_client_dialog, null);

        TextView nameTextView = view.findViewById(R.id.nameDetailsDialogClient);
        TextView streetTextView = view.findViewById(R.id.streetDetailsDialogClient);
        TextView numberTextView = view.findViewById(R.id.numberDetailsDialogClient);
        TextView districtTextView = view.findViewById(R.id.districtDetailsDialogClient);
        TextView cityTextView = view.findViewById(R.id.cityDetailsDialogClient);
        TextView stateTextView = view.findViewById(R.id.stateDetailsDialogClient);
        TextView phoneTextView = view.findViewById(R.id.phoneDetailsDialogClient);

        nameTextView.setText(client.getName());
        streetTextView.setText(client.getAddress().getStreet());
        numberTextView.setText(String.valueOf(client.getAddress().getNumber()));
        districtTextView.setText(client.getAddress().getDistrict());
        cityTextView.setText(client.getAddress().getCity());
        stateTextView.setText(client.getAddress().getState());
        phoneTextView.setText(client.getPhone());

        builder.setView(view);
        builder.show();



    }

    public void invoke(Product product){

        View view = LayoutInflater.from(context).inflate(R.layout.details_product_dialog, null);

        TextView nameTextView = view.findViewById(R.id.nameDetailsDialogProduct);
        TextView priceTextView = view.findViewById(R.id.priceDetailsDialogProduct);
        TextView quantityTextView = view.findViewById(R.id.quantityDetailsDialogProduct);
        TextView minimumTextView = view.findViewById(R.id.minimumDetailsDialogProduct);

        nameTextView.setText(product.getName());
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(product.getPrice()));
        quantityTextView.setText(String.valueOf(product.getQuantity()));
        if (product.getMinimumQuantity() == 0){
            minimumTextView.setText(R.string.without_info);
        }
        else {
            minimumTextView.setText(String.valueOf(product.getMinimumQuantity()));
        }

        builder.setView(view);
        builder.show();



    }
}
