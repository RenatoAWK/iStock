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
        //////
        OrderDAO orderDAO = new OrderDAO(context);
        Order order = new Order();
        order.setStatus(Constants.Status.ACTIVE);
        order.setAdministrator(Session.getInstance().getAdministrator());
        Client client = new Client();
        client.setId(10);
        order.setClient(client);
        order.setDateCreation(LocalDate.now());
        order.setDateDelivery(LocalDate.now());
        order.setTotal(new BigDecimal("2000"));
        Item item = new Item();
        item.setId_order(2);
        item.setName("produto1");
        item.setPrice(new BigDecimal("100"));
        item.setQuantity(1);
        Item item2 = new Item();
        item2.setId_order(2);
        item2.setName("produto2");
        item2.setPrice(new BigDecimal("200"));
        item2.setQuantity(1);
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(item);
        arrayList.add(item2);
        order.setItems(arrayList);
        try {
            orderDAO.insertOrder(order);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
