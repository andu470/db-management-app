package bll;

import dao.ClientDAO;
import dao.FileWriter;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Order;
import model.Product;

/**
 * @author Andrei Rotaru
 * This class contains the logic for order opereations - insert, delete, edit + modification of the stock for
 * products
 */

public class OrderBLL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private ClientDAO clientDAO;

    public OrderBLL(){
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        clientDAO = new ClientDAO();
    }

    public void insertOrder(int clientID, int productID, int quantity){
        Product productSource = productDAO.getProductById(Integer.toString(productID));
        if(productSource.getStock()-quantity >= 0) {
            Product productToBeUpdated = new Product(productSource.getId(), productSource.getName(),
                    productSource.getPrice(), productSource.getStock() - quantity);
            productDAO.updateEntity(productToBeUpdated);
        }
        else{
            throw new IllegalArgumentException("Quantity is bigger than stock!");
        }

        Order order = new Order(clientID,productID,quantity);
        orderDAO.insertEntity(order);
    }

    public void deleteOrder(int id){
        Order orderSource = orderDAO.getOrderById(Integer.toString(id));
        Product productSource = productDAO.getProductById(Integer.toString(orderSource.getProductID()));
        Product productToBeUpdated = new Product(productSource.getId(),productSource.getName(),
                productSource.getPrice(),productSource.getStock()+orderSource.getQuantity());
        productDAO.updateEntity(productToBeUpdated);

        Order order = new Order(id);
        orderDAO.deleteEntity(order);
    }

    public void editOrder(int id, int clientID, int productID, int quantity){
        Order orderSource = orderDAO.getOrderById(Integer.toString(id));
        Product productSource = productDAO.getProductById(Integer.toString(productID));

        if(productSource.getStock()+orderSource.getQuantity()-quantity >= 0) {
            Product productToBeUpdated = new Product(productSource.getId(), productSource.getName(),
                    productSource.getPrice(), productSource.getStock() + orderSource.getQuantity() - quantity);
            productDAO.updateEntity(productToBeUpdated);
            Order order = new Order(id,clientID,productID,quantity);
            orderDAO.updateEntity(order);
        }
        else{
            throw new IllegalArgumentException("Quantity is bigger than stock!");
        }
    }

    public void generateBill(int id){
        Order orderSource = orderDAO.getOrderById(Integer.toString(id));
        Product productSource = productDAO.getProductById(Integer.toString(orderSource.getProductID()));
        Client clientSource = clientDAO.getClientById(Integer.toString(orderSource.getClientID()));

        FileWriter fileWriter = new FileWriter();
        fileWriter.writeBill(orderSource,productSource,clientSource);
    }

}
