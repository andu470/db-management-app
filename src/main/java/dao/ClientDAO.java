package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Andrei Rotaru
 *
 * This class implements the CRUD operations for a client, using methods from the superclass.
 */

public class ClientDAO extends AbstractDAO<Client>{

    public boolean checkIfClientAlreadyRegistered(Client client){
        String query = "SELECT COUNT(*) AS counter FROM client WHERE name = \"" + client.getName() + "\""+
                "AND email = \"" + client.getEmail() + "\""+ "AND phone = \"" + client.getPhone() +
                "\"";
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
                count = resultSet.getInt("counter");
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

    public ArrayList<Client> getClientsList() {
        String query = "SELECT * FROM "+ "schooldb3" + ".client";

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

        ArrayList<Client> clientsList = new ArrayList<Client>();
        try {
            while (resultSet.next()) {
                Client newClient = new Client(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("email"),
                        resultSet.getString("phone"));
                clientsList.add(newClient);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return clientsList;
    }

    public Client getClientById(String id){
        String query = "SELECT * FROM "+ "schooldb3" + ".client" + " WHERE id = " + id;

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

        Client client = null;
        try {
            while (resultSet.next()) {
                client = new Client(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("email"),
                        resultSet.getString("phone"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return client;
    }
}
