package bsi.mpoo.istock.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.ArrayList;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Item;

public class ProductOrderListAdapter extends RecyclerView.Adapter<ProductOrderListAdapter.ProductOrderViewHolder> {

    private final ArrayList<Item> productOrderList;
    private LayoutInflater inflater;

    public ProductOrderListAdapter(Context context,ArrayList<Item> productOrderList){
        inflater = LayoutInflater.from(context);
        this.productOrderList = productOrderList;
    }

    class ProductOrderViewHolder extends RecyclerView.ViewHolder {
        final TextView nameItemView;
        final TextView totalItemView;
        final ProductOrderListAdapter adapter;

        private ProductOrderViewHolder(View itemView, ProductOrderListAdapter adapter ){
            super(itemView);
            nameItemView = itemView.findViewById(R.id.nameProductOrderItemList);
            totalItemView = itemView.findViewById(R.id.priceProductOrderItemList);
            this.adapter = adapter;

        }
    }

    @NonNull
    @Override
    public ProductOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_order_list_item, parent, false);
        return new ProductOrderViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderViewHolder orderViewHolder, int position) {
        String currentName = productOrderList.get(position).getName();
        String currentPrice = NumberFormat.getCurrencyInstance().format(productOrderList.get(position).getPrice());
        orderViewHolder.nameItemView.setText(currentName);
        orderViewHolder.totalItemView.setText(currentPrice);
    }

    @Override
    public int getItemCount() {
        return productOrderList.size();
    }
}
