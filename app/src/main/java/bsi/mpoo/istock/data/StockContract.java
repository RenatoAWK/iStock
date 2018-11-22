package bsi.mpoo.istock.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class StockContract {

    public final static String CONTENT_AUTHORITY = "bsi.mpoo.istock";

    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public final static String PATH_USERS = "users";

    private StockContract(){}

    public static final class UserEntry implements BaseColumns {

        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public final static String TABLE_NAME = "users";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_USER_EMAIL = "email";

        public final static String COLUMN_USER_PASSWORD = "password";

        public final static String COLUMN_USER_COMPANY = "company";

    }

}
