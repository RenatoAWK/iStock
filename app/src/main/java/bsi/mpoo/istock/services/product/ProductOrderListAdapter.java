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
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.ArrayList;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Cart;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.gui.DialogDetails;
import bsi.mpoo.istock.gui.sales.DialogQuantity;
import bsi.mpoo.istock.services.ImageServices;
import bsi.mpoo.istock.services.ItemServices;

public class ProductOrderListAdapter extends RecyclerView.Adapter<ProductOrderListAdapter.ProductOrderViewHolder> {

    private final ArrayList<Product> productList;
    private LayoutInflater inflater;
    private Context context;
    private ImageView cart;
    private ItemServices itemServices;


    public ProductOrderListAdapter(Context context, ArrayList<Product> productList, ImageView cart){
        inflater = LayoutInflater.from(context);
        this.itemServices = new ItemServices(context);
        this.productList = productList;
        this.context = context;
        this.cart = cart;
    }

    class ProductOrderViewHolder extends RecyclerView.ViewHolder {
        TextView nameItemView;
        TextView totalItemView;
        TextView quantityItemView;
        LinearLayout linearLayout;
        ImageView imageView;
        ImageView imageViewStatus;
        ProductOrderListAdapter adapter;
        Product product;

        private ProductOrderViewHolder(View itemView, final ProductOrderListAdapter adapter){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product = productList.get(getLayoutPosition());
                    new DialogDetails(context).invoke(product);
                }
            });
            nameItemView = itemView.findViewById(R.id.nameProductOrderItemList);
            totalItemView = itemView.findViewById(R.id.priceProductOrderItemList);
            quantityItemView = itemView.findViewById(R.id.quantityProductOrderItemList);
            linearLayout = itemView.findViewById(R.id.linearLayoutProductOrderListItem);
            imageView = itemView.findViewById(R.id.imageViewProductOrder);
            imageViewStatus = itemView.findViewById(R.id.imageViewProductStatus);
            imageViewStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product = productList.get(getLayoutPosition());
                    Item itemConverted = itemServices.convertProductToItem(product);
                    if (Cart.getInstance().getItems().contains(itemServices.convertProductToItem(product))){
                        Cart.getInstance().removeItem(itemServices.convertProductToItem(product));
                        adapter.notifyDataSetChanged();
                        if (Cart.getInstance().getItems().size() == 0){
                            cart.setBackgroundResource(R.drawable.ic_sales_before);
                            imageViewStatus.setImageResource(R.drawable.cart_add);
                        }
                    } else {
                        new DialogQuantity(context, cart, imageViewStatus).invokeQuantity(product.getQuantity(), itemConverted);
                    }
                }
            });
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
        String currentName = productList.get(position).getName();
        String currentPrice = NumberFormat.getCurrencyInstance().format(productList.get(position).getPrice());
        String currentQuantity = context.getString(R.string.at_stock)+":  "+String.valueOf(productList.get(position).getQuantity());
        if (productList.get(position).getImage() != null){
            ImageServices imageServices = new ImageServices();
            orderViewHolder.imageView.setImageBitmap(imageServices.byteToImage(productList.get(position).getImage()));
        }
        orderViewHolder.nameItemView.setText(currentName);
        orderViewHolder.totalItemView.setText(currentPrice);
        orderViewHolder.quantityItemView.setText(currentQuantity);
        if (Cart.getInstance().getItems().contains(itemServices.convertProductToItem(productList.get(position)))){
            orderViewHolder.imageViewStatus.setImageResource(R.drawable.cart_remove);
        } else {
            orderViewHolder.imageViewStatus.setImageResource(R.drawable.cart_add);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
