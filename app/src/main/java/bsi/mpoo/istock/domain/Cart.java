package bsi.mpoo.istock.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Cart {
    private static Cart instance = new Cart();
    private static ArrayList<Item> items = new ArrayList<>();
    private static ArrayList<Item> predictItems = new ArrayList<>();
    private static BigDecimal total;

    public static Cart getInstance() {
        return instance;
    }

    public static void setInstance(Cart instance) {
        Cart.instance = instance;
    }

    public ArrayList<Item> getItems() {
        return Cart.items;
    }

    public void setItems(ArrayList<Item> items) {
        Cart.items = items;
    }

    public BigDecimal getTotal() {
        BigDecimal newTotal = new BigDecimal("0");
        for (Item item: getItems()){
            newTotal = newTotal.add(item.getTotalPrice());
        }
        return newTotal;
    }

    public void setTotal(BigDecimal total) {
        Cart.total = total;
    }

    public void addItem(Item item){
        Cart.items.add(item);
    }

    public void removeItem(Item item){
        Cart.items.remove(item);
    }

    public void removeAllItems(){
        Cart.items.clear();
    }

    public ArrayList<Item> getPredictItems() {
        return predictItems;
    }

    public void setPredictItems(ArrayList<Item> predictItems) {
        Cart.predictItems.clear();
        for (Iterator<Item> iterator = predictItems.iterator(); iterator.hasNext();){
            if (Cart.predictItems.size() >= 3){
                break;
            }
            Item itereted = iterator.next();
            if (!Cart.items.contains(itereted)){
                if (itereted.getProduct().getQuantity() > 0){
                    Cart.predictItems.add(itereted);
                }
            }
        }
    }

    public void removePredictItem(Item item){
        Cart.predictItems.remove(item);
    }


}
