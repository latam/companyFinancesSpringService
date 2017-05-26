package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vat_registry_entries")
public class VatRegistryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    //@JsonBackReference
    private VatRegistry registry;
    @Column(name = "position", nullable = false)
    private Integer position;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Column(name = "purchase", nullable = false)
    private Boolean purchase;
    @Column(name = "doc_number", nullable = false)
    private String docNumber;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    private Company contractor;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "bid", nullable = false)
    private String bid;
    @Column(name = "netto_value", nullable = false)
    private BigDecimal nettoValue;
    @Column(name = "vat_value", nullable = false)
    private BigDecimal vatValue;

    public VatRegistryEntry() {
    }

    public VatRegistryEntry(VatRegistryEntry vatRegistryEntry) {
        this.id = vatRegistryEntry.id;
        this.registry = vatRegistryEntry.registry;
        this.position = vatRegistryEntry.position;
        this.date = vatRegistryEntry.date;
        this.purchase = vatRegistryEntry.purchase;
        this.docNumber = vatRegistryEntry.docNumber;
        this.contractor = vatRegistryEntry.contractor;
        this.description = vatRegistryEntry.description;
        this.bid = vatRegistryEntry.bid;
        this.nettoValue = vatRegistryEntry.nettoValue;
        this.vatValue = vatRegistryEntry.vatValue;
    }

    public VatRegistryEntry(VatRegistry registry, Integer position, LocalDate date, Boolean purchase, String docNumber, Company contractor, String description, String bid, BigDecimal nettoValue, BigDecimal vatValue) {
        this.registry = registry;
        this.position = position;
        this.date = date;
        this.purchase = purchase;
        this.docNumber = docNumber;
        this.contractor = contractor;
        this.description = description;
        this.bid = bid;
        this.nettoValue = nettoValue;
        this.vatValue = vatValue;
    }

    public Long getId() {
        return id;
    }

    public VatRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(VatRegistry registry) {
        this.registry = registry;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(Boolean purchase) {
        this.purchase = purchase;
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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public BigDecimal getNettoValue() {
        return nettoValue;
    }

    public void setNettoValue(BigDecimal nettoValue) {
        this.nettoValue = nettoValue;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }
}
