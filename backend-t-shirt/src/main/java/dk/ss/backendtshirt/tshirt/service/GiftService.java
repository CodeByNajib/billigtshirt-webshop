package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import dk.ss.backendtshirt.tshirt.model.Order;
import dk.ss.backendtshirt.tshirt.model.OrderGift;
import dk.ss.backendtshirt.tshirt.repository.GiftConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GiftService {

    private static final Logger logger = LoggerFactory.getLogger(GiftService.class);
    private GiftConfigRepository giftConfigRepository;

    public GiftService(GiftConfigRepository giftConfigRepository) {
        this.giftConfigRepository = giftConfigRepository;
    }

    // --- ADMIN LOGIK --

    // Hjælpemetode: Hent den aktive konfiguration
    // Hvis ingen findes, returnerer vi null (så vi kan håndtere fejl)
    private GiftConfig getActiveConfig() {
        return giftConfigRepository.findFirstByActive(1);
    }

    public BigDecimal getCurrentThreshold() {
        GiftConfig config = getActiveConfig();
        return (config != null) ? config.getThresholdAmount() : BigDecimal.ZERO;
    }

    // ---- LOGIKKEN UDEN HARDCODING) ---

    public void applyGiftToOrder(Order order) {
        GiftConfig config = getActiveConfig();

        // Sikkerhedsnet: Hvis admin ikke har sat en config op, gør vi intet
        if(config == null) {
            logger.warn("Ingen aktive gave-konfiguration fundet! Gaven springes over.");
            return;
        }

        BigDecimal threshold = config.getThresholdAmount();
        BigDecimal total = order.getTotalAmount();
        Long giftIdFromDb = config.getGiftProductId(); // Hentet dynamisk fra DB

        // Gem historik på ordren
        order.setOriginalGiftThreshold(threshold);

        if(total.compareTo(threshold) >= 0) {
            logger.info("Ordre {} kvalificeret. Total: {}, Grænse: {}, GaveID: {}",
                    order.getOrderNumber(), total, threshold, giftIdFromDb);


            // Opret gave-objektet
            OrderGift gift = new OrderGift();

            // Forskellen: Vi bruger ID fra databasen
            gift.setGiftProductId(giftIdFromDb);

            //TODO: Her mangler vi stadig lager-tjekket (Task S2.2)
            gift.setAvailable(1);

            order.addGift(gift);
        } else {
            logger.info("Order {} ikke kvalifcieret", order.getOrderNumber());
        }
    }

    // 2. Opdater grænsen (US-A3: Bruges af Admin)
    public GiftConfig updateThreshold(BigDecimal newAmount) {
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Beløbsgrænsen må ikke være negativ!");
        }

        // Prøv at finde den eksisterende config
        GiftConfig config = giftConfigRepository.findById(1L).orElse(null);

        if (config == null) {
            // Hvis den ikke findes, laver vi en NY
            config = new GiftConfig();
            config.setActive(1);
            // Vi lader være med at sætte ID manuelt her - databasen styrer det selv!
        }

        config.setThresholdAmount(newAmount);

        return giftConfigRepository.save(config);
    }

    // --- KUNDE/KURV LOGIK ---

    // Denne metode kaldes, hver gang kunden viser kurven eller tilføjer en vare
    // Tjek om et beløb kvalificerer til gave
    public boolean checkQualification(BigDecimal amount) {
        BigDecimal threshold = getCurrentThreshold();
        return amount.compareTo(threshold) >= 0;
    }
}
