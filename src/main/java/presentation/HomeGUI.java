package presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Andrei Rotaru
 *
 * This class represents the main frame which is the "mother" of all the other
 * frames in this application. It provides access to the other frames.
 */

public class HomeGUI extends JFrame {
    private final JLabel titleLabel;

    private final JButton clientButton;
    private final JButton productButton;
    private final JButton orderButton;

    public HomeGUI(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 50, 700, 650);
        this.getContentPane().setLayout(null);
        this.setTitle("Database Management Application - Home");

        titleLabel = new JLabel("Database Management Application");
        titleLabel.setBounds(250,100,200,50);
        getContentPane().add(titleLabel);


        clientButton = new JButton("Clients Table");
        clientButton.setBounds(100,250,200,50);
        getContentPane().add(clientButton);

        productButton = new JButton("Products Table");
        productButton.setBounds(400,250,200,50);
        getContentPane().add(productButton);

        orderButton = new JButton("Orders Table");
        orderButton.setBounds(250,350,200,50);
        getContentPane().add(orderButton);
    }

    public void addClientButtonActionListener(ActionListener actionListener) {
        clientButton.addActionListener(actionListener);
    }

    public void addProductButtonActionListener(ActionListener actionListener) {
        productButton.addActionListener(actionListener);
    }

    public void addOrderButtonActionListener(ActionListener actionListener) {
        orderButton.addActionListener(actionListener);
    }
}
