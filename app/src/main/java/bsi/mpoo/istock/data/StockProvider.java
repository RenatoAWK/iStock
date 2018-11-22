package bsi.mpoo.istock.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import bsi.mpoo.istock.data.StockContract.UserEntry;

public class StockProvider extends ContentProvider {

    private StockDbHelper mDbHelper;

    private static final int USERS = 100;

    private static final int USER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(StockContract.CONTENT_AUTHORITY, StockContract.PATH_USERS, USERS);
        sUriMatcher.addURI(StockContract.CONTENT_AUTHORITY, StockContract.PATH_USERS + "/#", USER_ID);
    }

    @Override
    public boolean onCreate() {

        mDbHelper = new StockDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case USERS:

                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case USER_ID:

                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case USERS:
                return UserEntry.CONTENT_LIST_TYPE;
            case USER_ID:
                return UserEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case USERS:
                return insertUser(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertUser(@NonNull Uri uri, @NonNull ContentValues values){

        String email = values.getAsString(UserEntry.COLUMN_USER_EMAIL);
        if(email == null){
            throw new IllegalArgumentException("User requires a login.");
        }

        String password = values.getAsString(UserEntry.COLUMN_USER_PASSWORD);
        if(password == null){
            throw new IllegalArgumentException("User requires a password.");
        }

        String company = values.getAsString(UserEntry.COLUMN_USER_COMPANY);
        if(company == null){
            throw new IllegalArgumentException("User requires a company.");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(UserEntry.TABLE_NAME, null, values);
        if(id == -1){
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match){
            case USERS:
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ID:

                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch(match){
            case USERS:
                return updateUser(uri, contentValues, selection, selectionArgs);
            case USER_ID:

                selection = UserEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUser(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        if (values.containsKey(UserEntry.COLUMN_USER_EMAIL)) {
            String email = values.getAsString(UserEntry.COLUMN_USER_EMAIL);
            if(email == null){
                throw new IllegalArgumentException("User requires a login.");
            }
        }

        if (values.containsKey(UserEntry.COLUMN_USER_PASSWORD)) {
            String password = values.getAsString(UserEntry.COLUMN_USER_PASSWORD);
            if(password == null){
                throw new IllegalArgumentException("User requires a password.");
            }
        }

        if (values.containsKey(UserEntry.COLUMN_USER_COMPANY)) {
            String company = values.getAsString(UserEntry.COLUMN_USER_COMPANY);
            if(company == null){
                throw new IllegalArgumentException("User requires a company.");
            }
        }

        if(values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(UserEntry.TABLE_NAME, values, selection, selectionArgs);

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

}
