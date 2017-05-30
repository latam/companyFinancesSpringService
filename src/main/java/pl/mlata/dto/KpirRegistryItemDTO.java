package pl.mlata.dto;

import pl.mlata.persistance.model.KpirRegistryEntry;

import java.math.BigDecimal;
import java.util.Date;

public class KpirRegistryItemDTO extends KpirFinancialColumns {
    private Integer position;
    private Date date;
    private String number;
    private String contractorName;
    private String contractorAddress;
    private String description;
    private String points;

    public KpirRegistryItemDTO() {
        super();
    }

    public KpirRegistryItemDTO(KpirRegistryEntry kpirRegistryEntry) {
        this.position = kpirRegistryEntry.getPosition();
        this.date = java.sql.Date.valueOf(kpirRegistryEntry.getDate());
        this.number = kpirRegistryEntry.getDocNumber();
        this.contractorName = kpirRegistryEntry.getContractor().getName();
        this.contractorAddress = kpirRegistryEntry.getContractor().getAddress();
        this.description = kpirRegistryEntry.getDescription();
        this.revProductServices = kpirRegistryEntry.getRevProductServices();
        this.revOther = kpirRegistryEntry.getRevOther();
        this.revCombined = kpirRegistryEntry.getRevCombined();
        this.purchaseGoodsMaterials = kpirRegistryEntry.getPurchaseGoodsMaterials();
        this.insuranceCost = kpirRegistryEntry.getInsuranceCost();
        this.expPayment = kpirRegistryEntry.getExpPayment();
        this.expOther = kpirRegistryEntry.getExpOther();
        this.expCombined = kpirRegistryEntry.getExpCombined();
        this.points = "";
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getContractorAddress() {
        return contractorAddress;
    }

    public void setContractorAddress(String contractorAddress) {
        this.contractorAddress = contractorAddress;
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
}
