import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Order order;
    private List<Ingredient> ingredients;
    private double discount;

    public Invoice(Order order) {
        this.order = order;
        this.ingredients = new ArrayList<>();
        this.discount = 0.0;
    }

    public void applyDiscount() {
        Discount discountCalculator = new Discount();
        this.discount = discountCalculator.calculate(order.getCustomer().getType(), order.getQuantity());
    }

    public void calculateIngredients() {
        // Dummy data for ingredients calculation
        if (order.getProduct().getName().equalsIgnoreCase("Bread")) {
            ingredients.add(new Ingredient("Flour", order.getQuantity() * 2));
            ingredients.add(new Ingredient("Water", order.getQuantity()));
        } else if (order.getProduct().getName().equalsIgnoreCase("Cake")) {
            ingredients.add(new Ingredient("Flour", order.getQuantity() * 3));
            ingredients.add(new Ingredient("Sugar", order.getQuantity() * 2));
        } else if (order.getProduct().getName().equalsIgnoreCase("Cookies")) {
            ingredients.add(new Ingredient("Flour", order.getQuantity() * 1));
            ingredients.add(new Ingredient("Butter", order.getQuantity()));
        }
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice for ").append(order.getCustomer().getName()).append("\n");
        sb.append("Product: ").append(order.getProduct().getName()).append("\n");
        sb.append("Quantity: ").append(order.getQuantity()).append("\n");
        sb.append("Discount: ").append(discount).append("\n");
        sb.append("Ingredients: \n");
        for (Ingredient ingredient : ingredients) {
            sb.append("- ").append(ingredient.getName()).append(": ").append(ingredient.getQuantity()).append("\n");
        }
        return sb.toString();
    }
}
