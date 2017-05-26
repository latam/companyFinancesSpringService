package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String nip;

    @Column(nullable = false)
    private String regon;

    @Column(nullable = false)
    private Boolean contractor;

    @Column
    @JsonIgnore
    private Boolean active;

    public Company() {
    }

    public Company(User user, String name, String city, String postalCode, String street, String nip, String regon, Boolean contractor, Boolean active) {
        this.user = user;
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.nip = nip;
        this.regon = regon;
        this.contractor = contractor;
        this.active = active;
    }

    public Company(Company company) {
        this.user = company.user;
        this.name = company.name;
        this.city = company.city;
        this.postalCode = company.postalCode;
        this.street = company.street;
        this.nip = company.nip;
        this.regon = company.regon;
        this.contractor = company.contractor;
        this.active = company.active;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public Boolean isContractor() {
        return contractor;
    }

    public void setContractor(Boolean contractor) {
        this.contractor = contractor;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return street + ", " + postalCode + " " + city;
    }
}
