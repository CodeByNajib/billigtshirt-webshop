package dk.ss.backendtshirt.common.data;

import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class initData implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Autowired
    public initData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (productRepository.count() == 0) {
            initializeProducts();
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
                true,
                Product.ProductType.REGULAR
        );

        Product product2 = new Product(
                "Black Premium T-Shirt",
                "Premium quality black t-shirt with modern fit",
                new BigDecimal("129.99"),
                "https://example.com/images/black-tshirt.jpg",
                "L",
                "Black",
                75,
                true,
                Product.ProductType.REGULAR
        );

        Product product3 = new Product(
                "Navy Blue T-Shirt",
                "Stylish navy blue t-shirt for everyday wear",
                new BigDecimal("109.99"),
                "https://example.com/images/navy-tshirt.jpg",
                "S",
                "Navy",
                50,
                true,
                Product.ProductType.REGULAR
        );

        Product product4 = new Product(
                "Red Graphic T-Shirt",
                "Eye-catching red t-shirt with graphic design",
                new BigDecimal("119.99"),
                "https://example.com/images/red-tshirt.jpg",
                "XL",
                "Red",
                60,
                true,
                Product.ProductType.REGULAR
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
                false,
                Product.ProductType.REGULAR
        );

        // Gift Products (Active)
        Product gift1 = new Product(
                "Premium Gift T-Shirt - White",
                "Exclusive gift t-shirt in premium white cotton",
                new BigDecimal("0.00"),
                "https://example.com/images/gift-white.jpg",
                "M",
                "White",
                200,
                true,
                Product.ProductType.GIFT
        );

        Product gift2 = new Product(
                "Premium Gift T-Shirt - Black",
                "Exclusive gift t-shirt in premium black cotton",
                new BigDecimal("0.00"),
                "https://example.com/images/gift-black.jpg",
                "L",
                "Black",
                150,
                true,
                Product.ProductType.GIFT
        );

        Product gift3 = new Product(
                "Limited Edition Gift T-Shirt",
                "Special limited edition gift t-shirt",
                new BigDecimal("0.00"),
                "https://example.com/images/gift-limited.jpg",
                "M",
                "Blue",
                50,
                true,
                Product.ProductType.GIFT
        );

        // Inactive gift product (for testing)
        Product gift4 = new Product(
                "Seasonal Gift T-Shirt",
                "Seasonal gift t-shirt - out of season",
                new BigDecimal("0.00"),
                "https://example.com/images/gift-seasonal.jpg",
                "S",
                "Green",
                0,
                false,
                Product.ProductType.GIFT
        );

        // Save all products
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(gift1);
        productRepository.save(gift2);
        productRepository.save(gift3);
        productRepository.save(gift4);

        System.out.println("✅ Test data initialized: 9 products created (5 regular, 4 gift products)");
        System.out.println("   - Active regular products: 4");
        System.out.println("   - Inactive regular products: 1");
        System.out.println("   - Active gift products: 3");
        System.out.println("   - Inactive gift products: 1");
    }
}
