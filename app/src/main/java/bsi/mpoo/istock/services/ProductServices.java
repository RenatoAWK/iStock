package bsi.mpoo.istock.services;

import android.content.Context;

import java.util.ArrayList;

import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.data.product.ProductDAO;
import bsi.mpoo.istock.domain.Product;
import bsi.mpoo.istock.domain.User;

public class ProductServices {

    private ProductDAO productDAO;

    public ProductServices(Context context){
        this.productDAO = new ProductDAO(context);
    }

    public boolean isProductRegistered(Product product){

        Product searchedProduct = productDAO.getProductByName(product);

        if (searchedProduct == null){
            return false;
        }
        return true;
    }

    public void registerProduct(Product product) throws Exception {

        if (isProductRegistered(product)){
            throw new Exceptions.ProductAlreadyRegistered();
        }
        else {
            productDAO.insertProduct(product);
        }

    }

    public void updateProduct(Product product) throws Exception{
        try {
            productDAO.updateProduct(product);
        }catch (Exception error){
            throw new Exceptions.ProductNotRegistered();
        }




    }

    public void disableProduct(Product product) throws Exception{

        if (isProductRegistered(product)){
            productDAO.disableProduct(product);
        }
        else {
            throw  new Exceptions.ProductNotRegistered();
        }


    }

    public ArrayList<Product> getAcitiveProductsAsc(User user){
        return (ArrayList<Product>) productDAO.getActiveProductsByAdmId(user,Contract.ASC);

    }

    public ArrayList<Product> getAcitiveProductsDesc(User user){
        return (ArrayList<Product>) productDAO.getActiveProductsByAdmId(user,Contract.DESC);

    }


}
