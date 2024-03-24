import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShoppingCartGUI extends JFrame {

    private List<Product> shoppingCart;
    private JTextArea cartDetailsTextArea;

    public ShoppingCartGUI(List<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Shopping Cart");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Shopping cart details text area
        cartDetailsTextArea = new JTextArea();
        cartDetailsTextArea.setEditable(false);

        // Populate shopping cart details
        populateCartDetails();

        // Layout setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(cartDetailsTextArea), BorderLayout.CENTER);

        add(mainPanel);
    }

    private void populateCartDetails() {
        StringBuilder cartDetails = new StringBuilder();
        double totalPrice = 0;

        if (shoppingCart == null || shoppingCart.isEmpty()) {
            cartDetails.append("No products in the cart.");
        } else {
            for (Product product : shoppingCart) {
                cartDetails.append("Product ID: ").append(product.getProductId()).append("\n");
                cartDetails.append("Product Name: ").append(product.getProductName()).append("\n");
                cartDetails.append("Price: $").append(product.getPrice()).append("\n");
                cartDetails.append("\n");

                totalPrice += product.getPrice();
            }
        }

        // Apply discounts
        double discountedPrice = applyDiscounts(totalPrice);

        cartDetails.append("Total Price: $").append(totalPrice).append("\n");
        cartDetails.append("Discounted Price: $").append(discountedPrice).append("\n");

        cartDetailsTextArea.setText(cartDetails.toString());
    }

    private double applyDiscounts(double totalPrice) {
        // Example: 20% discount when buying at least three products of the same category
        //         10% discount for the very first purchase

        int totalProducts = shoppingCart.size();
        if (totalProducts >= 3) {
            // Apply 20% discount for at least three products
            return totalPrice * 0.8;
        } else {
            // Apply 10% discount for the very first purchase
            return totalPrice * 0.9;
        }
    }

    public static void main(String[] args) {

        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.loadProductsFromFile();  // Load products from file
        manager.openGUI();  // Open the main shopping GUI
        List<Product> shoppingCart = manager.getShoppingCartList();  // Assuming you have a getter for shopping cart

        // Create the ShoppingCartGUI and display it
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart);
        SwingUtilities.invokeLater(() -> shoppingCartGUI.setVisible(true));
    }
}
