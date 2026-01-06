import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
    private Inventory inventory; //Her test senaryosundan önce taze bir envanter nesnesi oluşturur. Bu sayede testlerin birbirinden bağımsız çalışması sağlanır.

    @Before
    public void setUp(){
        inventory = new Inventory();
    }
    // Ürünün başarıyla eklendiğini ve ID ile geri çağrılabildiğini test eder.
    @Test
    public void testAddAndGetProduct(){
        Product p = new Product("101", "Elma", 10.5,50);
        inventory.addProduct(p);

        Product found = inventory.getProductById("101");
        assertNotNull("Ürün bulunmalı", found);
        assertEquals("Elma", found.getName());
    }
    // Ürün silme işleminin listeden veriyi tamamen kaldırdığını doğrular.
    @Test
    public void testRemoveProduct(){
        inventory.addProduct(new Product("102","Armut",5.0,10));
        inventory.removeProduct("102");

        assertNull("Silinen ürün null dönmeli", inventory.getProductById("102"));
    }
    // Stok artırma ve azaltma işlemlerinin matematiksel doğruluğunu denetler.
    @Test
    public void testStockUpdateLogic(){
        Product p = new Product("103","Muz",15.0,20);
        p.updateStock(10);
        assertEquals(30, p.getQuantity());

        p.updateStock(-5);
        assertEquals(25,p.getQuantity());
    }
    // isInStock metodunun miktar değişimlerine göre doğru boolean değer döndürdüğünü test eder.
    @Test
    public void testIsInStockValidation(){
        Product p = new Product("104", "Çilek", 20.0, 0);
        assertFalse("Stok 0 iken false dönmeli", p.isInStock());

        p.updateStock(1);
        assertTrue("Stock 1 iken true dönmeli", p.isInStock());
    }
    // Bozulabilir ürünlerin kalıtım yapısını ve tarih bilgisinin tutarlılığını doğrular.
    @Test
    public void testPerishableProductInheritance(){
        PerishableProduct pp = new PerishableProduct("201", "Yoğurt", 40.0, 5, "20-05-2026");
        assertEquals("20-05-2026",pp.getExpiryDate());
        assertTrue(pp instanceof Product);
    }
    // Stok tam 0'a çekildiğinde sistemin 'stokta yok' durumuna geçtiğini test eder.
    @Test
    public void testNegativeStockUpdate() {
        Product p = new Product("B1", "Sınır Testi", 10.0, 10);
        p.updateStock(-10);
        assertEquals(0, p.getQuantity());
        assertFalse("Stok 0 iken isInStock false olmalı", p.isInStock());
    }
    // Sistemin aynı ID'ye sahip ikinci bir ürünü reddettiğini ve  mevcut veriyi koruduğunu doğrulayan güvenlik testidir.
    @Test
    public void testDuplicateProductId() {
        inventory.addProduct(new Product("X1", "Ürün 1", 5.0, 10));
        inventory.addProduct(new Product("X1", "Ürün 2", 15.0, 20)); // Bu eklenmeyecek
        assertEquals("Aynı ID'li ürün eklenmemeliydi", 1, inventory.getProductCount());
    }
    // double veri tipindeki fiyatların ondalık hassasiyetinin korunduğunu test eder.
    @Test
    public void testPricePrecision() {
        Product p = new Product("P1", "Hassas Fiyat", 10.999, 5);
        assertEquals(10.999, p.getPrice(), 0.001);
    }
    // Çok yüksek miktarlı stok girişlerinde tam sayı (integer) sınırlarının yönetimini test eder.
    @Test
    public void testLargeQuantity() {
        Product p = new Product("L1", "Büyük Stok", 1.0, 1_000_000);
        p.updateStock(9_000_000);
        assertEquals(10000000, p.getQuantity());
    }
    // Boş envanterde yapılan aramaların çökme (crash) üretmediğini kontrol eder.
    @Test
    public void testSearchOnEmptyInventory() {
        inventory.searchProductByName("HerhangiBirSey");
        assertTrue(inventory.getProductById("999") == null);
    }
}
