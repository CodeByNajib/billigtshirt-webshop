package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.model.Customer;
import dk.ss.backendtshirt.tshirt.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    // Constructor injection foretrækkes frem for @Autowired på felter
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // --- CRUD METODER ---

    // Hent alle kunder -- READ
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Hent specifik kunde ud fra ID
    // Returnerer Optional, så controlleren kan håndtere 404
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    //Opret ny kunde -- CREATE
    // TODO: Implementere password hashing (f.eks. BCrypt) før vi gemmer.
    public Customer createCustomer(Customer customer) {
        // Tjek om email allerede findes (Business Rule)
        if(customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email findes allrede i systemet: " +customer.getAddress());
        }
        return customerRepository.save(customer);
    }


    // Opdater eksisterende kunde -- UPDATE
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setAddress(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhone(updatedCustomer.getAddress());
            existingCustomer.setCity(updatedCustomer.getCity());
            existingCustomer.setPostalCode(updatedCustomer.getPostalCode());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            // Vi opdaterer ikke password her uden videre sikkerhedsjek
            return customerRepository.save(existingCustomer);
        }) .orElse(null); // Returnerer null hvis ID ikke findes
    }

    // Slet kunde -- DELETE
    public boolean deleteCustomer(Long id) {
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
