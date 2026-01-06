// Stoklanabilir tüm nesneler için temel kuralları belirleyen arayüz (interface)
public interface Storable {
    void updateStock(int amount); // Stok miktarını güncellemek için zorunlu kılınan metot
    boolean isInStock(); // Ürünün stokta bulunup bulunmadığını kontrol etmek için zorunlu kılınan metot
}
