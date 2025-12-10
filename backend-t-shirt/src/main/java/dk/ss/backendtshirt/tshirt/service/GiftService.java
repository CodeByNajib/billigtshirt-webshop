package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.dto.GiftConfigResponseDTO;
import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.model.Order;
import dk.ss.backendtshirt.tshirt.model.OrderGift;
import dk.ss.backendtshirt.tshirt.repository.GiftConfigRepository;
import dk.ss.backendtshirt.tshirt.repository.GiftProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftService {

    private static final Logger logger = LoggerFactory.getLogger(GiftService.class);
    private final GiftConfigRepository giftConfigRepository;
    private final GiftProductRepository giftProductRepository;

    public GiftService(GiftConfigRepository giftConfigRepository, GiftProductRepository giftProductRepository) {
        this.giftConfigRepository = giftConfigRepository;
        this.giftProductRepository = giftProductRepository;
    }

    // --- ADMIN LOGIK - MULTIPLE KONFIGURATIONER ---

    // Opret ny gift konfiguration (POST)
    public GiftConfigResponseDTO createGiftConfig(BigDecimal thresholdAmount, Long giftProductId, int active) {
        // Validering
        if (thresholdAmount == null || thresholdAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Beløbsgrænsen skal være større end 0!");
        }

        if (giftProductId == null || giftProductId <= 0) {
            throw new IllegalArgumentException("Gave produkt ID skal være gyldigt!");
        }

        // Tjek om gave produktet eksisterer
        GiftProduct giftProduct = giftProductRepository.findById(giftProductId)
                .orElseThrow(() -> new IllegalArgumentException("Gave produkt med ID " + giftProductId + " findes ikke!"));

        // Tjek for duplikater - samme threshold + samme produkt
        Optional<GiftConfig> existing = giftConfigRepository.findByThresholdAmountAndGiftProductId(thresholdAmount, giftProductId);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Dette gave produkt er allerede konfigureret for denne threshold!");
        }

        // Opret ny konfiguration
        GiftConfig config = new GiftConfig();
        config.setThresholdAmount(thresholdAmount);
        config.setGiftProductId(giftProductId);
        config.setActive(active);

        GiftConfig savedConfig = giftConfigRepository.save(config);

        logger.info("Ny gave konfiguration oprettet: ID={}, Beløb={}, ProduktID={}, Aktiv={}",
                savedConfig.getId(), thresholdAmount, giftProductId, active);

        return mapToResponseDTO(savedConfig, giftProduct.getName());
    }

    // Hent alle gift konfigurationer (GET /all)
    public List<GiftConfigResponseDTO> getAllGiftConfigs() {
        List<GiftConfig> configs = giftConfigRepository.findAll();

        return configs.stream()
                .map(config -> {
                    String productName = giftProductRepository.findById(config.getGiftProductId())
                            .map(GiftProduct::getName)
                            .orElse("Ukendt produkt");
                    return mapToResponseDTO(config, productName);
                })
                .collect(Collectors.toList());
    }

    // Slet en gift konfiguration (DELETE)
    public void deleteGiftConfig(Long id) {
        if (!giftConfigRepository.existsById(id)) {
            throw new IllegalArgumentException("Gift konfiguration med ID " + id + " findes ikke!");
        }

        giftConfigRepository.deleteById(id);
        logger.info("Gift konfiguration med ID {} blev slettet", id);
    }

    // Hjælpemetode til at mappe til DTO
    private GiftConfigResponseDTO mapToResponseDTO(GiftConfig config, String productName) {
        return new GiftConfigResponseDTO(
                config.getId(),
                config.getThresholdAmount(),
                config.getGiftProductId(),
                productName,
                config.getActive()
        );
    }

    // --- EKSISTERENDE LOGIK (BEHOLD KOMPATIBILITET) ---

    // Hjælpemetode: Hent den aktive konfiguration
    // Hvis ingen findes, returnerer vi null (så vi kan håndtere fejl)
    private GiftConfig getActiveConfig() {
        return giftConfigRepository.findFirstByActive(1);
    }

    // Hent den komplette gift konfiguration (til admin frontend)
    public GiftConfig getGiftConfig() {
        // Først prøver vi at hente den aktive config
        GiftConfig config = getActiveConfig();

        // Hvis ingen aktiv findes, prøv at hente den første config (uanset status)
        if (config == null) {
            config = giftConfigRepository.findById(1L).orElse(null);
        }

        return config;
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

    // Opdater den komplette gift konfiguration (beløb, produkt ID og aktiv status)
    public GiftConfig updateGiftConfig(BigDecimal newAmount, Long giftProductId, int active) {
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Beløbsgrænsen må ikke være negativ!");
        }

        if (giftProductId == null || giftProductId <= 0) {
            throw new IllegalArgumentException("Gave produkt ID skal være gyldigt!");
        }

        // Prøv at finde den eksisterende config
        GiftConfig config = giftConfigRepository.findById(1L).orElse(null);

        if (config == null) {
            // Hvis den ikke findes, laver vi en NY
            config = new GiftConfig();
        }

        config.setThresholdAmount(newAmount);
        config.setGiftProductId(giftProductId);
        config.setActive(active);

        logger.info("Opdaterer gave konfiguration: Beløb={}, ProduktID={}, Aktiv={}",
                    newAmount, giftProductId, active);

        return giftConfigRepository.save(config);
    }

    // 2. Opdater grænsen (US-A3: Bruges af Admin) - DEPRECATED, brug updateGiftConfig i stedet
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
