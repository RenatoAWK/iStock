package bsi.mpoo.istock.data.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.provider.BaseColumns;

import bsi.mpoo.istock.data.DbHelper;
import bsi.mpoo.istock.domain.User;

public class UserDAO{
    private Context context;
    public UserDAO(Context context){
        this.context = context;
    }

    public void insertUser(User user) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContractUser.COLUMN_NAME, user.getName());
        values.put(ContractUser.COLUMN_EMAIL, user.getEmail());
        values.put(ContractUser.COLUMN_PASSWORD, user.getPassword());
        values.put(ContractUser.COLUMN_TYPE, user.getType());
        values.put(ContractUser.COLUMN_STATUS, user.getStatus());
        values.put(ContractUser.COLUMN_COMPANY, user.getCompany());
        values.put(ContractUser.COLUMN_ADMINISTRATOR, user.getAdministrator());

        long newRowID = db.insert(ContractUser.TABLE_NAME, null, values);

        user.setId(newRowID);
        db.close();
    }

    public User getUserbyEmail(User user) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        User searchedUser = null;

        String[] projection = {
                BaseColumns._ID,
                ContractUser.COLUMN_NAME,
                ContractUser.COLUMN_EMAIL,
                ContractUser.COLUMN_PASSWORD,
                ContractUser.COLUMN_TYPE,
                ContractUser.COLUMN_STATUS,
                ContractUser.COLUMN_COMPANY,
                ContractUser.COLUMN_ADMINISTRATOR
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
            cursor.moveToNext();
            searchedUser = createUser(cursor);
        }

        cursor.close();
        db.close();
        return searchedUser;

    }

    public User getUserById(User user) {

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        User searchedUser = null;

        String[] projection = {
                BaseColumns._ID,
                ContractUser.COLUMN_NAME,
                ContractUser.COLUMN_EMAIL,
                ContractUser.COLUMN_PASSWORD,
                ContractUser.COLUMN_TYPE,
                ContractUser.COLUMN_STATUS,
                ContractUser.COLUMN_COMPANY,
                ContractUser.COLUMN_ADMINISTRATOR
        };

        String selection = ContractUser._ID+" = ?";
        String[] selectionArgs = { String.valueOf(user.getId())};

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
            cursor.moveToNext();
            searchedUser = createUser(cursor);
        }

        cursor.close();
        db.close();
        return searchedUser;

    }

    private User createUser(Cursor cursor){

        int idIndex = cursor.getColumnIndexOrThrow(ContractUser._ID);
        int nameIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_NAME);
        int emailIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_EMAIL);
        int passwordIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_PASSWORD);
        int typeIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_TYPE);
        int statusIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_STATUS);
        int companyIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_COMPANY);
        int administratorIndex = cursor.getColumnIndexOrThrow(ContractUser.COLUMN_ADMINISTRATOR);

        long id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        String email = cursor.getString(emailIndex);
        String password = cursor.getString(passwordIndex);
        int type = cursor.getInt(typeIndex);
        int status = cursor.getInt(statusIndex);
        String company = cursor.getString(companyIndex);
        long administrator = cursor.getLong(administratorIndex);

        User createdUser = new User();
        createdUser.setId(id);
        createdUser.setName(name);
        createdUser.setEmail(email);
        createdUser.setPassword(password);
        createdUser.setType(type);
        createdUser.setStatus(status);
        createdUser.setCompany(company);
        createdUser.setAdministrator(administrator);

        return createdUser;

    }



}
