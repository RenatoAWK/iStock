package bsi.mpoo.istock.services.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Cart;
import bsi.mpoo.istock.domain.Item;

public class ProductCartListAdapter  extends RecyclerView.Adapter<ProductCartListAdapter.ProductCartViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private TextView total;


    public ProductCartListAdapter(Context context, TextView total){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.total = total;
    }

    class ProductCartViewHolder extends RecyclerView.ViewHolder {
        TextView nameItemView;
        TextView totalItemView;
        TextView quantityItemView;
        ImageView imageView;
        ProductCartListAdapter adapter;

        private ProductCartViewHolder(View itemView, final ProductCartListAdapter adapter){
            super(itemView);
            nameItemView = itemView.findViewById(R.id.nameProductCartItemList);
            totalItemView = itemView.findViewById(R.id.priceProductCartItemList);
            quantityItemView = itemView.findViewById(R.id.quantityProductCartItemList);
            imageView = itemView.findViewById(R.id.imageViewProductRemove);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart.getInstance().removeItem(Cart.getInstance().getItems().get(getLayoutPosition()));
                    adapter.notifyDataSetChanged();
                    total.setText(NumberFormat.getCurrencyInstance().format(Cart.getInstance().getTotal()));
                }
            });
            this.adapter = adapter;
        }
    }

    @NonNull
    @Override
    public ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_cart_list_item, parent, false);
        return new ProductCartViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartViewHolder cartViewHolder, int position) {
        String currentName = Cart.getInstance().getItems().get(position).getProduct().getName();
        String currentPrice = NumberFormat.getCurrencyInstance().format(Cart.getInstance().getItems().get(position).getPrice());
        String currentQuantity = String.valueOf(Cart.getInstance().getItems().get(position).getQuantity());
        cartViewHolder.nameItemView.setText(currentName);
        cartViewHolder.totalItemView.setText(currentPrice);
        cartViewHolder.quantityItemView.setText(currentQuantity);
    }

    @Override
    public int getItemCount() {
        return Cart.getInstance().getItems().size();
    }

}
