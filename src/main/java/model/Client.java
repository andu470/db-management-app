package model;

/**
 * @author Andrei Rotaru
 *
 * This class represents the model for clients, containing all columns from database: id, name, email
 * and phone.
 */

public class Client {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Client(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Client(int id){
        this.id = id;
        this.name = "";
        this.email = "";
        this.phone = "";
    }

    public Client(int id, String name, String email, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
