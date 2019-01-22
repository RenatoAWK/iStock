package bsi.mpoo.istock.domain;

public class Temp {
    private static Order order;

    public static Order getOrder() {
        return order;
    }

    public static void setOrder(Order order) {
        Temp.order = order;
    }
}
