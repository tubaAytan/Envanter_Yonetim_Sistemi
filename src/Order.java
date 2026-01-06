import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private String orderId;
    private String productName;
    private int quantity;
    private String orderDate;

    public String getOrderId(){return orderId;}
    public void setOrderId(String orderId) {this.orderId = orderId;}
    public String getProductName(){return productName;}
    public void setProductName(String productName){this.productName = productName;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity = quantity;}
    public String getOrderDate(){return orderDate;}
    public void setOrderDate(String orderDate) { this.orderDate = orderDate;}

    public Order(String orderId, String productName, int quantity){
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.orderDate = dtf.format(LocalDateTime.now());
    }

    public void saveOrderToFile(){
        try(FileWriter fw = new FileWriter("orders.csv",true); PrintWriter pw = new PrintWriter(fw)){
            pw.println(orderId + "," + productName + "," + quantity + "," + orderDate);
            System.out.println("Sipariş 'orders.csv' dosyasına kaydedildi.");
            pw.flush();
        }
        catch (IOException e){
            System.out.println("Sipariş yazılırken hata oluştu: " + e.getMessage());
        }
    }

    public void displayOrderInfo(){
        System.out.println("Sipariş No: " + orderId + " | Ürün Adı: " + productName + " | Miktar: " + quantity + " | Tarih: " + orderDate);
    }

    public static void listAllOrders() {
        System.out.println("\n~~~~ Geçmiş Sipariş Kayıtları ~~~~");
        System.out.println(String.format("%-15s | %-15s | %-8s | %-20s", "Sipariş ID", "Ürün Adı", "Miktar", "Tarih"));
        try (BufferedReader reader = new BufferedReader(new FileReader("orders.csv"))) {
            String line;
            boolean hasOrders = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length == 4) {
                    System.out.println(String.format("%-15s | %-15s | %-8s | %-20s", data[0], data[1], data[2], data[3]));
                    hasOrders = true;
                }
            }
            if (!hasOrders) {
                System.out.println("Henüz bir sipariş kaydı bulunmuyor.");
            }
        } catch (IOException e) {
            System.out.println("BİLGİ: Sipariş dosyası henüz oluşturulmamıştır.");
        }
    }
}
