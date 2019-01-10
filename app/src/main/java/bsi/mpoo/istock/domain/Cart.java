package bsi.mpoo.istock.domain;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart {
    private static Cart instance = new Cart();
    private static ArrayList<Product> products = new ArrayList<>();
    private static BigDecimal total;

    public static Cart getInstance() {
        return instance;
    }

    public static void setInstance(Cart instance) {
        Cart.instance = instance;
    }

    public ArrayList<Product> getProducts() {
        return Cart.products;
    }

    public void setProducts(ArrayList<Product> products) {
        Cart.products = products;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        Cart.total = total;
    }

    public void addProduct(Product product){
        Cart.products.add(product);
    }

    public void removeProduct(Product product){
        Cart.products.remove(product);
    }

    public void removeAllProducts(){
        Cart.products.clear();
    }
}
