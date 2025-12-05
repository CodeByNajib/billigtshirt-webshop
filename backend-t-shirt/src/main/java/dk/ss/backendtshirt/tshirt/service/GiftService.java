package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import dk.ss.backendtshirt.tshirt.repository.GiftConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GiftService {
    @Autowired
    private GiftConfigRepository giftConfigRepository;

    // --- ADMIN LOGIK ---

    // 1. Hent nuværende grænse (så admin kan se den)
    public BigDecimal getCurrentThreshold() {
        GiftConfig config = giftConfigRepository.findFirstByActive(1);
        if(config==null) return BigDecimal.ZERO; // Fallback hvis DB er tom
        return config.getThresholdAmount();
    }

    // 2. Opdater grænsen (US-A3: Bruges af Admin)
    public GiftConfig updateThreshold(BigDecimal newAmount) {
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Beløbsgrænsen må ikke være negativ!");
        }

        // Prøv at finde den eksisterende config
        GiftConfig config = giftConfigRepository.findById(1).orElse(null);

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
