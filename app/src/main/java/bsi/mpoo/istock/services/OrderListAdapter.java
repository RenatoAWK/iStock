package bsi.mpoo.istock.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.gui.AlertDialogGenerator;
import bsi.mpoo.istock.gui.DialogDetails;
import bsi.mpoo.istock.gui.EditOrderActivity;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private final ArrayList<Order> orderList;
    private LayoutInflater inflater;
    private Context context;

    public OrderListAdapter(Context context, ArrayList<Order> orderList){
        inflater = LayoutInflater.from(context);
        this.orderList = orderList;
        this.context = context;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        final TextView nameItemView;
        final TextView totalItemView;
        final TextView typeTitleItemView;
        final TextView dateItemView;
        final OrderListAdapter adapter;

        private OrderViewHolder(View itemView, OrderListAdapter adapter ){
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            nameItemView = itemView.findViewById(R.id.nameClientOrderItemList);
            totalItemView = itemView.findViewById(R.id.priceOrderItemList);
            typeTitleItemView = itemView.findViewById(R.id.typeTitleOrderItemList);
            dateItemView = itemView.findViewById(R.id.dateOrderItemList);
            this.adapter = adapter;

        }

        @Override
        public boolean onMenuItemClick(MenuItem item){
            OrderServices orderServices = new OrderServices(context);
            int position = getLayoutPosition();
            Order order = orderList.get(position);
            final String detailOption = context.getApplicationContext().getString(R.string.details);
            final String deleteOption = context.getApplicationContext().getString(R.string.delete);
            final String editOption = context.getApplicationContext().getString(R.string.edit);

            if (item.getTitle().toString().equals(deleteOption)){
                try {
                    orderServices.disableOrder(order);
                    orderList.remove(position);
                    adapter.notifyDataSetChanged();

                }
                catch (Exception error) {
                    new AlertDialogGenerator((Activity) context, error.getMessage(),false).invoke();
                }

            } else if (item.getTitle().equals(editOption)){
                Intent intent = new Intent(context, EditOrderActivity.class);
                intent.putExtra(Constants.BundleKeys.PRODUCT, order);
                context.startActivity(intent);
            } else if (item.getTitle().equals(detailOption)){
                DialogDetails dialogDetails = new DialogDetails(context);
                dialogDetails.invoke(order);
            }
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem detailItem = menu.add(context.getApplicationContext().getString(R.string.details));
            MenuItem editItem = menu.add(context.getApplicationContext().getString(R.string.edit));
            MenuItem deleteItem = menu.add(context.getApplicationContext().getString(R.string.delete));
            detailItem.setOnMenuItemClickListener(this);
            editItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.order_list_item, parent, false);
        return new OrderViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int position) {
        String currentName = orderList.get(position).getClient().getName();
        String currentType = null;
        String currentDate = null;
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault());
        switch (orderList.get(position).getDelivered()){
            case Constants.Order.DELIVERED:
                currentType = context.getString(R.string.realized);
                currentDate = dateFormat.format(orderList.get(position).getDateCreation());
                break;
            case   Constants.Order.NOT_DELIVERED:
                currentType = context.getString(R.string.delivery);
                currentDate = dateFormat.format(orderList.get(position).getDateDelivery());
                break;
        }
        String currentTotal = NumberFormat.getCurrencyInstance().format(orderList.get(position).getTotal());

        orderViewHolder.nameItemView.setText(currentName);
        orderViewHolder.totalItemView.setText(currentTotal);
        orderViewHolder.typeTitleItemView.setText(currentType);
        orderViewHolder.dateItemView.setText(currentDate);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
