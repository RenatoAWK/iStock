package bsi.mpoo.istock.data;

public enum Exceptions {
    EMAIL_ALREADY_REGISTERED(1), EMAIL_NOT_REGISTERED(2);

    private int value;

    Exceptions(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
