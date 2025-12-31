public class PerishableProduct extends Product {
    private String expiryDate;

    public PerishableProduct(String id, String name, double price, int quantity, String expiryDate){
        super(id, name, price, quantity);
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate(){
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate){
        this.expiryDate = expiryDate;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Son Kullanma Tarihi: " + expiryDate);
    }
}
