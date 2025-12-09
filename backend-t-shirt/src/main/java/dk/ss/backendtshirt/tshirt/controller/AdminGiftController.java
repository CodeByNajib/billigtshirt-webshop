package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.service.GiftService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/api/admin/gift-config")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminGiftController {


    private GiftService giftService;

    public AdminGiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    // GET: Hent den nuværende grænse
    @GetMapping
    public ResponseEntity<BigDecimal> getThreshold() {
        return ResponseEntity.ok(giftService.getCurrentThreshold());
    }

    // PUT: Opdater grænsen
    @PutMapping
    public ResponseEntity<String> updateThreshold(@RequestBody Map<String, BigDecimal> payload) {

        // Nu "fisker" vi tallet ud ved at bruge nøglen "thresholdAmount"
        BigDecimal newAmount = payload.get("thresholdAmount");

        // Tjek om vi fik noget (hvis man har stavet forkert i Postman)
        if (newAmount == null) {
            return ResponseEntity.badRequest().body("Fejl: JSON skal indeholde feltet 'thresholdAmount'");
        }

        try {
            giftService.updateThreshold(newAmount);
            return ResponseEntity.ok("Beløbsgrænse opdateret til: " + newAmount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
