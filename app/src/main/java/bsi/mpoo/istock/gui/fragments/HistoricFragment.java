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

import org.json.JSONException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import bsi.mpoo.istock.R;
import bsi.mpoo.istock.data.order.OrderDAO;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Salesman;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.gui.AlertDialogGenerator;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.OrderListAdapter;
import bsi.mpoo.istock.services.OrderServices;

public class HistoricFragment extends Fragment {

    private Context context;
    private Object account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historic, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        account = Session.getInstance().getAccount();
        getActivity().setTitle(getString(R.string.historic_sales));
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Order> ordersArrayList;
        OrderServices orderServices = new OrderServices(getActivity().getApplicationContext());
        if (account instanceof Administrator || account instanceof Salesman){
            try {
                ordersArrayList = orderServices.getOrders(Session.getInstance().getAdministrator());
                RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerviewOrders);
                OrderListAdapter adapter = new OrderListAdapter(context, ordersArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } catch (JSONException e) {
                new AlertDialogGenerator(getActivity(), context.getString(R.string.unknow_error), false).invoke();
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
