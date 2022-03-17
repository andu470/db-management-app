package presentation;

import bll.OrderBLL;
import dao.OrderDAO;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * @author Andrei Rotaru
 *
 * This class represents the order frame, provides buttons, labels and text fields to
 * interact with the order table.
 */

public class OrderGUI extends JFrame {
    private final JButton backButton;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton showButton;
    private final JButton billButton;

    private final JLabel idLabel;
    private final JLabel clientLabel;
    private final JLabel productLabel;
    private final JLabel quantityLabel;

    private final JTextField idText;
    private final JTextField clientText;
    private final JTextField productText;
    private final JTextField quantityText;

    public OrderGUI(HomeGUI homeGUI){
        this.setTitle("Database Management Application - Orders");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 50, 900, 700);
        this.getContentPane().setLayout(null);

        idLabel = new JLabel("Id: ");
        idLabel.setBounds(50,60,70,30);
        getContentPane().add(idLabel);
        idText = new JTextField();
        idText.setBounds(120,60,150,30);
        getContentPane().add(idText);

        clientLabel = new JLabel("Client id: ");
        clientLabel.setBounds(50,100,70,30);
        getContentPane().add(clientLabel);
        clientText = new JTextField();
        clientText.setBounds(120,100,150,30);
        getContentPane().add(clientText);

        productLabel = new JLabel("Product id: ");
        productLabel.setBounds(50,140,70,30);
        getContentPane().add(productLabel);
        productText = new JTextField();
        productText.setBounds(120,140,150,30);
        getContentPane().add(productText);

        quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setBounds(50,180,70,30);
        getContentPane().add(quantityLabel);
        quantityText = new JTextField();
        quantityText.setBounds(120,180,150,30);
        getContentPane().add(quantityText);

        addButton = new JButton("Add");
        addButton.setBounds(50,250,150,30);
        addButton.addActionListener(e->{
            OrderBLL orderBLL = new OrderBLL();
            if(Integer.parseInt(getQuantityText().getText()) > 0) {
                orderBLL.insertOrder(Integer.parseInt(getClientText().getText()),
                        Integer.parseInt(getProductText().getText()), Integer.parseInt(getQuantityText().getText()));
                JOptionPane.showMessageDialog(this, "Order added successfully");
            }
            else{
                JOptionPane.showMessageDialog(this, "Quantity must be > 0");
            }
        });
        getContentPane().add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(50,290,150,30);
        deleteButton.addActionListener(e->{
            OrderBLL orderBLL = new OrderBLL();
            orderBLL.deleteOrder(Integer.parseInt(getIdText().getText()));
            JOptionPane.showMessageDialog(this, "Order deleted successfully");
        });
        getContentPane().add(deleteButton);

        editButton = new JButton("Edit");
        editButton.setBounds(50, 330, 150, 30);
        editButton.addActionListener(e->{
            OrderBLL orderBLL = new OrderBLL();
            if(Integer.parseInt(getQuantityText().getText())>0) {
                orderBLL.editOrder(Integer.parseInt(getIdText().getText()),
                        Integer.parseInt(getClientText().getText()), Integer.parseInt(getProductText().getText()),
                        Integer.parseInt(getQuantityText().getText()));
                JOptionPane.showMessageDialog(this, "Order edited successfully");
            }
            else{
                JOptionPane.showMessageDialog(this, "Quantity must be > 0");
            }
        });
        getContentPane().add(editButton);

        showButton = new JButton("Show");
        showButton.setBounds(50, 370,150,30);
        showButton.addActionListener(e->{
            OrderDAO orderDAO = new OrderDAO();
            ArrayList<Order> ordersList = orderDAO.getOrdersList();

            JScrollPane myScrollPane = new JScrollPane();
            myScrollPane.setBounds(280, 50, 600, 350);

            JTable orderTable = new JTable();
            orderTable = this.createTable(ordersList);
            orderTable.setEnabled(true);
            orderTable.setVisible(true);

            myScrollPane.setViewportView(orderTable);
            getContentPane().add(myScrollPane);
        });
        getContentPane().add(showButton);

        billButton = new JButton("Generate Bill");
        billButton.setBounds(50,410,150,30);
        billButton.addActionListener(e->{
            OrderBLL orderBLL = new OrderBLL();
            orderBLL.generateBill(Integer.parseInt(getIdText().getText()));
            JOptionPane.showMessageDialog(this, "Bill generated successfully");
        });
        getContentPane().add(billButton);

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

    public JTextField getClientText() {
        return clientText;
    }

    public JTextField getProductText() {
        return productText;
    }

    public JTextField getQuantityText() {
        return quantityText;
    }
}
