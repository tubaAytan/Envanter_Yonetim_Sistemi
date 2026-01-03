import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Inventory {
    private List<Product> products;

    public Inventory(){
        this.products = new ArrayList<>();
    }
    public void addProduct(Product product){
        products.add(product);
        System.out.println(product.getName() + " envantere başarıyla eklendi.");
        saveToFile();
    }
    public void listProducts(){
        if(products.isEmpty()){
            System.out.println("Envanterde ürün bulunmamaktadır.");
        }
        else{
            System.out.println("\n~~~~ Mevcut Envanter Listesi ~~~~");
            for(Product p: products){
                p.displayInfo();
            }
        }
    }

    public void saveToFile(){
        String fileName = "inventory.csv";
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
        for(Product p: products){
            writer.println(p.getId() + "," + p.getName() + "," + p.getQuantity());
        }
        System.out.println("Başarılı: Envanter '"+ fileName + "' dosyasına kaydedildi.");
        }
        catch(IOException e){
        System.out.println("Hata: Dosya yazılırken bir sorun oluştu: " + e.getMessage());
        }
    }

    public void loadFromFile(){
        String fileName = "inventory.csv";
        products.clear();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if(data.length == 4){
                    String id = data[0].trim();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    int quantity = Integer.parseInt(data[3].trim());

                    Product p = new Product(id, name, price, quantity);
                    products.add(p);
                }
            }
            System.out.println("Başarılı: Veriler dosyadan yüklendi.");
        }
        catch(IOException e){
            System.out.println("Bilgi: Henüz bir kayıt dosyası bulunamadı, yeni liste ile başlanıyor.");
        }
    }

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

    public void checkLowStock(int threshold){
        System.out.println("\n~~Stok Seviyesi "+ threshold + " Altında Olan Ürünler~~");
        boolean alert = false;

        for(Product p: products){
            if(p.getQuantity()<threshold){
                System.out.println("UYARI: "+p.getName()+" ~ Kalan Stok: "+p.getQuantity());
            }
        }
        if(!alert){
            System.out.println("Tüm ürünlerin stok seviyesi güvenli sınırda.");
        }
    }
}

