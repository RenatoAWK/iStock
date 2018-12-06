package bsi.mpoo.istock.domain;

import android.os.Parcel;
import android.os.Parcelable;


public class Client implements Parcelable {


    private long id;
    private String name;
    private String phone;
    private Address address;
    private long idAdm;
    private int status;

    public Client(){}

    public Client(Parcel parcel){

        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.phone = parcel.readString();
        this.address = (Address)parcel.readValue(Address.class.getClassLoader());
        this.idAdm = parcel.readLong();


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(long idAdm) {
        this.idAdm = idAdm;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeValue(address);
        dest.writeLong(idAdm);
        dest.writeInt(status);


    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>(){


        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

}
