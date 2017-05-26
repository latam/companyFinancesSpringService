package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "kpir_registry_entries")
public class KpirRegistryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private KpirRegistry registry;
    @Column(nullable = false)
    private Integer position;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Column(name = "doc_number", nullable = false)
    private String docNumber;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    private Company contractor;
    @Column(nullable = false)
    private String description;
    @Column(name = "rev_product_services", nullable = false)
    private BigDecimal revProductServices;
    @Column(name = "rev_other", nullable = false)
    private BigDecimal revOther;
    @Column(name = "rev_combined", nullable = false)
    private BigDecimal revCombined;
    @Column(name = "purchase_goods_materials", nullable = false)
    private BigDecimal purchaseGoodsMaterials;
    @Column(name = "insurance_cost", nullable = false)
    private BigDecimal insuranceCost;
    @Column(name = "exp_payment", nullable = false)
    private BigDecimal expPayment;
    @Column(name = "exp_other", nullable = false)
    private BigDecimal expOther;
    @Column(name = "exp_combined", nullable = false)
    private BigDecimal expCombined;
    @Column(nullable = false)
    private String points;

    public KpirRegistryEntry() {
        this.revProductServices = new BigDecimal(0.0);
        this.revOther = new BigDecimal(0.0);
        this.revCombined = new BigDecimal(0.0);
        this.purchaseGoodsMaterials = new BigDecimal(0.0);
        this.insuranceCost = new BigDecimal(0.0);
        this.expPayment = new BigDecimal(0.0);
        this.expOther = new BigDecimal(0.0);
        this.expCombined = new BigDecimal(0.0);
        this.points = "";
    }

    public Long getId() {
        return id;
    }

    public KpirRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(KpirRegistry registry) {
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

    public BigDecimal getRevProductServices() {
        return revProductServices;
    }

    public void setRevProductServices(BigDecimal revProductServices) {
        this.revProductServices = revProductServices;
    }

    public BigDecimal getRevOther() {
        return revOther;
    }

    public void setRevOther(BigDecimal revOther) {
        this.revOther = revOther;
    }

    public BigDecimal getRevCombined() {
        return revCombined;
    }

    public void setRevCombined(BigDecimal revCombined) {
        this.revCombined = revCombined;
    }

    public BigDecimal getPurchaseGoodsMaterials() {
        return purchaseGoodsMaterials;
    }

    public void setPurchaseGoodsMaterials(BigDecimal purchaseGoodsMaterials) {
        this.purchaseGoodsMaterials = purchaseGoodsMaterials;
    }

    public BigDecimal getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(BigDecimal insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public BigDecimal getExpPayment() {
        return expPayment;
    }

    public void setExpPayment(BigDecimal expPayment) {
        this.expPayment = expPayment;
    }

    public BigDecimal getExpOther() {
        return expOther;
    }

    public void setExpOther(BigDecimal expOther) {
        this.expOther = expOther;
    }

    public BigDecimal getExpCombined() {
        return expCombined;
    }

    public void setExpCombined(BigDecimal expCombined) {
        this.expCombined = expCombined;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void recalculateExpCombined() {
        expCombined = expPayment.add(expOther);
    }

    public void recalculateRevCombined() {
        revCombined = revProductServices.add(revOther);
    }
}
