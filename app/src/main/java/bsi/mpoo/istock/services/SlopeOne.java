package bsi.mpoo.istock.services;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bsi.mpoo.istock.data.order.OrderDAO;
import bsi.mpoo.istock.data.product.ProductDAO;
import bsi.mpoo.istock.domain.Cart;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.Session;

/**
 * Code based on Daniel Lemire's code:
 */

public class SlopeOne {
    private Context context;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private ArrayList<Order> orders;
    private ItemServices itemServices;

    public SlopeOne(Context context) {
        this.context = context;
        this.orderDAO = new OrderDAO(context);
        this.productDAO = new ProductDAO(context);
        this.itemServices = new ItemServices(context);
    }

    public void setUpPredict() {
        orders = (ArrayList<Order>) orderDAO.getOrdersByAdm(Session.getInstance().getAdministrator());
        Order currentOrder = new Order();
        currentOrder.setItems(Cart.getInstance().getItems());
        currentOrder.setId(Constants.Order.KEY_PREDICT);
        orders.add(currentOrder);
        Map<String, Map<String, Double>> data = new HashMap<>();
        HashMap<String, Double> currentCaseTest = new HashMap<>();
        Set<String> set = new LinkedHashSet<>();
        for (Order order : orders) {
            HashMap<String, Double> caseTeste = new HashMap<>();
            for (Item item : order.getItems()) {
                caseTeste.put(item.getProduct().getName(), (double) item.getQuantity());
                set.add(item.getProduct().getName());
            }
            data.put(String.valueOf(order.getId()), caseTeste);
            if (order.getId() == Constants.Order.KEY_PREDICT) {
                currentCaseTest = caseTeste;
            }
        }
        mAllItems = set.toArray(new String[set.size()]);
        mData = data;
        buildDiffMatrix();
        Map<String, Double> predict = predict(currentCaseTest);
        Map<String, Double> sortedPredict = sortMap(predict);
        ArrayList<Item> items = new ArrayList<>();
        for (String nameProduct : sortedPredict.keySet()) {
            Product product = productDAO.getProductByName(nameProduct, Session.getInstance().getAdministrator());
            items.add(itemServices.convertProductToItem(product));
        }
        Cart.getInstance().setPredictItems(items);
    }

    private Map<String, Double> sortMap(Map<String, Double> predict) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(predict.entrySet());
        list.sort((o1, o2) -> false ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    Map<String, Map<String, Double>> mData;
    Map<String, Map<String, Double>> diffMatrix;
    Map<String, Map<String, Integer>> freqMatrix;
    static String[] mAllItems;

    private Map<String, Double> predict(Map<String, Double> user) {
        HashMap<String, Double> predictions = new HashMap<>();
        HashMap<String, Integer> frequencies = new HashMap<>();
        for (String j : diffMatrix.keySet()) {
            frequencies.put(j, 0);
            predictions.put(j, 0.0);
        }
        for (String j : user.keySet()) {
            for (String k : diffMatrix.keySet()) {
                try {
                    Double newval = (diffMatrix.get(k).get(j) + user.get(j)) * freqMatrix.get(k).get(j).intValue();
                    predictions.put(k, predictions.get(k) + newval);
                    frequencies.put(k, frequencies.get(k) + freqMatrix.get(k).get(j).intValue());
                } catch (NullPointerException e) {
                }
            }
        }
        HashMap<String, Double> cleanpredictions = new HashMap<>();
        for (String j : predictions.keySet()) {
            if (frequencies.get(j) > 0) {
                cleanpredictions.put(j, predictions.get(j) / frequencies.get(j).intValue());
            }
        }
        for (String j : user.keySet()) {
            cleanpredictions.put(j, user.get(j));
        }
        return cleanpredictions;
    }

    private void buildDiffMatrix() {
        diffMatrix = new HashMap<>();
        freqMatrix = new HashMap<>();
        for (Map<String, Double> user : mData.values()) {
            for (Map.Entry<String, Double> entry : user.entrySet()) {
                String i1 = entry.getKey();
                double r1 = entry.getValue();

                if (!diffMatrix.containsKey(i1)) {
                    diffMatrix.put(i1, new HashMap<String, Double>());
                    freqMatrix.put(i1, new HashMap<String, Integer>());
                }

                for (Map.Entry<String, Double> entry2 : user.entrySet()) {
                    String i2 = entry2.getKey();
                    double r2 = entry2.getValue();
                    int cnt = 0;
                    if (freqMatrix.get(i1).containsKey(i2)) cnt = freqMatrix.get(i1).get(i2);
                    double diff = 0.0;
                    if (diffMatrix.get(i1).containsKey(i2)) diff = diffMatrix.get(i1).get(i2);
                    double new_diff = r1 - r2;
                    freqMatrix.get(i1).put(i2, cnt + 1);
                    diffMatrix.get(i1).put(i2, diff + new_diff);
                }
            }
        }
        for (String j : diffMatrix.keySet()) {
            for (String i : diffMatrix.get(j).keySet()) {
                Double oldvalue = diffMatrix.get(j).get(i);
                int count = freqMatrix.get(j).get(i).intValue();
                diffMatrix.get(j).put(i, oldvalue / count);
            }
        }
    }
}

