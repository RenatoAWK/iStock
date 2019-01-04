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
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.ProductListAdapter;
import bsi.mpoo.istock.services.ProductServices;

public class ProductsFragment extends Fragment {

    private User user;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = Session.getInstance().getUser();
        getActivity().setTitle(getString(R.string.products));
    }

    @Override
    public void onStart() {
        super.onStart();
        ProductServices productServices = new ProductServices(getActivity().getApplicationContext());
        ArrayList<Product> productArrayList;
        if (user.getType() == Constants.UserTypes.ADMINISTRATOR) {
            productArrayList = productServices.getAcitiveProductsAsc(user);
        } else {
            User adm = new User();
            adm.setId(user.getAdministrator());
            productArrayList = productServices.getAcitiveProductsAsc(adm);
        }
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerviewProduct);
        ProductListAdapter adapter = new ProductListAdapter(context, productArrayList, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
