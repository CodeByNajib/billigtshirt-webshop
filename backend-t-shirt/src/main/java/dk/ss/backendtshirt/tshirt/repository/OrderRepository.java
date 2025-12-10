package dk.ss.backendtshirt.tshirt.repository;

import dk.ss.backendtshirt.tshirt.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Her kan vi senere tilføje: findByCustomer_Id(int id) osv
}
