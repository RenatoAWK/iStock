package bsi.mpoo.istock.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.user.UserListAdapter;
import bsi.mpoo.istock.services.user.UserServices;

public class UsersFragment extends Fragment {

    private Object account;
    private Context context;
    private UserListAdapter adapter;

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
            userArrayList = userServices.getAcitiveUsersAsc(Session.getInstance().getAdministrator());
            RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerviewUser);
            adapter = new UserListAdapter(context, userArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}
