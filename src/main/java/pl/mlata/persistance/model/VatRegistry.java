package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vat_registries")
public class VatRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @ManyToOne(targetEntity = Company.class)
    @JsonIgnore
    @JoinColumn
    private Company company;
    @Column(nullable = false)
    private Integer period;
    @Column(nullable = false)
    private Integer year;
    @OneToMany(mappedBy = "registry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //@JsonManagedReference
    @JsonIgnore
    private List<VatRegistryEntry> entries = new ArrayList<>();

    public VatRegistry() {
    }

    public VatRegistry(VatRegistry vatRegistry) {
        this.id = vatRegistry.id;
        this.company = vatRegistry.company;
        this.period = vatRegistry.period;
        this.year = vatRegistry.year;
        this.entries = vatRegistry.entries;
    }

    public VatRegistry(Company company, Integer period, Integer year, Boolean closed, List<VatRegistryEntry> entries) {
        this.company = company;
        this.period = period;
        this.year = year;
        this.entries = entries;
    }

    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<VatRegistryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<VatRegistryEntry> entries) {
        this.entries = entries;
    }
}
