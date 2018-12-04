package bsi.mpoo.istock.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractUser.SQL_CREATE_TABLE_USER);
        db.execSQL(ContractClient.SQL_CREATE_TABLE_CLIENT);
        db.execSQL(ContractAddress.SQL_CREATE_TABLE_ADDRESS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(ContractUser.SQL_DELETE_USERS);
        db.execSQL(ContractClient.SQL_DELETE_CLIENTS);
        db.execSQL(ContractAddress.SQL_DELETE_ADDRESS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
