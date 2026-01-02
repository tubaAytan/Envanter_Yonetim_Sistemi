import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Inventory {
    private List<Product> products;

    public Inventory(){
        this.products = new ArrayList<>();
    }
    public void addProduct(Product product){
        products.add(product);
        System.out.println(product.getName() + " envantere başarıyla eklendi.");
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
}
