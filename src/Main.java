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
            System.out.println("Seçimiz: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    inventory.listProducts();
                case 2:
                    System.out.println("ID: ");
                    String id = scanner.nextLine();
                    System.out.println("İsim: ");
                    String name = scanner.nextLine();
                    System.out.println("Fiyat: ");
                    double price = scanner.nextDouble();
                    System.out.println("Miktar: ");
                    int qty = scanner.nextInt();
                    inventory.addProduct(new Product(id, name, price, qty));
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
