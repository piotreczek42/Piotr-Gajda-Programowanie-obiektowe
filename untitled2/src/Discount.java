public class Discount {
    public double calculate(String customerType, int quantity) {
        double discount = 0.0;
        if (customerType.equalsIgnoreCase("Company")) {
            discount = 0.15;
        } else if (quantity > 10) {
            discount = 0.10;
        }
        return discount;
    }
}