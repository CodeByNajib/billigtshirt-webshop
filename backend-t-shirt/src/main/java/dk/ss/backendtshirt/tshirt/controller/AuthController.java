package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.dto.AuthResponseDTO;
import dk.ss.backendtshirt.tshirt.dto.LoginRequestDTO;
import dk.ss.backendtshirt.tshirt.dto.SignupRequestDTO;
import dk.ss.backendtshirt.tshirt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:80", "http://127.0.0.1:80"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody SignupRequestDTO request) {
        AuthResponseDTO response = authService.signup(request);

        if (response.getToken() == null) {
            // Signup failed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);

        if (response.getToken() == null) {
            // Login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
