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
                    try{
                    System.out.println("1. Normal Ürün");
                    System.out.println("2. Bozulabilir ürün");
                    System.out.print("Seçiminiz: ");
                    int type = Integer.parseInt(scanner.nextLine());

                    if(type == 2){
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("İsim: ");
                        String name = scanner.nextLine();
                        System.out.print("Fiyat: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Miktar: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Son Kullanma Tarihi (DD-MM-YYYY): ");
                        String date = scanner.nextLine();
                        inventory.addProduct(new PerishableProduct(id, name, price, qty, date));
                    }
                    else{
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("İsim: ");
                        String name = scanner.nextLine();
                        System.out.print("Fiyat: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Miktar: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();
                        inventory.addProduct(new Product(id, name, price, qty));
                    }

                    } catch (Exception e) {
                        System.out.println("HATA: Hatalı veri tipi girdiniz! Ürün ekleme iptal edildi.");
                        scanner.nextLine();
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
                    System.out.print("İletişim Bilgisi: ");
                    String supplierContact = scanner.nextLine();

                    Supplier newSupplier = new Supplier(supplierName, supplierContact);
                    newSupplier.saveSupplierToFile();
                    suppliers.loadFromFileSupplier();
                    break;
                case 8:
                    System.out.println("\n~~~~ Mevcut Tedarikçi Listesi ~~~~");
                    suppliers.listSuppliers();
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
