package bsi.mpoo.istock.services;

public enum ExceptionsEnum {
    EMAIL_ALREADY_REGISTERED(1), CLIENT_ALREADY_REGISTERED(2);

    private int value;

    ExceptionsEnum(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
