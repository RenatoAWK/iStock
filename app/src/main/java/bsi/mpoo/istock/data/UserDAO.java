package bsi.mpoo.istock.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import bsi.mpoo.istock.domain.User;

public class UserDAO{
    private Context context;
    public UserDAO(Context context){
        this.context = context;
    }

    Database mDbHelper = new Database(context);

    public void addUser(User user) throws Exception {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContractUser.COLUMN_NAME, user.getName());
        values.put(ContractUser.COLUMN_EMAIL, user.getEmail());
        values.put(ContractUser.COLUMN_PASSWORD, user.getPassword());

        long newRowID = db.insert(ContractUser.TABLE_NAME, null, values);

        user.setId(newRowID);
    }



}
