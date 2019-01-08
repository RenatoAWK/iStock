package bsi.mpoo.istock.data.order;
import android.provider.BaseColumns;
import bsi.mpoo.istock.data.client.ContractClient;
import bsi.mpoo.istock.data.user.ContractUser;

public class ContractOrder implements BaseColumns{

    private ContractOrder(){}

    public static final String TABLE_NAME = "order";
    public static final String COLUMN_DATE_CREATION = "date_creation";
    public static final String COLUMN_ID_CLIENT = "id_client";
    public static final String COLUMN_ID_ADM = "id_administrador";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_DELIVERED = "delivered";
    public static final String COLUMN_DATE_DELIVERY = "date_delivery";
    public static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + ContractOrder.TABLE_NAME + " ("+
                    ContractOrder._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    ContractOrder.COLUMN_DATE_CREATION + " TEXT,"+
                    ContractOrder.COLUMN_ID_CLIENT + " INTEGER,"+
                    ContractOrder.COLUMN_ID_ADM + " INTEGER,"+
                    ContractOrder.COLUMN_TOTAL + " TEXT,"+
                    ContractOrder.COLUMN_DELIVERED + " INTEGER,"+
                    ContractOrder.COLUMN_DATE_DELIVERY + " TEXT,"+
                    "FOREIGN KEY("+ ContractOrder.COLUMN_ID_CLIENT +") REFERENCES "+
                    ContractClient.TABLE_NAME+" ("+ContractUser._ID+")"+
                    "FOREIGN KEY("+ContractClient.COLUMN_ID_ADM +") REFERENCES "+
                    ContractUser.TABLE_NAME+" ("+ContractUser._ID+")"+
                    ")";

}
