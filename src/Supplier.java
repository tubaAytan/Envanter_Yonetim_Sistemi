import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String supplierName;
    private String contactInfo;
    private List<Supplier> suppliers;
    public Supplier(){
        this.suppliers = new ArrayList<>();
    }

    public Supplier(String supplierName, String contactInfo){
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
    }

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

    public void saveSupplierToFile(){
        try(FileWriter fw = new FileWriter("suppliers.csv", true); PrintWriter pw = new PrintWriter(fw)){
            pw.println(this.supplierName + "," + this.contactInfo);
            System.out.println("Tedarikçi Bilgisi 'suppliers.csv' dosyasına kaydedildi.");
        }
        catch(IOException e){
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    public void displaySupplierInfo(){
        System.out.print("Tedarikçi: " + supplierName + " | İletişim: " + contactInfo);
    }
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
}
