import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class BakerySystem extends JFrame {
    int g=0;

    String fileName = "database.txt";
    File file = new File (fileName);
    TextFileAsDatabase textFileAsDatabase = new TextFileAsDatabase();
    String data="name;product;id;discount;";
    boolean exit = false ;
    private JTextField customerNameField;
    private JComboBox<String> customerTypeBox;
    private JComboBox<String> productBox;

    private JButton addButton;
    private JButton generateInvoiceButton;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private List<Order> orders;

    public BakerySystem() {
        setTitle("Bakery System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        orders = new ArrayList<>();

        // Tworzenie panelu formularza
        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        formPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("Customer Type:"));
        customerTypeBox = new JComboBox<>(new String[]{"Individual", "Company"});
        formPanel.add(customerTypeBox);

        formPanel.add(new JLabel("Product:"));
        productBox = new JComboBox<>(new String[]{"Bread", "Cake", "Cookies"});
        formPanel.add(productBox);

        addButton = new JButton("Add Order");
        formPanel.add(addButton);

        generateInvoiceButton = new JButton("Generate Invoice");
        formPanel.add(generateInvoiceButton);

        add(formPanel, BorderLayout.NORTH);

        // Tworzenie tabeli
        tableModel = new DefaultTableModel(new String[]{"Customer Name", "Customer Type", "Product", "id"}, 0);
        orderTable = new JTable(tableModel);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        List<ArrayList<String>> list = null;
        try {
            list = textFileAsDatabase.readData(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Iterator i = list.iterator();
        while (i.hasNext()) {

            tableModel.addRow(new String[]{list.get(g).get(0), list.get(g).get(1), list.get(g).get(2),list.get(g).get(3)});
            System.out.println(list);
            g++;
            i.next();
        }


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });
    }

    private void addOrder() {


        String customerName = (String) customerNameField.getText();
        String customerType = (String) customerTypeBox.getSelectedItem();
        String product = (String) productBox.getSelectedItem();
        int latestID = g+1;
        Customer customer = new Customer(customerName, customerType);
        Order order = new Order(customer, new Product(product),(latestID));
        orders.add(order);
        tableModel.addRow(new String[]{customer.getName(), customer.getType(), product,String.valueOf(order.getQuantity())});
        data = customer.getName()+";"+customer.getType()+";"+product+";"+String.valueOf(order.getQuantity())+";"+"\n";
        try {
            textFileAsDatabase.writeData(file,data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g++;
    }

    private void generateInvoice() {
        for (Order order : orders) {
            Invoice invoice = new Invoice(order);
            invoice.applyDiscount();
            invoice.calculateIngredients();
            System.out.println(invoice.generate());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BakerySystem().setVisible(true);
        });




    }
}
