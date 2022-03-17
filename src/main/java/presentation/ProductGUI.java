package presentation;

import bll.ProductBLL;
import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * @author Andrei Rotaru
 *
 * This class represents the product frame, provides buttons, labels and text fields to
 * interact with the product table.
 */

public class ProductGUI extends JFrame {
    private final JButton backButton;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton showButton;

    private final JLabel idLabel;
    private final JLabel nameLabel;
    private final JLabel priceLabel;
    private final JLabel stockLabel;

    private final JTextField idText;
    private final JTextField nameText;
    private final JTextField priceText;
    private final JTextField stockText;

    public ProductGUI(HomeGUI homeGUI){
        this.setTitle("Database Management Application - Products");
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

        priceLabel = new JLabel("Price: ");
        priceLabel.setBounds(50,140,50,30);
        getContentPane().add(priceLabel);
        priceText = new JTextField();
        priceText.setBounds(100,140,150,30);
        getContentPane().add(priceText);

        stockLabel = new JLabel("Stock: ");
        stockLabel.setBounds(50,180,50,30);
        getContentPane().add(stockLabel);
        stockText = new JTextField();
        stockText.setBounds(100,180,150,30);
        getContentPane().add(stockText);

        addButton = new JButton("Add");
        addButton.setBounds(50,250,100,30);
        addButton.addActionListener(e->{
            ProductBLL productBLL = new ProductBLL();
            if(Double.parseDouble(getPriceText().getText())>0 && Integer.parseInt(getStockText().getText())>0) {
                productBLL.insertProduct(getNameText().getText(), Double.parseDouble(getPriceText().getText()),
                        Integer.parseInt(getStockText().getText()));
                JOptionPane.showMessageDialog(this, "Product added successfully");
            }
            else{
                JOptionPane.showMessageDialog(this, "Price and stock must be > 0");
            }
        });
        getContentPane().add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(50,290,100,30);
        deleteButton.addActionListener(e->{
            ProductBLL productBLL = new ProductBLL();
            productBLL.deleteProduct(Integer.parseInt(getIdText().getText()));
            JOptionPane.showMessageDialog(this, "Product deleted successfully");
        });
        getContentPane().add(deleteButton);

        editButton = new JButton("Edit");
        editButton.setBounds(50, 330, 100, 30);
        editButton.addActionListener(e->{
            ProductBLL productBLL = new ProductBLL();
            if(Double.parseDouble(getPriceText().getText())>0 && Integer.parseInt(getStockText().getText())>0) {
                productBLL.editProduct(Integer.parseInt(getIdText().getText()), getNameText().getText(),
                        Double.parseDouble(getPriceText().getText()), Integer.parseInt(getStockText().getText()));
                JOptionPane.showMessageDialog(this, "Product edited successfully");
            }
            else{
                JOptionPane.showMessageDialog(this, "Price and stock must be > 0");
            }
        });
        getContentPane().add(editButton);

        showButton = new JButton("Show");
        showButton.setBounds(50, 370,100,30);
        showButton.addActionListener(e->{
            ProductDAO productDAO = new ProductDAO();
            ArrayList<Product> productsList = productDAO.getProductsList();

            JScrollPane myScrollPane = new JScrollPane();
            myScrollPane.setBounds(280, 50, 600, 350);

            JTable productTable = new JTable();
            productTable = this.createTable(productsList);
            productTable.setEnabled(true);
            productTable.setVisible(true);

            myScrollPane.setViewportView(productTable);
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

    public JTextField getPriceText() {
        return priceText;
    }

    public JTextField getStockText() {
        return stockText;
    }
}
