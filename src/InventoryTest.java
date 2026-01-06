import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
    private Inventory inventory;

    @Before
    public void setUp(){
        inventory = new Inventory();
    }

    @Test
    public void testAddAndGetProduct(){
        Product p = new Product("101", "Elma", 10.5,50);
        inventory.addProduct(p);

        Product found = inventory.getProductById("101");
        assertNotNull("Ürün bulunmalı", found);
        assertEquals("Elma", found.getName());
    }

    @Test
    public void testRemoveProduct(){
        inventory.addProduct(new Product("102","Armut",5.0,10));
        inventory.removeProduct("102");

        assertNull("Silinen ürün null dönmeli", inventory.getProductById("102"));
    }

    @Test
    public void testStockUpdateLogic(){
        Product p = new Product("103","Muz",15.0,20);
        p.updateStock(10);
        assertEquals(30, p.getQuantity());

        p.updateStock(-5);
        assertEquals(25,p.getQuantity());
    }

    @Test
    public void testIsInStockValidation(){
        Product p = new Product("104", "Çilek", 20.0, 0);
        assertFalse("Stok 0 iken false dönmeli", p.isInStock());

        p.updateStock(1);
        assertTrue("Stock 1 iken true dönmeli", p.isInStock());
    }

    @Test
    public void testPerishableProductInheritance(){
        PerishableProduct pp = new PerishableProduct("201", "Yoğurt", 40.0, 5, "20-05-2026");
        assertEquals("20-05-2026",pp.getExpiryDate());
        assertTrue(pp instanceof Product);
    }

    @Test
    public void testNegativeStockUpdate() {
        Product p = new Product("B1", "Sınır Testi", 10.0, 10);
        p.updateStock(-10);
        assertEquals(0, p.getQuantity());
        assertFalse("Stok 0 iken isInStock false olmalı", p.isInStock());
    }

    @Test
    public void testDuplicateProductId() {
        inventory.addProduct(new Product("X1", "Ürün 1", 5.0, 10));
        inventory.addProduct(new Product("X1", "Ürün 2", 15.0, 20));
        assertEquals("Listede hala ürünler olmalı", 2, inventory.getProductById("X1") != null ? 1 : 0);
    }

    @Test
    public void testPricePrecision() {
        Product p = new Product("P1", "Hassas Fiyat", 10.999, 5);
        assertEquals(10.999, p.getPrice(), 0.001);
    }

    @Test
    public void testLargeQuantity() {
        Product p = new Product("L1", "Büyük Stok", 1.0, 1_000_000);
        p.updateStock(9_000_000);
        assertEquals(10000000, p.getQuantity());
    }

    @Test
    public void testSearchOnEmptyInventory() {
        inventory.searchProductByName("HerhangiBirSey");
        assertTrue(inventory.getProductById("999") == null);
    }
}
