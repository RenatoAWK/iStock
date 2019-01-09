package bsi.mpoo.istock.domain;
import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Parcelable{

    private long id;
    private Date dateCreation;
    private Client client;
    private Administrator administrator;
    private BigDecimal total;
    private int delivered;
    private Date dateDelivery;
    private int status;
    private ArrayList<Item> items;

    public Order() {}

    public Order(Parcel parcel) {
        this.id = parcel.readLong();
        this.dateCreation = (Date)parcel.readValue(Date.class.getClassLoader());
        this.client = parcel.readParcelable(Client.class.getClassLoader());
        this.administrator = parcel.readParcelable(Administrator.class.getClassLoader());
        this.total = (BigDecimal)parcel.readValue(BigDecimal.class.getClassLoader());
        this.delivered = parcel.readInt();
        this.dateDelivery = (Date)parcel.readValue(Date.class.getClassLoader());
        this.status = parcel.readInt();
        this.items = parcel.readArrayList(Item.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Administrator getAdministrator() {
        return administrator;
    }
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }
    public int getDelivered() {
        return delivered;
    }
    public Date getDateDelivery() {
        return dateDelivery;
    }
    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }
    public int getStatus(){
        return this.status;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeValue(dateCreation);
        parcel.writeValue(client);
        parcel.writeValue(administrator);
        parcel.writeString(total.toString());
        parcel.writeInt(delivered);
        parcel.writeValue(dateDelivery);
        parcel.writeInt(status);
        parcel.writeArray(new ArrayList[]{items});
    }
}
