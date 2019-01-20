package bsi.mpoo.istock.services.order;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.gui.DialogDetails;
import bsi.mpoo.istock.gui.historic.EditOrderActivity;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.DateServices;

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
        final ImageView imageView;
        final OrderListAdapter adapter;

        private OrderViewHolder(View itemView, OrderListAdapter adapter ){
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            nameItemView = itemView.findViewById(R.id.nameClientOrderItemList);
            totalItemView = itemView.findViewById(R.id.priceOrderItemList);
            typeTitleItemView = itemView.findViewById(R.id.typeTitleOrderItemList);
            dateItemView = itemView.findViewById(R.id.dateOrderItemList);
            imageView = itemView.findViewById(R.id.imageViewHistoric);
            this.adapter = adapter;

        }

        @Override
        public boolean onMenuItemClick(MenuItem item){
            int position = getLayoutPosition();
            Order order = orderList.get(position);
            final String detailOption = context.getApplicationContext().getString(R.string.details);
            final String editOption = context.getApplicationContext().getString(R.string.edit);

            if (item.getTitle().equals(editOption)){
                Intent intent = new Intent(context, EditOrderActivity.class);
                intent.putExtra(Constants.BundleKeys.ORDER, order);
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
            detailItem.setOnMenuItemClickListener(this);
            editItem.setOnMenuItemClickListener(this);
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
        String currentType;
        String currentDate;
        if (orderList.get(position).getDelivered() == Constants.Order.DELIVERED){
            currentType = context.getString(R.string.realized);
            currentDate = DateServices.localDateToFormatedToString(orderList.get(position).getDateCreation());
            if (orderList.get(position).getDateDelivery() != null){
                orderViewHolder.imageView.setBackgroundResource(R.drawable.calendar_ok);
            } else {
                orderViewHolder.imageView.setBackgroundResource(R.drawable.cart);
            }
        } else {
            currentType = context.getString(R.string.delivery);
            currentDate = DateServices.localDateToFormatedToString(orderList.get(position).getDateDelivery());
            orderViewHolder.imageView.setBackgroundResource(R.drawable.calendar_warning);
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
