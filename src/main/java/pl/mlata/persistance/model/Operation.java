package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Column(name = "doc_number", nullable = false)
    private String docNumber;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    @JsonIgnore
    private Company company;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    private Company contractor;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Boolean purchase;
    @Column(nullable = false)
    private String type;
    @Column
    private Integer kpirColumn;
    @Column(nullable = false)
    private String vatBid;
    @Column(nullable = false)
    private BigDecimal nettoValue;
    @OneToOne(targetEntity = VatRegistryEntry.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "vat_registry_entry_id")
    @JsonIgnore
    private VatRegistryEntry vatRegistryEntry;
    @OneToOne(targetEntity = KpirRegistryEntry.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "kpir_registry_entry_id")
    @JsonIgnore
    private KpirRegistryEntry kpirRegistryEntry;

    public Operation() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Company getContractor() {
        return contractor;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPurchase() {
        return purchase;
    }

    public void setPurchase(Boolean purchase) {
        this.purchase = purchase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVatBid() {
        return vatBid;
    }

    public void setVatBid(String vatBid) {
        this.vatBid = vatBid;
    }

    public BigDecimal getNettoValue() {
        return nettoValue;
    }

    public void setNettoValue(BigDecimal nettoValue) {
        this.nettoValue = nettoValue;
    }

    public Integer getKpirColumn() {
        return kpirColumn;
    }

    public void setKpirColumn(Integer kpirColumn) {
        this.kpirColumn = kpirColumn;
    }

    public VatRegistryEntry getVatRegistryEntry() {
        return vatRegistryEntry;
    }

    public void setVatRegistryEntry(VatRegistryEntry vatRegistryEntry) {
        this.vatRegistryEntry = vatRegistryEntry;
    }

    public KpirRegistryEntry getKpirRegistryEntry() {
        return kpirRegistryEntry;
    }

    public void setKpirRegistryEntry(KpirRegistryEntry kpirRegistryEntry) {
        this.kpirRegistryEntry = kpirRegistryEntry;
    }
}
