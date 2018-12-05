package bsi.mpoo.istock.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import bsi.mpoo.istock.domain.Address;
import bsi.mpoo.istock.domain.Client;

public class ClientDAO {
    private Context context;
    public ClientDAO(Context context){
        this.context = context;
    }

    public void insertClient(Client client) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContractClient.COLUMN_NAME, client.getName());
        values.put(ContractClient.COLUMN_PHONE, client.getPhone());
        values.put(ContractClient.COLUMN_ID_ADM, client.getIdAdm());

        AddressDAO addressDAO = new AddressDAO(context);
        addressDAO.insertAddress(client.getAddress());

        values.put(ContractClient.COLUMN_ID_ADDRESS, client.getAddress().getId());

        long newRowID = db.insert(ContractClient.TABLE_NAME, null, values);

        client.setId(newRowID);
        db.close();
    }

    public Client getClientName(String name, long idAdm) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Client searchedClient = null;

        String[] projection = {
                BaseColumns._ID,
                ContractClient.COLUMN_NAME,
                ContractClient.COLUMN_PHONE,
                ContractClient.COLUMN_ID_ADDRESS,
                ContractClient.COLUMN_ID_ADM
        };

        String selection = ContractClient.COLUMN_NAME+" = ?"+" AND "+
                ContractClient.COLUMN_ID_ADM+" =?";
        String[] selectionArgs = { name.trim().toUpperCase(), String.valueOf(idAdm) };

        Cursor cursor = db.query(
                ContractClient.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount()==1){
          searchedClient = createClient(cursor);
        }

        cursor.close();
        db.close();
        return searchedClient;

    }

    private Client createClient(Cursor cursor){

        cursor.moveToNext();

        int idIndex = cursor.getColumnIndexOrThrow(ContractClient._ID);
        int nameIndex = cursor.getColumnIndexOrThrow(ContractClient.COLUMN_NAME);
        int phoneIndex = cursor.getColumnIndexOrThrow(ContractClient.COLUMN_PHONE);
        int idAddressIndex = cursor.getColumnIndexOrThrow(ContractClient.COLUMN_ID_ADDRESS);
        int idAdmIndex = cursor.getColumnIndexOrThrow(ContractClient.COLUMN_ID_ADM);


        long id = cursor.getLong(idIndex);
        String name = cursor.getString(nameIndex);
        String phone = cursor.getString(phoneIndex);
        int idAddress = cursor.getInt(idAddressIndex);
        long idAdm = cursor.getLong(idAdmIndex);

        Client createdClient = new Client();
        createdClient.setId(id);
        createdClient.setName(name);
        createdClient.setPhone(phone);
        createdClient.setIdAdm(idAdm);

        AddressDAO addressDAO = new AddressDAO(context);
        Address searchedAddress = addressDAO.getAddressID(idAddress);

        createdClient.setAddress(searchedAddress);

        return createdClient;

    }



}
