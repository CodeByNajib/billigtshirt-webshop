package dk.ss.backendtshirt.tshirt.repository;

import dk.ss.backendtshirt.tshirt.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
