package bsi.mpoo.istock.services.user;

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

import java.util.ArrayList;

import bsi.mpoo.istock.gui.DetailsActivity;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.ImageServices;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{
    private final ArrayList<User> userList;
    private LayoutInflater inflater;
    private Context context;

    public UserListAdapter(Context context, ArrayList<User> userList){
        inflater = LayoutInflater.from(context);
        this.userList = userList;
        this.context = context;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        final TextView nameItemView;
        final TextView functionItemView;
        final ImageView imageView;
        final ImageView imageViewSituation;
        final UserListAdapter adapter;

        private UserViewHolder(View itemView, UserListAdapter adapter ){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(Constants.BundleKeys.OBJECT, userList.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
            nameItemView = itemView.findViewById(R.id.nameUserItemList);
            functionItemView = itemView.findViewById(R.id.functionUserItemList);
            imageView = itemView.findViewById(R.id.imageViewUser);
            imageViewSituation = itemView.findViewById(R.id.imageViewUserSituation);
            this.adapter = adapter;

        }

    }

    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.user_list_item, parent, false);
        return new UserListAdapter.UserViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder userViewHolder, int position) {
        String currentName = userList.get(position).getName();
        String currentFunction = null;
        Bitmap currentImage;
        if (userList.get(position).getImage() != null){
            ImageServices imageServices = new ImageServices();
            currentImage = imageServices.byteToImage(userList.get(position).getImage());
            userViewHolder.imageView.setImageBitmap(currentImage);
            userViewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        switch (userList.get(position).getType()){
            case Constants.UserTypes.ADMINISTRATOR:
                currentFunction = context.getString(R.string.administration);
                break;
            case Constants.UserTypes.SALESMAN:
                currentFunction = context.getString(R.string.sales);
                break;
            case Constants.UserTypes.PRODUCER:
                currentFunction = context.getString(R.string.production);
                break;
        }
        userViewHolder.nameItemView.setText(currentName);
        userViewHolder.functionItemView.setText(currentFunction);
        switch (userList.get(position).getStatus()){
            case Constants.Status.INACTIVE:
                userViewHolder.imageViewSituation.setImageResource(R.drawable.user_remove);
                break;
            case Constants.Status.FIRST_ACCESS_FOR_USER:
                userViewHolder.imageViewSituation.setImageResource(R.drawable.user_warning);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
