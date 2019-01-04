package bsi.mpoo.istock.services;

import android.content.Context;

import java.util.ArrayList;

import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.data.product.ProductDAO;
import bsi.mpoo.istock.domain.Administrator;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.User;

public class ProductServices {

    private ProductDAO productDAO;

    public ProductServices(Context context){
        this.productDAO = new ProductDAO(context);
    }

    public boolean isProductRegistered(String name, Administrator administrator){
        Product searchedProduct = productDAO.getProductByName(name, administrator);
        return searchedProduct != null;
    }

    public void registerProduct(Product product, Administrator administrator) throws Exception {
        if (isProductRegistered(product.getName(), administrator)){
            throw new Exceptions.ProductAlreadyRegistered();
        } else {
            productDAO.insertProduct(product);
        }
    }

    public void updateProduct(Product product) throws Exception{
        try {
            productDAO.updateProduct(product);
        } catch (Exception error){
            throw new Exceptions.ProductNotRegistered();
        }
    }

    public void disableProduct(Product product, Administrator administrator) throws Exception{

        if (isProductRegistered(product.getName(), administrator)){
            productDAO.disableProduct(product);
        } else {
            throw  new Exceptions.ProductNotRegistered();
        }
    }

    public ArrayList<Product> getAcitiveProductsAsc(Administrator administrator){
        return (ArrayList<Product>) productDAO.getActiveProductsByAdmId(administrator,Contract.ASC);
    }

    public ArrayList<Product> getAcitiveProductsDesc(Administrator administrator){
        return (ArrayList<Product>) productDAO.getActiveProductsByAdmId(administrator ,Contract.DESC);
    }
}
