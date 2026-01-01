import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory(){
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
        System.out.println(product.getName() + " envantere başarıyla eklendi.");
    }
}
