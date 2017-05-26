package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kpir_registries")
public class KpirRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    @JsonIgnore
    private Company company;
    @Column(nullable = false)
    private Integer period;
    @Column(nullable = false)
    private Integer year;
    @OneToMany(mappedBy = "registry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<KpirRegistryEntry> entries = new ArrayList<>();

    public KpirRegistry() {
        this.id = 0L;
        this.company = null;
        this.period = 0;
        this.year = 0;
    }

    public KpirRegistry(Integer period, Integer year) {
        this.id = 0L;
        this.company = null;
        this.period = period;
        this.year = year;
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

    public List<KpirRegistryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<KpirRegistryEntry> entries) {
        this.entries = entries;
    }
}
