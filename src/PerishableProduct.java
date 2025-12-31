public class PerishableProduct extends Product {
    private String expiryDate;

    public PerishableProduct(String id, String name, double price, int quantity, String expiryDate){
        super(id, name, price, quantity);
        this.expiryDate = expiryDate;
    }
}
