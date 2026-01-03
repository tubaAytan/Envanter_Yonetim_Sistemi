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
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
            }
        }
    }
}
