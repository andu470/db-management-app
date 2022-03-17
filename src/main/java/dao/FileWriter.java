package dao;

import model.Client;
import model.Order;
import model.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Andrei Rotaru
 *
 * This class implements methods for writing in a file (generating bills)
 */

public class FileWriter {
    public void writeBill(Order order, Product product, Client client){
        String text = createOutputText(order, product, client);
        try{
            File file = new File("bills.txt");
            BufferedWriter wrt = new BufferedWriter(new java.io.FileWriter(file,true));
            wrt.write(text);
            wrt.close();
        }catch(FileNotFoundException e){
            System.out.println("Output file is not valid!");
        } catch (IOException e) {
            System.out.println("Incorrect output format!");
        }
    }

    private String createOutputText(Order order, Product product, Client client){
        String text = "";
        text += "Order: " + order.getId() + "\n";
        text += "Client: " + client.getName() + "\n";
        text += "Product: " + product.getName() + "\n";
        text += "Total: ";
        double sum = order.getQuantity() * product.getPrice();
        text += sum + "\n\n";
        return text;
    }
}
