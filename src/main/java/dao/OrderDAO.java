package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Andrei Rotaru
 *
 * This class implements the CRUD operations for an order, using methods from the superclass.
 */

public class OrderDAO extends AbstractDAO<Order>{

    public ArrayList<Order> getOrdersList() {
        String query = "SELECT * FROM "+ "schooldb3" + ".order";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Order> ordersList = new ArrayList<Order>();
        try {
            while (resultSet.next()) {
                Order newOrder = new Order(resultSet.getInt("id"),
                        resultSet.getInt("clientID"), resultSet.getInt("productID"),
                        resultSet.getInt("quantity"));
                ordersList.add(newOrder);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return ordersList;
    }

    public Order getOrderById(String id){
        String query = "SELECT * FROM "+ "schooldb3" + ".order" + " WHERE id = " + id;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Order order = null;
        try {
            while (resultSet.next()) {
                order = new Order(resultSet.getInt("id"),
                        resultSet.getInt("clientID"), resultSet.getInt("productID"),
                        resultSet.getInt("quantity"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return order;
    }

}
