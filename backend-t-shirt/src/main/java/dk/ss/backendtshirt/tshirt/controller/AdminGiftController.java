package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.GiftConfigRequest;
import dk.ss.backendtshirt.tshirt.dto.GiftConfigResponseDTO;
import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import dk.ss.backendtshirt.tshirt.service.GiftService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/admin/gift-config")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminGiftController {

    private final GiftService giftService;

    public AdminGiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    // POST: Opret ny gift konfiguration
    @PostMapping
    public ResponseEntity<?> createGiftConfig(@RequestBody GiftConfigRequest request) {
        // Validering
        if (request.getThresholdAmount() == null) {
            return ResponseEntity.badRequest().body("Fejl: thresholdAmount er påkrævet");
        }

        if (request.getGiftProductId() == null) {
            return ResponseEntity.badRequest().body("Fejl: giftProductId er påkrævet");
        }

        try {
            BigDecimal thresholdAmount = request.getThresholdAmount();
            Long giftProductId = request.getGiftProductId();
            int active = (request.getActive() != null) ? request.getActive() : 1;

            GiftConfigResponseDTO response = giftService.createGiftConfig(thresholdAmount, giftProductId, active);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fejl ved oprettelse: " + e.getMessage());
        }
    }

    // GET /all: Hent alle gift konfigurationer
    @GetMapping("/all")
    public ResponseEntity<List<GiftConfigResponseDTO>> getAllGiftConfigs() {
        try {
            List<GiftConfigResponseDTO> configs = giftService.getAllGiftConfigs();
            return ResponseEntity.ok(configs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE: Slet en specifik gift konfiguration
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftConfig(@PathVariable Long id) {
        try {
            giftService.deleteGiftConfig(id);
            return ResponseEntity.ok("Gift konfiguration med ID " + id + " blev slettet succesfuldt");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fejl ved sletning: " + e.getMessage());
        }
    }

    // GET: Hent den komplette konfiguration (LEGACY - til bagudkompatibilitet)
    @GetMapping
    public ResponseEntity<GiftConfig> getGiftConfig() {
        GiftConfig config = giftService.getGiftConfig();
        if (config == null) {
            // Returner en tom config hvis ingen findes
            config = new GiftConfig();
            config.setThresholdAmount(BigDecimal.ZERO);
            config.setActive(0);
        }
        return ResponseEntity.ok(config);
    }

    // PUT: Opdater den komplette konfiguration (LEGACY - til bagudkompatibilitet)
    @PutMapping
    public ResponseEntity<String> updateGiftConfig(@RequestBody GiftConfigRequest request) {

        // Validering
        if (request.getThresholdAmount() == null) {
            return ResponseEntity.badRequest().body("Fejl: thresholdAmount er påkrævet");
        }

        if (request.getGiftProductId() == null) {
            return ResponseEntity.badRequest().body("Fejl: giftProductId er påkrævet");
        }

        try {
            BigDecimal newAmount = request.getThresholdAmount();
            Long giftProductId = request.getGiftProductId();
            int active = (request.getActive() != null) ? request.getActive() : 1;

            giftService.updateGiftConfig(newAmount, giftProductId, active);
            return ResponseEntity.ok("Gave konfiguration opdateret succesfuldt! Beløb: " + newAmount +
                                   " kr, Produkt ID: " + giftProductId +
                                   ", Status: " + (active == 1 ? "Aktiv" : "Inaktiv"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fejl ved opdatering: " + e.getMessage());
        }
    }
}
