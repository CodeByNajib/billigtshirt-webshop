package dk.ss.backendtshirt.tshirt.repository;

import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftProductRepository extends JpaRepository<GiftProduct, Long> {

    // Find all active gift products
    List<GiftProduct> findByActiveTrue();

    // Find all inactive gift products
    List<GiftProduct> findByActiveFalse();
}

