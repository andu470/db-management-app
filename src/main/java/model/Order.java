package model;

/**
 * @author Andrei Rotaru
 *
 * This class represents the model for orders, containing all columns from database: id, clientID, productID
 * and quantity.
 */

public class Order {
    private int id;
    private int clientID;
    private int productID;
    private int quantity;

    public Order(int clientID, int productID, int quantity){
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Order(int id, int clientID, int productID, int quantity){
        this.id = id;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Order(int id){
        this.id = id;
        this.quantity = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
