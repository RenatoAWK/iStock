package bsi.mpoo.istock.services;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.User;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private final ArrayList<Client> clientList;
    private LayoutInflater inflater;
    private Context context;

    public ClientListAdapter(Context context, ArrayList<Client> clientList){
        inflater = LayoutInflater.from(context);
        this.clientList = clientList;
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

            if (item.getTitle().toString().equals("Deletar")){

                int position = getLayoutPosition();
                Client client = clientList.get(position);
                try {
                    clientServices.disableClient(client, client.getIdAdm());
                    clientList.remove(position);
                    adapter.notifyDataSetChanged();

                }
                catch (Exception error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(error.getMessage());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }

            }

            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem detailItem = menu.add("Detalhes");
            MenuItem editItem = menu.add("Editar");
            MenuItem deleteItem = menu.add("Deletar");

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
