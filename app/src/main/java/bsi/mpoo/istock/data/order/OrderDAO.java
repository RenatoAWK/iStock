package bsi.mpoo.istock.data.order;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.provider.BaseColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.data.DbHelper;
import bsi.mpoo.istock.data.client.ClientDAO;
import bsi.mpoo.istock.data.user.UserDAO;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Item;
import bsi.mpoo.istock.domain.Order;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;
import bsi.mpoo.istock.services.UserServices;

public class OrderDAO {
    private Context context;
    private ClientDAO clientDAO;
    private UserServices userServices;
    private UserDAO userDAO;

    public OrderDAO(Context context){
        this.context = context;
    }

    public void insertOrder(Order order) throws JSONException {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContractOrder.COLUMN_DATE_CREATION, order.getDateCreation().toString());
        values.put(ContractOrder.COLUMN_ID_CLIENT, order.getClient().getId());
        values.put(ContractOrder.COLUMN_ID_ADM, order.getAdministrator().getUser().getId());
        values.put(ContractOrder.COLUMN_TOTAL, order.getTotal().toString());
        values.put(ContractOrder.COLUMN_DELIVERED, order.getDelivered());
        values.put(ContractOrder.COLUMN_DATE_DELIVERY, order.getDateDelivery().toString());
        values.put(ContractOrder.COLUMN_STATUS, order.getStatus());
        values.put(ContractOrder.COLUMN_ITEMS, convertArrayItemToJSONinSring(order.getItems()));
        long newRowID = db.insert(ContractOrder.TABLE_NAME, null, values);
        order.setId(newRowID);
        db.close();
    }

    public Order getOrderById(long id) throws JSONException {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Order searchedOrder = null;
        String[] projection = {
                BaseColumns._ID,
                ContractOrder.COLUMN_DATE_CREATION,
                ContractOrder.COLUMN_ID_CLIENT,
                ContractOrder.COLUMN_ID_ADM,
                ContractOrder.COLUMN_TOTAL,
                ContractOrder.COLUMN_DELIVERED,
                ContractOrder.COLUMN_DATE_DELIVERY,
                ContractOrder.COLUMN_STATUS,
                ContractOrder.COLUMN_ITEMS
        };
        String selection = ContractOrder._ID+" = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(
                ContractOrder.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount()==1){
            cursor.moveToNext();
            searchedOrder = createOrder(cursor);
        }
        cursor.close();
        db.close();
        return searchedOrder;
    }

    public List<Order> getOrdersByAdm(Administrator administrator) throws JSONException {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                ContractOrder.COLUMN_DATE_CREATION,
                ContractOrder.COLUMN_ID_CLIENT,
                ContractOrder.COLUMN_ID_ADM,
                ContractOrder.COLUMN_TOTAL,
                ContractOrder.COLUMN_DELIVERED,
                ContractOrder.COLUMN_DATE_DELIVERY,
                ContractOrder.COLUMN_STATUS,
                ContractOrder.COLUMN_ITEMS
        };
        List<Order> orderList = new ArrayList<>();
        String selection = ContractOrder.COLUMN_ID_ADM + " = ?";
        String[] selectionArgs = {String.valueOf(administrator.getUser().getId())};
        Cursor cursor = db.query(
                ContractOrder.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToNext()){
            do {
                Order order = createOrder(cursor);
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderList;
    }

    public List<Order> getActiveOrdersByAdm(Administrator administrator) throws JSONException {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                ContractOrder.COLUMN_DATE_CREATION,
                ContractOrder.COLUMN_ID_CLIENT,
                ContractOrder.COLUMN_ID_ADM,
                ContractOrder.COLUMN_TOTAL,
                ContractOrder.COLUMN_DELIVERED,
                ContractOrder.COLUMN_DATE_DELIVERY,
                ContractOrder.COLUMN_STATUS,
                ContractOrder.COLUMN_ITEMS
        };
        List<Order> orderList = new ArrayList<>();
        String selection = ContractOrder.COLUMN_ID_ADM + " = ?"+" AND "+
                ContractOrder.COLUMN_STATUS + " = ?";
        String[] selectionArgs = {String.valueOf(administrator.getUser().getId()),
                String.valueOf(Constants.Status.ACTIVE)};
        Cursor cursor = db.query(
                ContractOrder.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToNext()){
            do {
                Order order = createOrder(cursor);
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderList;
    }

    public void updateOrder(Order order) throws JSONException {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContractOrder.COLUMN_DATE_CREATION,order.getDateCreation().toString());
        values.put(ContractOrder.COLUMN_ID_CLIENT,order.getClient().getId());
        values.put(ContractOrder.COLUMN_ID_ADM,order.getAdministrator().getUser().getId());
        values.put(ContractOrder.COLUMN_TOTAL,order.getTotal().toString());
        values.put(ContractOrder.COLUMN_DELIVERED,order.getDelivered());
        values.put(ContractOrder.COLUMN_DATE_DELIVERY,order.getDateDelivery().toString());
        values.put(ContractOrder.COLUMN_STATUS, order.getStatus());
        values.put(ContractOrder.COLUMN_ITEMS, convertArrayItemToJSONinSring(order.getItems()));
        String selection = ContractOrder._ID + " = ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        db.update(ContractOrder.TABLE_NAME,values,selection,selectionArgs);
    }

    public Order createOrder(Cursor cursor) throws JSONException {
        int idIndex = cursor.getColumnIndexOrThrow(ContractOrder._ID);
        int dateCreationIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_DATE_CREATION);
        int idClientIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_ID_CLIENT);
        int idAdmIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_ID_ADM);
        int totalIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_TOTAL);
        int deliveredIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_DELIVERED);
        int dateDeliveryIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_DATE_DELIVERY);
        int statusIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_STATUS);
        int itemsIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_ITEMS);
        long id = cursor.getLong(idIndex);
        String dateCreation = cursor.getString(dateCreationIndex);
        long id_client = cursor.getLong(idClientIndex);
        long id_adm = cursor.getLong(idAdmIndex);
        String total = cursor.getString(totalIndex);
        int delivered = cursor.getInt(deliveredIndex);
        String dateDelivery = cursor.getString(dateDeliveryIndex);
        int status = cursor.getInt(statusIndex);
        ArrayList<Item> items = convertJSONStringToArrayItem(cursor.getString(itemsIndex));
        Order order = new Order();
        order.setId(id);
        order.setDateCreation(new Date(dateCreation));
        order.setClient(clientDAO.getClientById(id_client));
        order.setDelivered(delivered);
        order.setStatus(status);
        order.setItems(items);
        User user = new User();
        user.setId(id_adm);
        User searchedUser = userDAO.getUserById(user.getId());
        UserServices userServices = new UserServices(context);
        order.setAdministrator((Administrator) userServices.getUserInDomainType(searchedUser));
        order.setTotal(new BigDecimal(total));
        order.setDateDelivery(new Date(dateDelivery));
        return order;
    }

    public void disableOrder(Order order){
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContractOrder.COLUMN_DATE_CREATION, order.getDateCreation().toString());
        values.put(ContractOrder.COLUMN_ID_CLIENT, order.getClient().getId());
        values.put(ContractOrder.COLUMN_ID_ADM, order.getAdministrator().getUser().getId());
        values.put(ContractOrder.COLUMN_TOTAL, order.getTotal().toString());
        values.put(ContractOrder.COLUMN_DELIVERED, order.getDelivered());
        values.put(ContractOrder.COLUMN_DATE_DELIVERY, order.getDateDelivery().toString());
        values.put(ContractOrder.COLUMN_STATUS, Constants.Status.INACTIVE);
        String selection = ContractOrder._ID+" = ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        db.update(ContractOrder.TABLE_NAME, values, selection, selectionArgs);
    }

    private String convertArrayItemToJSONinSring(ArrayList<Item> items) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.Order.ITEMS, new JSONArray(items));
        return jsonObject.toString();
        //Se der problema, passar o order.getItems() direto
    }

    private ArrayList<Item> convertJSONStringToArrayItem(String string) throws JSONException {
        JSONObject json = new JSONObject(string);
        JSONArray items = json.optJSONArray(Constants.Order.ITEMS);
        ArrayList<Item> newArrayItems = new ArrayList<>((Collection<? extends Item>) items.opt(0));
        return newArrayItems;
    }
}
