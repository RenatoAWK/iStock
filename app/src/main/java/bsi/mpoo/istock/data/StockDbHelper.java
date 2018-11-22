package bsi.mpoo.istock.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import bsi.mpoo.istock.data.StockContract.UserEntry;


public class StockDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stock.db";

    private static final int DATABASE_VERSION = 1;

    public StockDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + UserEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
                + UserEntry.COLUMN_USER_COMPANY +  " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
