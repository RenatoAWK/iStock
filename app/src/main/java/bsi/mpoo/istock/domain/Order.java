package bsi.mpoo.istock.domain;
import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Parcelable{

    private long id;
    private Date dateCreation;
    private Client client;
    private Administrator administrator;
    private BigDecimal total;
    private boolean delivered;
    private Date dateDelivery;

    public Order() {}

    public Order(Parcel parcel) {
        this.id = parcel.readLong();
        this.dateCreation = (Date)parcel.readValue(Date.class.getClassLoader());
        this.client = (Client) parcel.readParcelable(Client.class.getClassLoader());
        this.administrator = (Administrator) parcel.readParcelable(Administrator.class.getClassLoader());
        this.total = (BigDecimal)parcel.readValue(BigDecimal.class.getClassLoader());
        this.delivered = parcel.readByte() != 0;
        this.dateDelivery = (Date)parcel.readValue(Date.class.getClassLoader());
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
    public boolean isDelivered() {
        return delivered;
    }
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
    public Date getDateDelivery() {
        return dateDelivery;
    }
    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeValue(dateCreation);
        parcel.writeParcelable(client, i);
        parcel.writeParcelable(administrator, i);
        parcel.writeString(total.toString());
        parcel.writeByte((byte) (delivered ? 1 : 0));
        parcel.writeValue(dateDelivery);
    }
}
