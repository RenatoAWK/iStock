package bsi.mpoo.istock.services;

public enum UserTypes {
    ADMINISTRATOR(1),
    SALESMAN(2),
    PRODUCER(3),
    IS_THE_ADMINISTRATOR(-1);

    private int value;
    UserTypes(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
