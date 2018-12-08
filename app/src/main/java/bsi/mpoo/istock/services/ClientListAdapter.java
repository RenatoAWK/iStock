package bsi.mpoo.istock.services;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.gui.AlertDialogGenerator;
import bsi.mpoo.istock.gui.EditClientActivity;
import bsi.mpoo.istock.gui.LoginActivity;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private final ArrayList<Client> clientList;
    private LayoutInflater inflater;
    private Context context;
    private User user;

    public ClientListAdapter(Context context, ArrayList<Client> clientList, User user){
        inflater = LayoutInflater.from(context);
        this.clientList = clientList;
        this.user = user;
        this.context = context;

    }

    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public final TextView nameItemView;
        public final TextView phoneItemView;
        final ClientListAdapter adapter;

        public ClientViewHolder(View itemView, ClientListAdapter adapter ){
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            nameItemView = itemView.findViewById(R.id.nameClientItemList);
            phoneItemView = itemView.findViewById(R.id.phoneClientItemList);
            this.adapter = adapter;

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            ClientServices clientServices = new ClientServices(context);

            int position = getLayoutPosition();
            Client client = clientList.get(position);

            final String detaillOption = context.getApplicationContext().getString(R.string.details);
            final String deleteOption = context.getApplicationContext().getString(R.string.delete);
            final String editOption = context.getApplicationContext().getString(R.string.edit);

            if (item.getTitle().toString().equals(deleteOption)){


                try {
                    clientServices.disableClient(client);
                    clientList.remove(position);
                    adapter.notifyDataSetChanged();

                }
                catch (Exception error) {

                    new AlertDialogGenerator((Activity) context, error.getMessage(),false).invoke();

                }

            } else if (item.getTitle().equals(editOption)){
                Intent intent = new Intent(context, EditClientActivity.class);
                intent.putExtra("client", client);
                context.startActivity(intent);

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
