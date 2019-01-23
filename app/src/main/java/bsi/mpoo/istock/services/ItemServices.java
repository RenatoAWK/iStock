package bsi.mpoo.istock.services;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.data.item.ItemDAO;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.Product;

public class ItemServices {

    private ItemDAO itemDAO;

    public ItemServices(Context context){
        this.itemDAO = new ItemDAO(context);
    }

    public void insertItem(Item item){
        itemDAO.insertItem(item);
    }

    public void insertItem(ArrayList<Item> items){
        itemDAO.insertItem(items);
    }

    public Item getItemById(long id){
        return itemDAO.getItemById(id);
    }

    public List<Item> getItemsOrderById(Order order){
        return itemDAO.getItemsOrderById(order, Contract.ASC);
    }

    public List<Item> getActiveItemsByOrderId(Order order){
        return itemDAO.getActiveItemsByOrderId(order, Contract.ASC);
    }

    public void disableItem(Item item){
        itemDAO.disableItem(item);
    }

    public void updateItem(Item item){
        itemDAO.updateItem(item);
    }

    public Item convertProductToItem(Product product){
        Item item = new Item();
        item.setPrice(product.getPrice());
        item.setIdAdministrator(product.getAdministrator().getUser().getId());
        item.setStatus(product.getStatus());
        item.setProduct(product);
        return item;
    }
}
