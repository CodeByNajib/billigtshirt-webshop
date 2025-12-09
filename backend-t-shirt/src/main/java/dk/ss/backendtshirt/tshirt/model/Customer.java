package dk.ss.backendtshirt.tshirt.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id") // Matcher vores ERD
    private int id;

    private String email;
    private String name;
    private String password;
    private String address;
    private String city;

    @Column(name = "postal_code") // Mapper 'postalCode' til 'postal_code' i DB
    private String postalCode;

    private String phone;

    // --- RELATIONER ---

    // En kunde kan have mange ordrer
    // "mappedBy" fortæller, at det er 'customer' feltet ovre i Order-klassen, der ejer relationen
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    // Vi har også en relation til Cart i vores ERD, men vi venter med den,
    // indtil vi har Cart-klassen helt på plads, så vi ikke får flere fejl nu

    // --- CONSTRUCTORS, GETTERS & SETTERS ---


    public Customer() {
    }

    public Customer(int id, String email, String name, String password, String address, String city, String postalCode, String phone, List<Order> orders) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
