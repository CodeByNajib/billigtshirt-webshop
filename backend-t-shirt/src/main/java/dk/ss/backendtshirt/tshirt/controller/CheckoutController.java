package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.model.Cart;
import dk.ss.backendtshirt.tshirt.model.Customer;
import dk.ss.backendtshirt.tshirt.model.Order;
import dk.ss.backendtshirt.tshirt.repository.CustomerRepository;
import dk.ss.backendtshirt.tshirt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/test-checkout") // Skal slettes senere
public class CheckoutController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    // POST: Simuler et køb uden at have en rigtig kurv i databasen
    @PostMapping
    public ResponseEntity<?> createTestOrder(@RequestBody Map<String, Object> payload) {
        // 1. Hent data fra JSON (Postman)
        int customerId = (int) payload.get("customerId");
        double amount = Double.parseDouble(payload.get("amount").toString());
        BigDecimal totalAmount = BigDecimal.valueOf(amount);

        // 2. Find en kunde (Vi antager kunde ID 1 findes - ellers opret en i DB først!)
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Kunde ikke fundet"));

        // 3. Opret en "Falsk" kurv i hukommelsen (Vi gemmer den ikke i DB, vi bruger den bare til transport)
        Cart fakeCart = new Cart();
        fakeCart.setTotalAmount(totalAmount);
        // fakeCart.setCustomer(customer); // Hvis vores Cart kræver en kunde

        // 4. Kald din rigtige service (Det er HER vi tester din logik!)
        Order createOrder = orderService.createOrderFromCart(fakeCart, customer);

        return ResponseEntity.ok("Ordre oprettet med ID: " +  createOrder.getId() +
                ". Ordrenummer: " + createOrder.getOrderNumber());
    }


}
