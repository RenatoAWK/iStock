package bsi.mpoo.istock.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private final ArrayList<Client> clientList;
    private LayoutInflater inflater;

    public ClientListAdapter(Context context, ArrayList<Client> clientList){
        inflater = LayoutInflater.from(context);
        this.clientList = clientList;
    }

    class ClientViewHolder extends RecyclerView.ViewHolder{
        public final TextView nameItemView;
        public final TextView phoneItemView;
        final ClientListAdapter adapter;

        public ClientViewHolder(View itemView, ClientListAdapter adapter ){
            super(itemView);
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
        String currentPhone = clientList.get(position).getPhone();
        clientViewHolder.nameItemView.setText(currentName);
        clientViewHolder.phoneItemView.setText(currentPhone);
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }
}
