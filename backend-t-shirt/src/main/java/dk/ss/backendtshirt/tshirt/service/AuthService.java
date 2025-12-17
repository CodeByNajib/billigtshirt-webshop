package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.dto.AuthResponseDTO;
import dk.ss.backendtshirt.tshirt.dto.LoginRequestDTO;
import dk.ss.backendtshirt.tshirt.dto.SignupRequestDTO;
import dk.ss.backendtshirt.tshirt.model.Admin;
import dk.ss.backendtshirt.tshirt.model.Customer;
import dk.ss.backendtshirt.tshirt.repository.AdminRepository;
import dk.ss.backendtshirt.tshirt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Transactional
    public AuthResponseDTO signup(SignupRequestDTO request) {
        // 1. Tjek om email allerede findes i Customer eller Admin tabellen
        if (customerRepository.existsByEmail(request.getEmail()) ||
                adminRepository.existsByEmail(request.getEmail())) {
            AuthResponseDTO response = new AuthResponseDTO();
            response.setMessage("Email allerede i brug");
            return response;
        }

        // 2. Opret ny kunde (signup er kun for kunder)
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setName(request.getFirstname() + " " + request.getLastname());
        customer.setPassword(request.getPassword()); // Husk hashing i produktion!

        // --- NYT: Overfør adresseoplysninger fra DTO til Entitet ---
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setPostalCode(request.getPostalCode());
        customer.setPhone(request.getPhone());

        // 3. Gem kunden i databasen
        Customer savedCustomer = customerRepository.save(customer);

        // 4. Generer token
        String token = UUID.randomUUID().toString();

        // 5. Returner succes-respons
        return new AuthResponseDTO(
                savedCustomer.getId(),
                savedCustomer.getEmail(),
                savedCustomer.getName(),
                "CUSTOMER",
                token,
                "Registrering succesfuld"
        );
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        // Prøv at finde brugeren i Customer-tabellen først
        Optional<Customer> customerOpt = customerRepository.findByEmail(request.getEmail());

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            if (!customer.getPassword().equals(request.getPassword())) {
                AuthResponseDTO response = new AuthResponseDTO();
                response.setMessage("Forkert email eller adgangskode");
                return response;
            }

            String token = UUID.randomUUID().toString();

            return new AuthResponseDTO(
                    customer.getId(),
                    customer.getEmail(),
                    customer.getName(),
                    "CUSTOMER",
                    token,
                    "Login succesfuld"
            );
        }

        // Prøv at finde brugeren i Admin-tabellen
        Optional<Admin> adminOpt = adminRepository.findByEmail(request.getEmail());

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            if (!admin.getPassword().equals(request.getPassword())) {
                AuthResponseDTO response = new AuthResponseDTO();
                response.setMessage("Forkert email eller adgangskode");
                return response;
            }

            String token = UUID.randomUUID().toString();

            return new AuthResponseDTO(
                    admin.getId(),
                    admin.getEmail(),
                    admin.getName(),
                    "ADMIN",
                    token,
                    "Login succesfuld"
            );
        }

        AuthResponseDTO response = new AuthResponseDTO();
        response.setMessage("Forkert email eller adgangskode");
        return response;
    }
}

