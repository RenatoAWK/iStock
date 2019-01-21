package bsi.mpoo.istock.services.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.gui.DetailsActivity;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.ImageServices;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>{

    private final ArrayList<Product> productList;
    private LayoutInflater inflater;
    private Context context;
    private ImageServices imageServices = new ImageServices();

    public ProductListAdapter(Context context, ArrayList<Product> productList){
        inflater = LayoutInflater.from(context);
        this.productList = productList;
        this.context = context;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView nameItemView;
        final TextView quantityItemView;
        final TextView priceItemView;
        final ImageView imageView;
        final ProductListAdapter adapter;

        private ProductViewHolder(View itemView, ProductListAdapter adapter ){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(Constants.BundleKeys.OBJECT, productList.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
            nameItemView = itemView.findViewById(R.id.nameProductItemList);
            quantityItemView = itemView.findViewById(R.id.quantityProductItemList);
            priceItemView = itemView.findViewById(R.id.priceProductItemList);
            imageView = itemView.findViewById(R.id.imageViewProduct);
            this.adapter = adapter;

        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        String currentName = productList.get(position).getName();
        String currentQuantity = context.getString(R.string.at_stock)+":  "+String.valueOf(productList.get(position).getQuantity());
        String currentPrice = NumberFormat.getCurrencyInstance().format(productList.get(position).getPrice());
        Bitmap currentBitmap = imageServices.byteToImage(productList.get(position).getImage());
        productViewHolder.nameItemView.setText(currentName);
        productViewHolder.quantityItemView.setText(currentQuantity);
        productViewHolder.priceItemView.setText(currentPrice);
        if (currentBitmap != null){
            productViewHolder.imageView.setImageBitmap(currentBitmap);
            productViewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}