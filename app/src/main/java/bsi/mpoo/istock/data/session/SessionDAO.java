package bsi.mpoo.istock.data.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.data.DbHelper;
import bsi.mpoo.istock.data.user.ContractUser;
import bsi.mpoo.istock.data.user.UserDAO;
import bsi.mpoo.istock.domain.Session;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Constants;

public class SessionDAO {
    private Context context;
    public SessionDAO(Context context){
        this.context = context;
    }

    public void update(Session session){

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContractSession._ID, Constants.Session.POSITION_USER);
        values.put(ContractSession.ID_USER, session.getUser().getId());
        values.put(ContractSession.COLUMN_REMEMBER, session.getRemember());
        db.insert(ContractSession.TABLE_NAME, null, values);
        db.close();
    }

    public Session get(){
        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Session session = null;
        String[] projection = {
                BaseColumns._ID,
                ContractSession.ID_USER,
                ContractSession.COLUMN_REMEMBER
        };
        Cursor cursor = db.query(
                ContractSession.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            session = createSession(cursor);
        }
        cursor.close();
        db.close();
        return session;
    }

    public void clearSession(){

        DbHelper mDbHelper = new DbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(ContractSession.TABLE_NAME,
                null, null,
                null, null,
                null, null);

        if (cursor.moveToFirst()){
            String rowId = cursor.getString(cursor.getColumnIndex(ContractSession._ID));
            String selection = ContractSession._ID + " = ?";
            String[] selectionArgs = { rowId };
            db.delete(ContractSession.TABLE_NAME,
                    selection,
                    selectionArgs
            );
            db.close();
        }





    }

    private Session createSession(Cursor cursor){
        int idUserIndex = cursor.getColumnIndexOrThrow(ContractSession.ID_USER);
        int rememberIndex = cursor.getColumnIndexOrThrow(ContractSession.COLUMN_REMEMBER);
        Session searchedSession = new Session();
        long idUser = cursor.getLong(idUserIndex);
        int remember = cursor.getInt(rememberIndex);
        UserDAO userDAO = new UserDAO(context);
        User user = new User();
        user.setId(idUser);
        User searchedUser = userDAO.getUserById(user);
        searchedSession.setId_user(idUser);
        searchedSession.setRemember(remember);
        searchedSession.setUser(searchedUser);
        return searchedSession;
    }


}
