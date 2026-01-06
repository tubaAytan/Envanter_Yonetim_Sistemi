public class Product implements Storable { //Sistemdeki her bir ürünü temsil eden temel sınıftır. Storable arayüzünü uygulayarak stok yönetimi ve kontrolü yeteneklerini barındırır.
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
    // Getter ve Setter metotları (Basit işlevler olduğu için dökümantasyon genellikle opsiyoneldir)
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
// Ürünün tüm bilgilerini tek bir satırda konsola yazdırır.
    public void displayInfo(){
        System.out.print("ID: " + id + " | İsim: " + name + " | Fiyat: " + price + "Tl " + " | Stok: " + quantity+" ");

    }
    //Mevcut stok miktarını verilen değer kadar artırır veya azaltır.
    @Override
    public void updateStock(int amount){
        this.quantity += amount;
        System.out.println("Stok güncellendi. Yeni miktar: " + this.quantity);
    }
// Ürünün stokta olup olmadığını kontrol eder.
    @Override
    public boolean isInStock(){
        return this.quantity > 0;
    }
}
