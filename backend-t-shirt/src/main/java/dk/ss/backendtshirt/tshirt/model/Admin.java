package dk.ss.backendtshirt.tshirt.model;

import dk.ss.backendtshirt.common.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN")
public class Admin extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    // --- CONSTRUCTORS, GETTERS & SETTERS ---

    public Admin() {
    }

    public Admin(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
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
}
