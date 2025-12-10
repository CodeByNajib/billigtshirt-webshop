package dk.ss.backendtshirt.tshirt.repository;

import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiftConfigRepository extends JpaRepository<GiftConfig,Long> {
    // Vi antager, at der kun er én aktiv config, eller vi henter den med ID 1
    GiftConfig findFirstByActive(int active);

    // Hent alle aktive konfigurationer
    List<GiftConfig> findAllByActive(int active);

    // Tjek om en specifik kombination af threshold og produktID allerede eksisterer
    Optional<GiftConfig> findByThresholdAmountAndGiftProductId(BigDecimal thresholdAmount, Long giftProductId);

    // Hent alle konfigurationer for en specifik threshold
    List<GiftConfig> findByThresholdAmount(BigDecimal thresholdAmount);
}
