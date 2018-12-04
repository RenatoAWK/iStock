package bsi.mpoo.istock.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import bsi.mpoo.istock.domain.Address;
import bsi.mpoo.istock.domain.Client;

public class AddressDAO {
    private Context context;
    public AddressDAO(Context context){
        this.context = context;
    }

    public void insertAddress(Address address) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContractAddress.COLUMN_STREET, address.getStreet());
        values.put(ContractAddress.COLUMN_NUMBER, address.getNumber());
        values.put(ContractAddress.COLUMN_DISTRICT, address.getDistrict());
        values.put(ContractAddress.COLUMN_CITY, address.getCity());
        values.put(ContractAddress.COLUMN_STATE, address.getState());


        long newRowID = db.insert(ContractAddress.TABLE_NAME, null, values);

        address.setId(newRowID);
        db.close();
    }

    public Address getAddressID(int id) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Address searchedAddress = null;

        String[] projection = {
                BaseColumns._ID,
                ContractAddress.COLUMN_STREET,
                ContractAddress.COLUMN_NUMBER,
                ContractAddress.COLUMN_DISTRICT,
                ContractAddress.COLUMN_CITY,
                ContractAddress.COLUMN_STATE,
        };

        String selection = ContractAddress._ID+" = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                ContractAddress.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount()==1){
          searchedAddress = createAddress(cursor);
        }

        cursor.close();
        db.close();
        return searchedAddress;

    }

    private Address createAddress(Cursor cursor){

        cursor.moveToNext();

        int idIndex = cursor.getColumnIndexOrThrow(ContractAddress._ID);
        int streetIndex = cursor.getColumnIndexOrThrow(ContractAddress.COLUMN_STREET);
        int numberIndex = cursor.getColumnIndexOrThrow(ContractAddress.COLUMN_NUMBER);
        int districtIndex = cursor.getColumnIndexOrThrow(ContractAddress.COLUMN_DISTRICT);
        int cityIndex = cursor.getColumnIndexOrThrow(ContractAddress.COLUMN_CITY);
        int stateIndex = cursor.getColumnIndexOrThrow(ContractAddress.COLUMN_STATE);

        long id = cursor.getInt(idIndex);
        String street = cursor.getString(streetIndex);
        int number = cursor.getInt(numberIndex);
        String district = cursor.getString(districtIndex);
        String city = cursor.getString(cityIndex);
        String state = cursor.getString(stateIndex);

        Address createdAddress = new Address();
        createdAddress.setId(id);
        createdAddress.setStreet(street);
        createdAddress.setNumber(number);
        createdAddress.setDistrict(district);
        createdAddress.setCity(city);
        createdAddress.setState(state);

        return createdAddress;

    }



}
