package bsi.mpoo.istock.services;

public enum AccountStatus {
    ACTIVE(1),
    INACTIVE(2),
    FIRST_ACCESS(3);

    private int value;

    AccountStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
