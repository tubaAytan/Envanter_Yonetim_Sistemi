import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Inventory inventory = new Inventory();
        inventory.loadFromFile();
        Scanner scanner = new Scanner(System.in);
        Supplier suppliers = new Supplier();
        suppliers.loadFromFileSupplier();

        int choice = -1;

        while(choice != 0){
            System.out.println("\n~~~~ ENVANTER YÖNETİM SİSTEMİ ~~~~");
            System.out.println("1. Tüm Ürünleri Listele");
            System.out.println("2. Yeni Ürün Ekle");
            System.out.println("3. Ürün Sil (ID ile)");
            System.out.println("4. Ürün Ara (İsim ile)");
            System.out.println("5. Az Stok Uyarılarını Gör");
            System.out.println("6. Stok Güncelleme");
            System.out.println("7. Yeni Tedarikçi Bilgisi Ekle");
            System.out.println("8. Tüm Tedarikçileri Listele");
            System.out.println("9. Tedarakçi Silme (İsim ile)");
            System.out.println("10. Sipariş Oluşturma");
            System.out.println("11.Tüm Siparişler");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            try{
                choice =scanner.nextInt();
                scanner.nextLine();
            }
            catch(Exception e){
                System.out.println("HATA: Lütfen sadece sayı giriniz!");
                scanner.nextLine();
                choice = -1;
                continue;
            }
            System.out.println("-----------------------------------------------");
            switch(choice){
                case 1:
                    inventory.listProducts();
                    break;
                case 2:
                    try {
                        int type = 0;
                        try {
                            System.out.println("1. Normal Ürün");
                            System.out.println("2. Bozulabilir Ürün");
                            System.out.print("Seçiminiz: ");
                            String typeInput = scanner.nextLine().trim();

                            if (typeInput.isEmpty()) {
                                System.out.println("HATA: Seçim boş bırakılamaz!");
                                break;
                            }
                            type = Integer.parseInt(typeInput);

                            if (type != 1 && type != 2) {
                                System.out.println("HATA: Lütfen sadece 1 veya 2 seçeneğini kullanın!");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("HATA: Geçersiz giriş! Lütfen bir sayı giriniz.");
                            break;
                        }

                        System.out.print("ID: ");
                        String id = scanner.nextLine().trim();
                        if (id.isEmpty()) { System.out.println("HATA: ID boş bırakılamaz."); break; }

                        System.out.print("İsim: ");
                        String name = scanner.nextLine().trim();
                        if (name.isEmpty()) { System.out.println("HATA: İsim boş bırakılamaz."); break; }

                        double price = 0;
                        int qty = 0;

                        try {
                            System.out.print("Fiyat: ");
                            String priceInput = scanner.nextLine().trim();
                            if (priceInput.isEmpty()) { System.out.println("HATA: Fiyat boş olamaz."); break; }
                            price = Double.parseDouble(priceInput);
                            if (price < 0) { System.out.println("HATA: Fiyat negatif olamaz!"); break; }

                            System.out.print("Miktar: ");
                            String qtyInput = scanner.nextLine().trim();
                            if (qtyInput.isEmpty()) { System.out.println("HATA: Miktar boş olamaz."); break; }
                            qty = Integer.parseInt(qtyInput);
                            if (qty < 0) { System.out.println("HATA: Miktar negatif olamaz!"); break; }
                        } catch (NumberFormatException e) {
                            System.out.println("HATA: Geçerli sayısal değerler giriniz!");
                            break;
                        }
                        if (type == 2) {
                            System.out.print("Son Kullanma Tarihi (DD-MM-YYYY): ");
                            String date = scanner.nextLine().trim();
                            if (date.isEmpty()) { System.out.println("HATA: Tarih boş olamaz."); break; }

                            inventory.addProduct(new PerishableProduct(id, name, price, qty, date));
                        } else {
                            inventory.addProduct(new Product(id, name, price, qty));
                        }
                    } catch (Exception e) {
                        System.out.println("HATA: Beklenmedik bir sorun oluştu.");
                    }
                    break;
                case 3:
                    System.out.print("Silinecek Ürün ID: ");
                    String deleteId = scanner.nextLine();
                    inventory.removeProduct(deleteId);
                    break;
                case 4:
                    System.out.print("Aranacak Ürün (İsim ile): ");
                    String searchName = scanner.nextLine();
                    inventory.searchProductByName(searchName);
                    break;
                case 5:
                    inventory.checkLowStock(5);
                    System.out.print("\nAzalan ürünler için otomatik sipariş oluşturulsun mu? (E/H): ");
                    String oChoice = scanner.nextLine();
                    if(oChoice.equalsIgnoreCase("E")){
                        System.out.println("Sipariş işlemi başlatılıyor...");
                        inventory.orderLowStockItems();
                        System.out.println("İşlem tamamlandı. Ana menüye dönülüyor.");
                    }

                    else if(oChoice.equalsIgnoreCase("H")){
                        System.out.println("İşlem iptal edildi.");
                    }
                    else{
                        System.out.println("HATA: Yanlış bilgi girişi('" + choice + "')! İşleminiz sıfırlandı.");
                    }
                    break;
                case 6:
                    inventory.listProducts();
                    System.out.print("\nStok güncellemek istediğiniz ürünün ID'sini giriniz: ");
                    String sId = scanner.nextLine();
                    Product foundProduct = inventory.getProductById(sId);

                    if(foundProduct == null){
                        System.out.println("HATA: Yazılan ID ürün listesiyle uyuşmuyor! İşleminiz sıfırlandı.");
                    }
                    else{
                        if(!foundProduct.isInStock()){
                            System.out.println("BİLGİ: Bu ürünün stoğu şu an 0 (Stokta yok).");
                            System.out.print(foundProduct.getName() + " ürünü için yeni stok miktarını giriniz (Örn: +10): ");
                            try{
                                int amount = Integer.parseInt(scanner.nextLine());
                                foundProduct.updateStock(amount);
                                if(amount<0){
                                    System.out.println("HATA: Ürün stoğu eksi konumda. İşleminiz Sıfırlanıyor.");
                                }
                                else {
                                    System.out.println("GÜNCELLENDİ: " + foundProduct.getName() + "| Yeni Stok: " + foundProduct.getQuantity());
                                    inventory.saveToFile();
                                }
                            }
                            catch(NumberFormatException e){
                                System.out.println("HATA: Lütfen geçerli bir sayı giriniz!");
                            }
                        }
                        else {
                            System.out.println("BİLGİ: Ürün şu an stokta mevcut.");
                            System.out.print(foundProduct.getName() + " ürünü için yeni stok miktarını giriniz (Örn: +10 veya -5): ");
                            try {
                                int amount = Integer.parseInt(scanner.nextLine());
                                foundProduct.updateStock(amount);
                                if(foundProduct.getQuantity()<0){
                                    System.out.println("HATA: Ürün stoğu eksi konumda. İşleminiz sıfırlanıyor.");
                                }
                                else{
                                System.out.println("GÜNCELLENDİ: " + foundProduct.getName() + "| Yeni Stok: " + foundProduct.getQuantity());
                                inventory.saveToFile();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("HATA: Lütfen geçerli bir sayı giriniz!");
                            }
                        }
                    }
                    break;
                case 7:
                    System.out.println("\n~~~~ Tedarikçi Kaydı ~~~~");
                    System.out.print("Tedarikçi Adı: ");
                    String supplierName = scanner.nextLine();
                    if(supplierName.isEmpty()){
                        System.out.println("HATA: Tedarikçi adı boş bırakılamaz!");
                        break;
                    }
                    System.out.print("İletişim Bilgisi: ");
                    String supplierContact = scanner.nextLine();
                    if(supplierContact.isEmpty()){
                        System.out.println("HATA: İletişim bilgisi boş bırakılamaz.");
                        break;
                    }
                    Supplier newSupplier = new Supplier(supplierName, supplierContact);
                    newSupplier.saveSupplierToFile();
                    suppliers.loadFromFileSupplier();
                    break;
                case 8:
                    System.out.println("\n~~~~ Mevcut Tedarikçi Listesi ~~~~");
                    suppliers.listSuppliers();
                    break;
                case 9:
                    System.out.println("\n~~~~ Tedarikçi Silme Paneli ~~~~");
                    System.out.print("Silmek istediğiniz tedarikçinin tam adını giriniz: ");
                    String deleteSupplierName = scanner.nextLine().trim();
                    if(deleteSupplierName.isEmpty()){
                        System.out.println("HATA: Silinecek tedarikçi ismini girmediniz!");
                    }
                    suppliers.removeSupplier(deleteSupplierName);
                    break;
                case 10:
                    System.out.println("\n~~~~ Sipariş Paneli ~~~~");
                    System.out.print("Sipariş verilecek ürünün adı: ");
                    String orderProductName = scanner.nextLine();
                    if(orderProductName.trim().isEmpty()){
                        System.out.println("HATA: Ürün Adı boş bırakılamaz!");
                        break;
                    }
                    int orderAmount = 0;
                    try{
                        System.out.print("Sipariş miktarı (Adet): ");
                        orderAmount = Integer.parseInt(scanner.nextLine());
                        if(orderAmount <= 0){
                            System.out.println("HATA: Sipariş miktarı 0'dan büyük olmalıdır!");
                            break;
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("HATA: Lütfen miktar için geçerli bir sayı giriniz!");
                        break;
                    }
                    String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
                    String oId = "ORD-" + timestamp;
                    Order manualOrder = new Order(oId, orderProductName, orderAmount);
                    manualOrder.saveOrderToFile();

                    System.out.println("\nSİPARİŞİNİZ ONAYLANDI");
                    manualOrder.displayOrderInfo();
                    break;

                case 11:
                    Order.listAllOrders();
                    break;
                case 0:
                    System.out.println("Sistemden Çıkılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz Seçim!");
            }
        }
        scanner.close();
    }
}
