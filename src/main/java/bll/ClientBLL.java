package bll;

import bll.validators.EmailValidator;
import bll.validators.PhoneValidator;
import dao.ClientDAO;
import model.Client;

/**
 * @author Andrei Rotaru
 * This class contains the logic for client opereations - insert, delete, edit
 */

public class ClientBLL {
    private final ClientDAO clientDAO;
    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;

    public ClientBLL(){
        clientDAO = new ClientDAO();
        emailValidator = new EmailValidator();
        phoneValidator = new PhoneValidator();
    }

    public void insertClient(String name, String email, String phone){
        Client client = new Client(name,email,phone);
        emailValidator.validate(client);
        phoneValidator.validate(client);
        if(!clientDAO.checkIfClientAlreadyRegistered(client)){
            clientDAO.insertEntity(client);
        }
        else{
            System.out.println("Client is registered");
        }
    }

    public void deleteClient(int id){
        Client client = new Client(id);
        clientDAO.deleteEntity(client);
    }

    public void editClient(int clientID, String name, String email, String phone){
        Client client = new Client(clientID,name,email,phone);
        phoneValidator.validate(client);
        emailValidator.validate(client);
        clientDAO.updateEntity(client);
    }
}
