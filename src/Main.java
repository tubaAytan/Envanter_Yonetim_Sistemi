import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);
        inventory.loadFromFile();
        int choice = -1;

        while(choice != 0){
            System.out.println("\n~~~~ ENVANTER YÖNETİM SİSTEMİ ~~~~");
            System.out.println("1. Tüm Ürünleri Listele");
            System.out.println("2. Yeni Ürün Ekle");
            System.out.println("3. Ürün Sil (ID ile)");
            System.out.println("4. Ürün Ara (İsim ile)");
            System.out.println("5. Az Stok Uyarılarını Gör");
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
