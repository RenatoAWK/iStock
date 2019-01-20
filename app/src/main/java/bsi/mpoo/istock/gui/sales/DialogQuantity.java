package bsi.mpoo.istock.gui.sales;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Cart;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.services.product.ProductOrderListAdapter;

public class DialogQuantity {
    private Context context;
    private ImageView cart;
    private ImageView status;

    public DialogQuantity(Context context, ImageView cart, ImageView status) {
        this.context = context;
        this.cart = cart;
        this.status = status;
    }


    public void invokeQuantity(final long quantityMax, final Item item){
        long quantity = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        View view = inflater.inflate(R.layout.quantity_order, null, false);
        ImageView imageViewSubtract = view.findViewById(R.id.subtract);
        ImageView imageViewAdd = view.findViewById(R.id.add);
        final TextView textViewQuantity = view.findViewById(R.id.textViewQuantity);
        textViewQuantity.setText(String.valueOf(quantity));
        imageViewSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentValue = Long.parseLong(textViewQuantity.getText().toString());
                if (currentValue > 0){
                    currentValue--;
                    textViewQuantity.setText(String.valueOf(currentValue));
                }
            }
        });
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentValue = Long.parseLong(textViewQuantity.getText().toString());
                if (currentValue < quantityMax){
                    currentValue++;
                    textViewQuantity.setText(String.valueOf(currentValue));
                }
            }
        });
        builder.setPositiveButton(context.getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long currentValue = Long.parseLong(textViewQuantity.getText().toString());
                if (currentValue > 0){
                    item.setQuantity(currentValue);
                    Cart.getInstance().addItem(item);
                    cart.setBackgroundResource(R.drawable.ic_sales_after);
                    status.setImageResource(R.drawable.cart_remove);
                }


            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view);

        builder.show();

    }
}

