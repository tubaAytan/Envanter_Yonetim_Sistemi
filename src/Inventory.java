import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;
// Envanterdeki ürünleri yöneten, CSV dosyasıyla veri senkronizasyonu sağlayan merkez sınıf
public class Inventory {
    private List<Product> products; // Ürünleri hafızada tutan liste
    // Constructor: Boş bir ürün listesi başlatır
    public Inventory(){
        this.products = new ArrayList<>();
    }
    // Listeye yeni ürün ekler; ID kontrolü yapar
    public void addProduct(Product product){
        if(getProductById(product.getId()) != null) {
            System.out.println("HATA: " + product.getId() + " ID'li ürün zaten mevcut! Ekleme reddedildi.");
        }
        else{
            products.add(product);
            saveToFile(); // Değişikliği anında dosyaya kaydeder
            System.out.println(product.getName() + " envantere başarıyla eklendi.");
        }
    }
    // Envanterdeki tüm ürünleri konsola listeler
    public void listProducts(){
        if(products.isEmpty()){
            System.out.println("Envanterde ürün bulunmamaktadır.");
        }
        else{
            System.out.println("\n~~~~ Mevcut Envanter Listesi ~~~~");
            for(Product p: products){
                p.displayInfo();
                System.out.println();
            }
        }
    }
    // Listeyi inventory.csv dosyasına yazar; polimorfizm kullanarak ürün tipine göre formatlar
    public void saveToFile(){
        String fileName = "inventory.csv";
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
        for(Product p: products){
            if(p instanceof PerishableProduct){
                PerishableProduct pp = (PerishableProduct) p;
                String line = String.format(Locale.US, "%s,%s,%.2f,%d,%s",p.getId(), p.getName(), p.getPrice(), p.getQuantity(),pp.getExpiryDate());
                writer.println(line);
            }
            else{ // Normal ürün formatında yazar
                String line = String.format(Locale.US, "%s,%s,%.2f,%d",p.getId(), p.getName(), p.getPrice(), p.getQuantity());
                writer.println(line);
            }
        }
        System.out.println("Başarılı: Envanter '"+ fileName + "' dosyasına kaydedildi.");
        }
        catch(IOException e){
        System.out.println("Hata: Dosya yazılırken bir sorun oluştu: " + e.getMessage());
        }
    }
    // Dosyadan verileri okur; sütun sayısına göre Product veya PerishableProduct oluşturur
    public void loadFromFile(){
        String fileName = "inventory.csv";
        products.clear(); // Mükerrer veri yüklemeyi önlemek için listeyi temizler

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if(data.length == 5){ // 5 sütun varsa bozulabilir üründür
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    int quantity = Integer.parseInt(data[3].trim());
                    String expiryDate = data[4].trim();

                    products.add(new PerishableProduct(id, name, price, quantity, expiryDate));
                }
                else if(data.length == 4){ // 4 sütun varsa normal üründür
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    int quantity = Integer.parseInt(data[3].trim());

                    products.add(new Product(id, name, price, quantity));
                }
            }
            System.out.println("Başarılı: Veriler dosyadan yüklendi.");
        }
        catch(IOException e){
            System.out.println("Bilgi: Henüz bir kayıt dosyası bulunamadı, yeni liste ile başlanıyor.");
        }
    }
    // Belirtilen ID'ye sahip ürünü siler ve dosyayı günceller
    public void removeProduct(String id){
        boolean found = false;

        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId().equals(id)){
                System.out.println(products.get(i).getName() + " envanterden siliniyor...");
                products.remove(i);
                found = true;
                break;
            }
        }

        if(!found){
            System.out.println("Hata: "+ id + " ID'li ürün bulunamadı.");
        }
        else{
            saveToFile();
        }
    }
    // Ürün adı içerisinde arama yapar (Büyük/küçük harf duyarsız)
    public void searchProductByName(String searchTerm){
        System.out.println("\n~~~~ '" + searchTerm + "' için Arama Sonuçları ~~~~");
        boolean found = false;

        for(Product p: products){
            if(p.getName().toLowerCase().contains(searchTerm.toLowerCase())){
                p.displayInfo();
                found = true;
            }
        }
        if(!found){
            System.out.println("Aranan kriterlere uygun ürün bulunamadı.");
        }
    }
    // Stok miktarı belirlenen sınırın altında kalan ürünleri listeler
    public void checkLowStock(int threshold){
        System.out.println("\n~~Stok Seviyesi "+ threshold + " Altında Olan Ürünler~~");
        boolean alert = false;

        for(Product p: products){
            if(p.getQuantity()<threshold){
                System.out.println("UYARI: "+p.getName()+" ~ Kalan Stok: "+p.getQuantity());
                alert =  true;
            }
        }
        if(!alert){
            System.out.println("Tüm ürünlerin stok seviyesi güvenli sınırda.");
        }
    }
    // Verilen ID'ye sahip ürün nesnesini döndürür
    public Product getProductById(String id){
        for(Product p : products){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
    // Azalan ürünler için otomatik sipariş oluşturur ve stok ikmali yapar
    public void orderLowStockItems(){
        boolean ordered = false;
        for(Product p : products){
            if(p.getQuantity() < 5 ){
                Order autoOrder = new Order("AUTO-" + p.getId(), p.getName(),20);
                autoOrder.saveOrderToFile();
                p.updateStock(20);
                System.out.println(p.getName() + " için sipariş eklendi ve stok +20 güncellendi.");
                ordered = true;
            }
           if(!ordered){
               System.out.println("BİLGİ: Sipariş verilecek ürün bulunamadı.");
           }
           else{
               saveToFile();
           }
        }
    }
    // JUnit testlerinin ürün sayısını doğrulaması için kullanılır
    public int getProductCount() {
        return products.size();
    }
}

