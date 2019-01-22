package bsi.mpoo.istock.services.client;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.gui.DetailsActivity;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.MaskGenerator;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder>{

    private final ArrayList<Client> clientList;
    private LayoutInflater inflater;
    private Context context;

    public ClientListAdapter(Context context, ArrayList<Client> clientList){
        inflater = LayoutInflater.from(context);
        this.clientList = clientList;
        this.context = context;

    }

    class ClientViewHolder extends RecyclerView.ViewHolder {
        final TextView nameItemView;
        final TextView phoneItemView;
        final ClientListAdapter adapter;

        private ClientViewHolder(View itemView, ClientListAdapter adapter ){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(Constants.BundleKeys.OBJECT, clientList.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
            nameItemView = itemView.findViewById(R.id.nameClientItemList);
            phoneItemView = itemView.findViewById(R.id.phoneClientItemList);
            this.adapter = adapter;

        }

    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.client_list_item, parent, false);
        return new ClientViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder clientViewHolder, int position) {
        String currentName = clientList.get(position).getName();
        String currentPhone = MaskGenerator.unmaskedTextToStringMasked(clientList.get(position).getPhone(), Constants.MaskTypes.PHONE);
        clientViewHolder.nameItemView.setText(currentName);
        clientViewHolder.phoneItemView.setText(currentPhone);
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }
}
