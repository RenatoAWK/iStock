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
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.gui.sales.DialogQuantity;
import bsi.mpoo.istock.services.SlopeOne;

public class ProductCartPredictListAdapter  extends RecyclerView.Adapter<ProductCartPredictListAdapter.ProductCartPredictViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private TextView total;
    private ProductCartListAdapter productCartListAdapter;
    private SlopeOne slopeOne;


    public ProductCartPredictListAdapter(Context context, TextView total, ProductCartListAdapter productCartListAdapter){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.total = total;
        this.productCartListAdapter = productCartListAdapter;
        this.slopeOne = new SlopeOne(context);
        this.slopeOne.setUpPredict();
    }

    class ProductCartPredictViewHolder extends RecyclerView.ViewHolder {
        TextView nameItemView;
        TextView totalItemView;
        TextView quantityItemView;
        ImageView imageView;
        ProductCartPredictListAdapter adapter;

        private ProductCartPredictViewHolder(View itemView, final ProductCartPredictListAdapter adapter){
            super(itemView);
            nameItemView = itemView.findViewById(R.id.nameProductCartItemListPredict);
            totalItemView = itemView.findViewById(R.id.priceProductCartItemListPredict);
            quantityItemView = itemView.findViewById(R.id.quantityProductCartItemListPredict);
            imageView = itemView.findViewById(R.id.imageViewProductAdd);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Item item = Cart.getInstance().getPredictItems().get(getLayoutPosition());
                    new DialogQuantity(context,null,null).invokeQuantity(item.getProduct().getQuantity(),item, adapter, productCartListAdapter, total);
                }
            });
            this.adapter = adapter;
        }
    }

    @NonNull
    @Override
    public ProductCartPredictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_cart_list_item_predict, parent, false);
        return new ProductCartPredictViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartPredictViewHolder cartViewHolder, int position) {
        String currentName = Cart.getInstance().getPredictItems().get(position).getProduct().getName();
        String currentPrice = NumberFormat.getCurrencyInstance().format(Cart.getInstance().getPredictItems().get(position).getPrice());
        String currentQuantity = String.valueOf(Cart.getInstance().getPredictItems().get(position).getQuantity());
        cartViewHolder.nameItemView.setText(currentName);
        cartViewHolder.totalItemView.setText(currentPrice);
        cartViewHolder.quantityItemView.setText(currentQuantity);
    }

    @Override
    public int getItemCount() {
        return Cart.getInstance().getPredictItems().size();
    }

}

