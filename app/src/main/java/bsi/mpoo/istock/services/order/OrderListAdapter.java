package bsi.mpoo.istock.services.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Temp;
import bsi.mpoo.istock.gui.DetailsActivity;
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

    class OrderViewHolder extends RecyclerView.ViewHolder {
        final TextView nameItemView;
        final TextView totalItemView;
        final TextView typeTitleItemView;
        final TextView dateItemView;
        final ImageView imageView;
        final OrderListAdapter adapter;

        private OrderViewHolder(View itemView, OrderListAdapter adapter ){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    Temp.setOrder(orderList.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
            nameItemView = itemView.findViewById(R.id.nameClientOrderItemList);
            totalItemView = itemView.findViewById(R.id.priceOrderItemList);
            typeTitleItemView = itemView.findViewById(R.id.typeTitleOrderItemList);
            dateItemView = itemView.findViewById(R.id.dateOrderItemList);
            imageView = itemView.findViewById(R.id.imageViewHistoric);
            this.adapter = adapter;

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
