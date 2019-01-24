package bsi.mpoo.istock.services.product;

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

    public class ItemOrderListAdapter  extends RecyclerView.Adapter<ItemOrderListAdapter.ItemOrderViewHolder>{

        private LayoutInflater inflater;
        private ArrayList<Item> arrayList;


        public ItemOrderListAdapter(Context context, ArrayList<Item> arrayList){
            inflater = LayoutInflater.from(context);
            this.arrayList  = arrayList;
        }

        class ItemOrderViewHolder extends RecyclerView.ViewHolder{
            TextView nameItemView;
            TextView totalItemView;
            TextView quantityItemView;
            ItemOrderListAdapter adapter;

            private ItemOrderViewHolder(View itemView, ItemOrderListAdapter adapter){
                super(itemView);
                nameItemView = itemView.findViewById(R.id.nameItemOrderList);
                totalItemView = itemView.findViewById(R.id.priceItemOrderList);
                quantityItemView = itemView.findViewById(R.id.quantityItemOrderList);
                this.adapter = adapter;
            }
        }

        @NonNull
        @Override
        public ItemOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.item_order, parent, false);
            return new ItemOrderViewHolder(itemView, this);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemOrderViewHolder itemOrderViewHolder, int position) {
            String currentName = arrayList.get(position).getProduct().getName();
            String currentPrice = NumberFormat.getCurrencyInstance().format(arrayList.get(position).getPrice());
            String currentQuantity = String.valueOf(arrayList.get(position).getQuantity());
            itemOrderViewHolder.nameItemView.setText(currentName);
            itemOrderViewHolder.totalItemView.setText(currentPrice);
            itemOrderViewHolder.quantityItemView.setText(currentQuantity);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

    }

