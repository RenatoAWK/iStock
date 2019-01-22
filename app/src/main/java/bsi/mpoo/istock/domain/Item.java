package bsi.mpoo.istock.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Item  implements Parcelable {
    private long id;
    private Product product;
    private BigDecimal price;
    private long quantity;
    private long idOrder;
    private long idAdministrator;
    private int status;

    public Item(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long id_order) {
        this.idOrder = id_order;
    }

    public long getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(long idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item){
            if (getProduct().equals(((Item) obj).getProduct()) ){
                return true;
            }
        }
        return false;
    }

    public BigDecimal getTotalPrice(){
        BigDecimal qtd = new BigDecimal(getQuantity());
        return qtd.multiply(getPrice());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(product);
        dest.writeValue(price);
        dest.writeLong(quantity);
        dest.writeLong(idOrder);
        dest.writeLong(idAdministrator);
        dest.writeInt(status);
    }

    public Item (Parcel parcel){
        this.id = parcel.readLong();
        this.product = (Product) parcel.readValue(Product.class.getClassLoader());
        this.price = (BigDecimal) parcel.readValue(BigDecimal.class.getClassLoader());
        this.quantity = parcel.readLong();
        this.idOrder = parcel.readLong();
        this.idAdministrator = parcel.readLong();
        this.status = parcel.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
