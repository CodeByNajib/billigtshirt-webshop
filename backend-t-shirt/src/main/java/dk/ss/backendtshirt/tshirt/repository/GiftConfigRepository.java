package dk.ss.backendtshirt.tshirt.repository;

import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftConfigRepository extends JpaRepository<GiftConfig,Integer> {
    // Vi antager, at der kun er én aktiv config, eller vi henter den med ID 1
    GiftConfig findFirstByActive(int active);

}
