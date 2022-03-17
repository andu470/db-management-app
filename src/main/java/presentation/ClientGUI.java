package presentation;

import bll.ClientBLL;
import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * @author Andrei Rotaru
 *
 * This class represents the client frame, provides buttons, labels and text fields to
 * interact with the clients table.
 */

public class ClientGUI extends JFrame {
    private final JButton backButton;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton showButton;

    private final JLabel idLabel;
    private final JLabel nameLabel;
    private final JLabel emailLabel;
    private final JLabel phoneLabel;

    private final JTextField idText;
    private final JTextField nameText;
    private final JTextField emailText;
    private final JTextField phoneText;

    public ClientGUI(HomeGUI homeGUI) {
        this.setTitle("Database Management Application - Clients");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 50, 900, 700);
        this.getContentPane().setLayout(null);

        idLabel = new JLabel("Id: ");
        idLabel.setBounds(50,60,50,30);
        getContentPane().add(idLabel);
        idText = new JTextField();
        idText.setBounds(100,60,150,30);
        getContentPane().add(idText);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(50,100,50,30);
        getContentPane().add(nameLabel);
        nameText = new JTextField();
        nameText.setBounds(100,100,150,30);
        getContentPane().add(nameText);

        emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(50,140,50,30);
        getContentPane().add(emailLabel);
        emailText = new JTextField();
        emailText.setBounds(100,140,150,30);
        getContentPane().add(emailText);

        phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBounds(50,180,50,30);
        getContentPane().add(phoneLabel);
        phoneText = new JTextField();
        phoneText.setBounds(100,180,150,30);
        getContentPane().add(phoneText);

        addButton = new JButton("Add");
        addButton.setBounds(50,250,100,30);
        addButton.addActionListener(e->{
            ClientBLL clientBLL = new ClientBLL();
            clientBLL.insertClient(getNameText().getText(),getEmailText().getText(),
                    getPhoneText().getText());
            JOptionPane.showMessageDialog(this, "Client added successfully");
        });
        getContentPane().add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(50,290,100,30);
        deleteButton.addActionListener(e->{
            ClientBLL clientBLL = new ClientBLL();
            clientBLL.deleteClient(Integer.parseInt(getIdText().getText()));
            JOptionPane.showMessageDialog(this, "Client deleted successfully");
        });
        getContentPane().add(deleteButton);

        editButton = new JButton("Edit");
        editButton.setBounds(50, 330, 100, 30);
        editButton.addActionListener(e->{
            ClientBLL clientBLL = new ClientBLL();
            clientBLL.editClient(Integer.parseInt(getIdText().getText()), getNameText().getText(),
                    getEmailText().getText(), getPhoneText().getText());
            JOptionPane.showMessageDialog(this, "Client edited successfully");

        });
        getContentPane().add(editButton);

        showButton = new JButton("Show");
        showButton.setBounds(50, 370,100,30);
        showButton.addActionListener(e->{
            ClientDAO clientDAO = new ClientDAO();
            ArrayList<Client> clientsList = clientDAO.getClientsList();

            JScrollPane myScrollPane = new JScrollPane();
            myScrollPane.setBounds(280, 50, 600, 350);

            JTable clientTable = new JTable();
            clientTable = this.createTable(clientsList);
            clientTable.setEnabled(true);
            clientTable.setVisible(true);

            myScrollPane.setViewportView(clientTable);
            getContentPane().add(myScrollPane);
        });
        getContentPane().add(showButton);

        backButton = new JButton("Back");
        backButton.setBounds(100, 550, 100, 50);
        backButton.addActionListener(e -> {
            this.setVisible(false);
            homeGUI.setVisible(true);
        });
        getContentPane().add(backButton);
    }

    public static JTable createTable(ArrayList<?> myList) {
        int tableSize = myList.get(0).getClass().getDeclaredFields().length;
        String columns[] = new String[tableSize];
        int columnIndex = 0;
        for (java.lang.reflect.Field currentField : myList.get(0).getClass().getDeclaredFields()) {
            currentField.setAccessible(true);
            try {
                columns[columnIndex] = currentField.getName();
                columnIndex++;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        DefaultTableModel myModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        myModel.setColumnIdentifiers(columns);
        for (Object o : myList) {
            Object[] obj = new Object[tableSize];
            int col = 0;
            for (java.lang.reflect.Field currentField : o.getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    obj[col] = currentField.get(o);
                    col++;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            myModel.addRow(obj);
        }
        JTable myTable = new JTable(myModel);
        return myTable;
    }

    public JTextField getIdText() {
        return idText;
    }

    public JTextField getNameText() {
        return nameText;
    }

    public JTextField getEmailText() {
        return emailText;
    }

    public JTextField getPhoneText() {
        return phoneText;
    }
}
