package dk.ss.backendtshirt.config;

import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.repository.GiftConfigRepository;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component //Denne annotation siger til Spring: "Kør mig ved opstart!"
public class DataSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final GiftConfigRepository giftConfigRepository; // Vi skal bruge denne nu

    // Constructor Injection: Spring giver os adgang til begge repositories
    public DataSeeder(ProductRepository productRepository, GiftConfigRepository giftConfigRepository) {
        this.productRepository = productRepository;
        this.giftConfigRepository = giftConfigRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // 1. OPRET PRODUKTER (HVIS TOM)
        if (productRepository.count() == 0) {
            System.out.println("🌱 SEEDING: Opretter produkter...");
            productRepository.save(new Product("Klassisk Grøn T-Shirt",
                    new BigDecimal("150.00"))); // ID 1
            productRepository.save(new Product("Premium Hvid T-Shirt",
                    new BigDecimal("200.00")));  // ID 2
            productRepository.save(new Product("Skov Edition",
                    new BigDecimal("350.00")));          // ID 3
        }

        // 2. OPRET GAVE-KONFIGURATION (HVIS TOM)
        // Det er her, vi fortæller systemet at grænsen er 599 kr
        if (giftConfigRepository.count() == 0) {
            System.out.println("🎁 SEEDING: Sætter gavegrænse til 599 kr...");

            GiftConfig config = new GiftConfig();
            config.setThresholdAmount(new BigDecimal("599.00")); // Grænsen
            config.setActive(1);                                 // Den er aktiv
            config.setGiftProductId(1L);                         // Gaven er produkt ID 1 (Grøn T-shirt)

            giftConfigRepository.save(config);
        }
    }
}
