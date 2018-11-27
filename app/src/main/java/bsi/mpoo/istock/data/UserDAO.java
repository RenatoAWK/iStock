package bsi.mpoo.istock.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.provider.BaseColumns;

import bsi.mpoo.istock.domain.User;

public class UserDAO{
    private Context context;
    public UserDAO(Context context){
        this.context = context;
    }

    Database mDbHelper = new Database(context);

    public void addUser(User user) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContractUser.COLUMN_NAME, user.getName());
        values.put(ContractUser.COLUMN_EMAIL, user.getEmail());
        values.put(ContractUser.COLUMN_PASSWORD, user.getPassword());
        values.put(ContractUser.COLUMN_TYPE, user.getType());
        values.put(ContractUser.COLUMN_STATUS, user.getStatus());
        values.put(ContractUser.COLUMN_COMPANY, user.getCompany());

        long newRowID = db.insert(ContractUser.TABLE_NAME, null, values);

        user.setId(newRowID);
    }

    public User searchUser(User user) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        User searchedUser = null;

        String[] projection = {
                BaseColumns._ID,
                ContractUser.COLUMN_NAME,
                ContractUser.COLUMN_EMAIL,
                ContractUser.COLUMN_PASSWORD,
                ContractUser.COLUMN_TYPE,
                ContractUser.COLUMN_STATUS,
                ContractUser.COLUMN_COMPANY
        };

        String selection = ContractUser.COLUMN_EMAIL+" = ?";
        String[] selectionArgs = { user.getEmail().trim().toUpperCase()};

        Cursor cursor = db.query(
                ContractUser.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount()==1){
          searchedUser = createUser(cursor);
        }

        return searchedUser;




    }

    public User createUser(Cursor cursor){

        int idIndex = cursor.getColumnIndexOrThrow(ContractUser._ID);
        int nameIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_NAME);
        int emailIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_EMAIL);
        int passwordIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_PASSWORD);
        int typeIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_TYPE);
        int statusIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_STATUS);
        int companyIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_COMPANY);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(nameIndex);
        String email = cursor.getString(emailIndex);
        String password = cursor.getString(passwordIndex);
        String type = cursor.getString(typeIndex);
        String status = cursor.getColumnName(statusIndex);
        String company = cursor.getColumnName(companyIndex);

        User searchedUser = new User();
        searchedUser.setId(id);
        searchedUser.setName(name);
        searchedUser.setEmail(email);
        searchedUser.setPassword(password);
        searchedUser.setType(type);
        searchedUser.setStatus(status);
        searchedUser.setCompany(company);

        return searchedUser;
    }



}
