import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Supplier {
    private String supplierName;
    private String contactInfo;

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
        try(FileWriter fw = new FileWriter("supplier.csv", true); PrintWriter pw = new PrintWriter(fw)){
            pw.println(this.supplierName + "," + this.contactInfo);
            System.out.println("Tedarikçi Bilgisi 'supplier.csv' dosyasına kaydedildi.");
        }
        catch(IOException e){
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    public void displaySupplierInfo(){
        System.out.println(" | Tedarikçi: " + supplierName + " | İletişim: " + contactInfo);
    }


}
