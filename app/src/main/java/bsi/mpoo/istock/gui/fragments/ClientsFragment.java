package bsi.mpoo.istock.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.ClientListAdapter;
import bsi.mpoo.istock.services.ClientServices;

public class ClientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ClientListAdapter adapter;
    private User user;
    private ClientServices clientServices;
    private ArrayList<Client> clientArrayList;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clients, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = getArguments().getParcelable("user");

        getActivity().setTitle(getString(R.string.clients));


    }

    @Override
    public void onStart() {
        super.onStart();
        clientServices = new ClientServices(getActivity().getApplicationContext());
        clientArrayList = clientServices.getAcitiveClientsAsc(user);

        recyclerView = getActivity().findViewById(R.id.recyclerviewClient);

        adapter = new ClientListAdapter(context, clientArrayList, user);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
