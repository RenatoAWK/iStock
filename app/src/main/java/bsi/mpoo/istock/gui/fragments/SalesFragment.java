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
import android.widget.SeekBar;

import java.math.BigDecimal;
import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.data.product.ProductDAO;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.ProductOrderListAdapter;
import bsi.mpoo.istock.services.ProductServices;

public class SalesFragment extends Fragment {
    private Object account;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sales, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        account = Session.getInstance().getAccount();
        getActivity().setTitle(getString(R.string.sales));

    }

    @Override
    public void onStart() {
        super.onStart();
        ProductServices productServices = new ProductServices(getActivity().getApplicationContext());
        ArrayList<Product> productArrayList;

        if (account instanceof Administrator || account instanceof Salesman){
            productArrayList = productServices.getAcitiveProductsAsc(Session.getInstance().getAdministrator());
            RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerviewSales);
            ProductOrderListAdapter adapter = new ProductOrderListAdapter(context, productArrayList);
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
