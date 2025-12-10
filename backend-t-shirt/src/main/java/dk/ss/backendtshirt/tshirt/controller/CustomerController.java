package dk.ss.backendtshirt.tshirt.controller;

import dk.ss.backendtshirt.tshirt.model.Customer;
import dk.ss.backendtshirt.tshirt.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers") // Base URL for alle endpoints her
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET: Hent alle -- READ --
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }

    // GET: Hent enkelt kunde -- READ --
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST: Opret kunde -- CREATE --
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try{
            Customer newCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(newCustomer,HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            // Returner 400 Bad Request hvis email findes
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // PUT: Opdater kunde -- UPDATE --
    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updated = customerService.updateCustomer(id, customer);
        if(updated != null){
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Slet kunde
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if(customerService.deleteCustomer(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
