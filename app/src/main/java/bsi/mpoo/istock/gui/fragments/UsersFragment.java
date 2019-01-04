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
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.UserListAdapter;
import bsi.mpoo.istock.services.UserServices;

public class UsersFragment extends Fragment {

    private Object account;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        account = Session.getInstance().getAccount();
        getActivity().setTitle(getString(R.string.users));
    }

    @Override
    public void onStart() {
        super.onStart();
        UserServices userServices = new UserServices(getActivity().getApplicationContext());
        ArrayList<User> userArrayList;

        if (account instanceof Administrator){
            userArrayList = userServices.getUsersAsc(Session.getInstance().getAdministrator());
            RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerviewUser);
            UserListAdapter adapter = new UserListAdapter(context, userArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
