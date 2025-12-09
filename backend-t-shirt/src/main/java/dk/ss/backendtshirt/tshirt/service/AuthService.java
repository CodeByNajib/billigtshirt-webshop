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

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;

    public AuthResponseDTO signup(SignupRequestDTO request) {
        // Check if email already exists in either Customer or Admin table
        if (customerRepository.existsByEmail(request.getEmail()) ||
            adminRepository.existsByEmail(request.getEmail())) {
            AuthResponseDTO response = new AuthResponseDTO();
            response.setMessage("Email allerede i brug");
            return response;
        }

        // Create new customer (signup is only for customers)
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setName(request.getFirstname() + " " + request.getLastname());
        customer.setPassword(request.getPassword()); // Note: In production, hash this password!

        // Save customer
        Customer savedCustomer = customerRepository.save(customer);

        // Generate simple token (in production, use JWT)
        String token = UUID.randomUUID().toString();

        // Return success response
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
        // Try to find user in Customer table first
        Optional<Customer> customerOpt = customerRepository.findByEmail(request.getEmail());

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            // Check password
            if (!customer.getPassword().equals(request.getPassword())) {
                AuthResponseDTO response = new AuthResponseDTO();
                response.setMessage("Forkert email eller adgangskode");
                return response;
            }

            // Generate token
            String token = UUID.randomUUID().toString();

            // Return success response for Customer
            return new AuthResponseDTO(
                    customer.getId(),
                    customer.getEmail(),
                    customer.getName(),
                    "CUSTOMER",
                    token,
                    "Login succesfuld"
            );
        }

        // Try to find user in Admin table
        Optional<Admin> adminOpt = adminRepository.findByEmail(request.getEmail());

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            // Check password
            if (!admin.getPassword().equals(request.getPassword())) {
                AuthResponseDTO response = new AuthResponseDTO();
                response.setMessage("Forkert email eller adgangskode");
                return response;
            }

            // Generate token
            String token = UUID.randomUUID().toString();

            // Return success response for Admin
            return new AuthResponseDTO(
                    admin.getId(),
                    admin.getEmail(),
                    admin.getName(),
                    "ADMIN",
                    token,
                    "Login succesfuld"
            );
        }

        // User not found in either table
        AuthResponseDTO response = new AuthResponseDTO();
        response.setMessage("Forkert email eller adgangskode");
        return response;
    }
}

