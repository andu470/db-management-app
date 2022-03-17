package bll;

import dao.ProductDAO;
import model.Product;

/**
 * @author Andrei Rotaru
 * This class contains the logic for product opereations - insert, delete, edit
 */

public class ProductBLL {
    private final ProductDAO productDAO;

    public ProductBLL(){
        productDAO = new ProductDAO();
    }

    public void insertProduct(String name, double price, int stock){
        Product product = new Product(name,price,stock);
        if(!productDAO.checkIfProductAlreadyRegistered(product)){
            productDAO.insertEntity(product);
        }
        else{
            System.out.println("Product is registered");
        }
    }

    public void deleteProduct(int id){
        Product product = new Product(id);
        productDAO.deleteEntity(product);
    }

    public void editProduct(int id, String name, double price, int stock){
        Product product = new Product(id,name,price,stock);
        productDAO.updateEntity(product);
    }
}
