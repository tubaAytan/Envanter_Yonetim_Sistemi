public class Product implements Storable {
    private String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void displayInfo(){
        System.out.println("ID: " + id + " İsim: " + name + " Fiyat: " + price + " Stok: " + quantity);
    }
    @Override
    public void updateStock(int amount){
        this.quantity += amount;
        System.out.println("Stok güncellendi. Yeni miktar: " + this.quantity);
    }

    @Override
    public boolean isInStock(){
        return this.quantity > 0;
    }
}
