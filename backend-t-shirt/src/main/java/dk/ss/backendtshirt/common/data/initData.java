package dk.ss.backendtshirt.common.data;

import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import dk.ss.backendtshirt.tshirt.repository.GiftProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class initData implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final GiftProductRepository giftProductRepository;

    @Autowired
    public initData(ProductRepository productRepository, GiftProductRepository giftProductRepository) {
        this.productRepository = productRepository;
        this.giftProductRepository = giftProductRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (productRepository.count() == 0 && giftProductRepository.count() == 0) {
            initializeProducts();
            initializeGiftProducts();
        }
    }

    private void initializeProducts() {
        // Regular T-Shirt Products (Active)
        Product product1 = new Product(
                "Classic White T-Shirt",
                "Comfortable cotton t-shirt in classic white",
                new BigDecimal("99.99"),
                "https://example.com/images/white-tshirt.jpg",
                "M",
                "White",
                100,
                true
        );

        Product product2 = new Product(
                "Black Premium T-Shirt",
                "Premium quality black t-shirt with modern fit",
                new BigDecimal("129.99"),
                "https://example.com/images/black-tshirt.jpg",
                "L",
                "Black",
                75,
                true
        );

        Product product3 = new Product(
                "Navy Blue T-Shirt",
                "Stylish navy blue t-shirt for everyday wear",
                new BigDecimal("109.99"),
                "https://example.com/images/navy-tshirt.jpg",
                "S",
                "Blue",
                50,
                true
        );

        Product product4 = new Product(
                "Red Graphic T-Shirt",
                "Eye-catching red t-shirt with graphic design",
                new BigDecimal("119.99"),
                "https://example.com/images/red-tshirt.jpg",
                "XL",
                "Red",
                60,
                true
        );

        // Inactive product (for testing deactivation functionality)
        Product product5 = new Product(
                "Vintage Gray T-Shirt",
                "Vintage style gray t-shirt - discontinued",
                new BigDecimal("89.99"),
                "https://example.com/images/gray-tshirt.jpg",
                "M",
                "Gray",
                0,
                false
        );

        // Save all products
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);

        System.out.println("✅ Regular products initialized: 5 products created");
        System.out.println("   - Active regular products: 4");
        System.out.println("   - Inactive regular products: 1");
    }

    private void initializeGiftProducts() {
        // Gift Products (Active)
        GiftProduct gift1 = new GiftProduct(
                "Premium Gift T-Shirt - White",
                "Exclusive gift t-shirt in premium white cotton",
                "https://example.com/images/gift-white.jpg",
                200,
                true
        );

        GiftProduct gift2 = new GiftProduct(
                "Premium Gift T-Shirt - Black",
                "Exclusive gift t-shirt in premium black cotton",
                "https://example.com/images/gift-black.jpg",
                150,
                true
        );

        GiftProduct gift3 = new GiftProduct(
                "Limited Edition Gift T-Shirt",
                "Special limited edition gift t-shirt",
                "https://example.com/images/gift-limited.jpg",
                50,
                true
        );

        // Inactive gift product (for testing)
        GiftProduct gift4 = new GiftProduct(
                "Seasonal Gift T-Shirt",
                "Seasonal gift t-shirt - out of season",
                "https://example.com/images/gift-seasonal.jpg",
                0,
                false
        );

        // Save all gift products
        giftProductRepository.save(gift1);
        giftProductRepository.save(gift2);
        giftProductRepository.save(gift3);
        giftProductRepository.save(gift4);

        System.out.println("✅ Gift products initialized: 4 gift products created");
        System.out.println("   - Active gift products: 3");
        System.out.println("   - Inactive gift products: 1");
    }
}
