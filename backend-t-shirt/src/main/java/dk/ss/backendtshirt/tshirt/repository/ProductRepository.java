package dk.ss.backendtshirt.tshirt.repository;

import dk.ss.backendtshirt.tshirt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find all active products
    List<Product> findByActiveTrue();

    // Find all inactive products
    List<Product> findByActiveFalse();

    // Find active products by type
    List<Product> findByActiveTrueAndProductType(Product.ProductType productType);

    // Find all products by type (active and inactive)
    List<Product> findByProductType(Product.ProductType productType);

    // Default method for finding active gift products
    default List<Product> findActiveGiftProducts() {
        return findByActiveTrueAndProductType(Product.ProductType.GIFT);
    }
}
