package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Andrei Rotaru
 *
 * This class implements the CRUD operations for a product, using methods from the superclass.
 */

public class ProductDAO extends AbstractDAO<Product>{

    public boolean checkIfProductAlreadyRegistered(Product product){
        String query = "SELECT COUNT(*) FROM product WHERE name = \"" + product.getName() + "\"";
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

        int count = 0;
        try {
            if(resultSet.next()){
                count = Integer.parseInt(resultSet.getString(1));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        if(count != 0){
            return true;
        }
        return false;
    }

    public ArrayList<Product> getProductsList() {
        String query = "SELECT * FROM "+ "schooldb3" + ".product";

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

        ArrayList<Product> productsList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Product newProduct = new Product(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getDouble("price"),
                        resultSet.getInt("stock"));
                productsList.add(newProduct);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return productsList;
    }

    public Product getProductById(String id){
        String query = "SELECT * FROM "+ "schooldb3" + ".product" + " WHERE id = " + id;

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

        Product product = null;
        try {
            while (resultSet.next()) {
                product = new Product(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getDouble("price"),
                        resultSet.getInt("stock"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return product;
    }
}
