package fruteria.tropical.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String region;
    private String phone;

    public Supplier() {}

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
