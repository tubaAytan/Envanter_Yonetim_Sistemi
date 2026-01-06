import java.io.*;
import java.util.ArrayList;
import java.util.List;
// Tedarikçi bilgilerini yöneten ve verileri suppliers.csv dosyasında saklayan sınıftır
public class Supplier {
    private String supplierName;
    private String contactInfo;
    private List<Supplier> suppliers; // Sistemdeki tüm tedarikçileri tutan liste
    // Parametresiz kurucu: Tedarikçi listesini bellekte oluşturur
    public Supplier(){
        this.suppliers = new ArrayList<>();
    }
    public Supplier(String supplierName, String contactInfo){ // Parametreli kurucu: Belirli bir tedarikçi nesnesi oluşturmak için kullanılır
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
    }
    // Getter ve Setter metotları: Kapsülleme (Encapsulation) ilkesine uygun veri erişimi sağlar
    public String getSupplierName(){
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getContactInfo(){
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    // Yeni tedarikçiyi dosyaya ekleme (append) modunda kaydeder
    public void saveSupplierToFile(){
        try(FileWriter fw = new FileWriter("suppliers.csv", true); PrintWriter pw = new PrintWriter(fw)){
            pw.println(this.supplierName + "," + this.contactInfo);
            System.out.println("Tedarikçi Bilgisi 'suppliers.csv' dosyasına kaydedildi.");
        }
        catch(IOException e){
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }
    // Tedarikçi bilgilerini konsola tek satırda yazdırır
    public void displaySupplierInfo(){
        System.out.print("Tedarikçi: " + supplierName + " | İletişim: " + contactInfo);
    }
    // suppliers.csv dosyasındaki tüm verileri okuyup listeye yükler
    public void loadFromFileSupplier(){
        String fileName = "suppliers.csv";
        suppliers.clear();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if(data.length == 2){
                    String supplierName = data[0].trim();
                    String contactInfo = data[1].trim();

                    suppliers.add(new Supplier(supplierName, contactInfo));
                }
            }
            System.out.println("Başarılı: Veriler dosyadan yüklendi.");
        }
        catch(IOException e){
            System.out.println("Bilgi: Henüz bir kayıt dosyası bulunamadı, yeni liste ile başlanıyor.");
        }
    }
    // Bellekteki tüm tedarikçileri ekrana listeler
    public void listSuppliers(){
        if(suppliers.isEmpty()){
            System.out.println("Sistemde tedarikçi bulunamamıştır.");
        }
        else{
            for(Supplier s: suppliers){
                s.displaySupplierInfo();
                System.out.println();
            }
        }
    }
    // Belirtilen isme göre tedarikçiyi siler ve dosyayı günceller
    public void removeSupplier(String name){
        // removeIf: İsmi eşleşen tedarikçiyi listeden kaldırır
        boolean removed = suppliers.removeIf(s -> s.getSupplierName().equalsIgnoreCase(name));

        if(removed){ // FileWriter(..., false): Dosyayı silip güncel listeyi baştan yazar
            try(PrintWriter pw = new PrintWriter((new FileWriter("suppliers.csv", false)))){
                for(Supplier s : suppliers){
                    pw.println(s.getSupplierName() + "," + s.getContactInfo());
                }
                System.out.println(name + " isimli tedarikçi silindi.");
            }
            catch (IOException e){
                System.out.println("Dosya güncelleme hatası: " + e.getMessage());
            }
        }
        else{
            System.out.println("HATA: Bu isimde bir tedarikçi bulunamadı.");
        }
    }
}
