package bsi.mpoo.istock.data;

public class ContractUser {

    private ContractUser(){}

    public static final String _ID = "id";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_TYPE = "type";

    public static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE "+ContractUser.TABLE_NAME+" ("+
                    ContractUser._ID + " NOT NULL INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    ContractUser.COLUMN_NAME + " NOT NULL TEXT,"+
                    ContractUser.COLUMN_EMAIL + " NOT NULL TEXT,"+
                    ContractUser.COLUMN_PASSWORD + " NOT NULL TEXT,"+
                    ContractUser.COLUMN_TYPE + " NOT NULL TEXT)";

    public static  final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS "+ ContractUser.TABLE_NAME;


}
