package bsi.mpoo.istock.services;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import bsi.mpoo.istock.data.order.OrderDAO;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Order;

public class OrderServices {

    private OrderDAO orderDAO;

    public OrderServices(Context context){
        this.orderDAO = new OrderDAO(context);
    }

    public boolean isOrderRegistered(Order order) throws JSONException {
        Order searchedOrder = orderDAO.getOrderById(order.getId());
        return searchedOrder != null;
    }

    public void registerOrder(Order order) throws Exception {
            orderDAO.insertOrder(order);
    }

    public void updateOrder(Order order) throws Exception{
        try {
            orderDAO.updateOrder(order);
        } catch (Exception error){
            throw new Exceptions.OrderNotRegistered();
        }
    }

    public void disableOrder(Order order) throws Exception{

        if (isOrderRegistered(order)){
            orderDAO.disableOrder(order);
        } else {
            throw  new Exceptions.OrderNotRegistered();
        }
    }

    public ArrayList<Order> getAcitiveOrders(Administrator administrator) throws JSONException {
        return (ArrayList<Order>) orderDAO.getActiveOrdersByAdm (administrator);
    }

    public ArrayList<Order> getOrders(Administrator administrator) throws JSONException {
        return (ArrayList<Order>) orderDAO.getOrdersByAdm (administrator);
    }

}
