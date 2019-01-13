package bsi.mpoo.istock.services.product;

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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.ArrayList;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.gui.AlertDialogGenerator;
import bsi.mpoo.istock.gui.DialogDetails;
import bsi.mpoo.istock.gui.product.EditProductActivity;
import bsi.mpoo.istock.services.Constants;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>  implements Filterable{

    private final ArrayList<Product> productList;
    private ArrayList<Product> productListFull;
    private LayoutInflater inflater;
    private Context context;

    public ProductListAdapter(Context context, ArrayList<Product> productList){
        inflater = LayoutInflater.from(context);
        this.productList = productList;
        this.context = context;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        final TextView nameItemView;
        final TextView quantityItemView;
        final TextView priceItemView;
        final ProductListAdapter adapter;

        private ProductViewHolder(View itemView, ProductListAdapter adapter ){
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            nameItemView = itemView.findViewById(R.id.nameProductItemList);
            quantityItemView = itemView.findViewById(R.id.quantityProductItemList);
            priceItemView = itemView.findViewById(R.id.priceProductItemList);
            this.adapter = adapter;

        }

        @Override
        public boolean onMenuItemClick(MenuItem item){
            ProductServices productServices = new ProductServices(context);
            int position = getLayoutPosition();
            Product product = productList.get(position);
            final String detailOption = context.getApplicationContext().getString(R.string.details);
            final String deleteOption = context.getApplicationContext().getString(R.string.delete);
            final String editOption = context.getApplicationContext().getString(R.string.edit);

            if (item.getTitle().toString().equals(deleteOption)){
                try {
                    productServices.disableProduct(product, Session.getInstance().getAdministrator());
                    productList.remove(position);
                    adapter.notifyDataSetChanged();

                }
                catch (Exception error) {
                    new AlertDialogGenerator((Activity) context, error.getMessage(),false).invoke();
                }

            } else if (item.getTitle().equals(editOption)){
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra(Constants.BundleKeys.PRODUCT, product);
                context.startActivity(intent);
            } else if (item.getTitle().equals(detailOption)){
                DialogDetails dialogDetails = new DialogDetails(context);
                dialogDetails.invoke(product);
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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        String currentName = productList.get(position).getName();
        String currentQuantity = String.valueOf(productList.get(position).getQuantity());
        String currentPrice = NumberFormat.getCurrencyInstance().format(productList.get(position).getPrice());
        productViewHolder.nameItemView.setText(currentName);
        productViewHolder.quantityItemView.setText(currentQuantity);
        productViewHolder.priceItemView.setText(currentPrice);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Product> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(productListFull);
            } else {
                String filtedPattern = constraint.toString().toLowerCase().trim();
                for(Product product : productListFull){
                    if(product.getName().toLowerCase().contains(filtedPattern)){
                        filteredList.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}