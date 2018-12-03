package bsi.mpoo.istock.domain;

import android.os.Parcel;
import android.os.Parcelable;


public class Address implements Parcelable {

    private long id;
    private String street;
    private int number;
    private String neighborhood;
    private String city;
    private String state;

    public Address(){}

    public Address(Parcel parcel){


        this.id = parcel.readLong();
        this.street = parcel.readString();
        this.number = parcel.readInt();
        this.neighborhood = parcel.readString();
        this.city = parcel.readString();
        this.state = parcel.readString();

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(street);
        dest.writeInt(number);
        dest.writeString(neighborhood);
        dest.writeString(city);
        dest.writeString(state);

    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>(){


        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

}

