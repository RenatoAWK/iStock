package bsi.mpoo.istock.data.order;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.provider.BaseColumns;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import bsi.mpoo.istock.data.DbHelper;
import bsi.mpoo.istock.data.client.ClientDAO;
import bsi.mpoo.istock.data.user.UserDAO;
import bsi.mpoo.istock.domain.Administrator;
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

    public void insertOrder(Order order){
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContractOrder.COLUMN_DATE_CREATION, order.getDateCreation().toString());
        values.put(ContractOrder.COLUMN_ID_CLIENT, order.getClient().getId());
        values.put(ContractOrder.COLUMN_ID_ADM, order.getAdministrator().getUser().getId());
        values.put(ContractOrder.COLUMN_TOTAL, order.getTotal().toString());
        values.put(ContractOrder.COLUMN_DELIVERED, order.isDelivered());
        values.put(ContractOrder.COLUMN_DATE_DELIVERY, order.getDateDelivery().toString());
        values.put(ContractOrder.COLUMN_STATUS, order.getStatus());
        long newRowID = db.insert(ContractOrder.TABLE_NAME, null, values);
        order.setId(newRowID);
        db.close();
    }

    public Order getOrderById(long id){
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
                ContractOrder.COLUMN_STATUS
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

    public List<Order> getOrderByAdm(Administrator administrator){
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
                ContractOrder.COLUMN_STATUS
        };
        List<Order> orderList = new ArrayList<Order>();
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

    public void updateOrder(Order order) {
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContractOrder.COLUMN_DATE_CREATION,order.getDateCreation().toString());
        values.put(ContractOrder.COLUMN_ID_CLIENT,order.getClient().getId());
        values.put(ContractOrder.COLUMN_ID_ADM,order.getAdministrator().getUser().getId());
        values.put(ContractOrder.COLUMN_TOTAL,order.getTotal().toString());
        values.put(ContractOrder.COLUMN_DELIVERED,order.isDelivered());
        values.put(ContractOrder.COLUMN_DATE_DELIVERY,order.getDateDelivery().toString());
        values.put(ContractOrder.COLUMN_STATUS, order.getStatus());
        String selection = ContractOrder._ID + " = ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        db.update(ContractOrder.TABLE_NAME,values,selection,selectionArgs);
    }

    public Order createOrder(Cursor cursor){
        int idIndex = cursor.getColumnIndexOrThrow(ContractOrder._ID);
        int dateCreationIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_DATE_CREATION);
        int idClientIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_ID_CLIENT);
        int idAdmIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_ID_ADM);
        int totalIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_TOTAL);
        int deliveredIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_DELIVERED);
        int dateDeliveryIndex = cursor.getColumnIndexOrThrow(ContractOrder.COLUMN_DATE_DELIVERY);
        long id = cursor.getLong(idIndex);
        String dateCreation = cursor.getString(dateCreationIndex);
        long id_client = cursor.getLong(idClientIndex);
        long id_adm = cursor.getLong(idAdmIndex);
        String total = cursor.getString(totalIndex);
        int delivered = cursor.getInt(deliveredIndex);
        String dateDelivery = cursor.getString(dateDeliveryIndex);
        Order order = new Order();
        order.setId(id);
        order.setDateCreation(new Date(dateCreation));
        order.setClient(clientDAO.getClientById(id_client));
        order.setDelivered(delivered);
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
        values.put(ContractOrder.COLUMN_DELIVERED, order.isDelivered());
        values.put(ContractOrder.COLUMN_DATE_DELIVERY, order.getDateDelivery().toString());
        values.put(ContractOrder.COLUMN_STATUS, Constants.Status.INACTIVE);
        String selection = ContractOrder._ID+" = ?";
        String[] selectionArgs = {String.valueOf(order.getId())};
        db.update(ContractOrder.TABLE_NAME, values, selection, selectionArgs);
    }

}
