public class PerishableProduct extends Product { // Product sınıfından türetilen, son kullanma tarihi olan ürünleri temsil eder
    private String expiryDate; // Ürünün son kullanma tarihi bilgisini tutar
    // Kurucu metot: Üst sınıfın bilgilerini (super) ve tarih bilgisini ayarlar
    public PerishableProduct(String id, String name, double price, int quantity, String expiryDate){
        super(id, name, price, quantity); // Temel ürün bilgilerini Product sınıfına gönderir
        this.expiryDate = expiryDate;
    }
    // Tarih bilgisine erişmek için kullanılan getter metodu
    public String getExpiryDate(){
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate){
        this.expiryDate = expiryDate;
    } // Tarih bilgisini güncellemek için kullanılan setter metodu

    @Override // Üst sınıfın bilgilerini yazdırıp yanına tarih bilgisini ekler
    public void displayInfo(){
        super.displayInfo();
        System.out.print("| Son Kullanma Tarihi: " + expiryDate);
    }
}
