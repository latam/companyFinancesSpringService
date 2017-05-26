package pl.mlata.reports.dto;

import java.math.BigDecimal;

public class KpirSummTableItem extends KpirFinancialColumns {
    private String caption;

    public KpirSummTableItem() {
        caption = "";
        revProductServices = new BigDecimal(0.0);
        revOther = new BigDecimal(0.0);
        revCombined = new BigDecimal(0.0);
        purchaseGoodsMaterials = new BigDecimal(0.0);
        insuranceCost = new BigDecimal(0.0);
        expPayment = new BigDecimal(0.0);
        expOther = new BigDecimal(0.0);
        expCombined = new BigDecimal(0.0);
    }

    public KpirSummTableItem(String caption, KpirSummTableItem item) {
        this.caption = caption;
        this.revProductServices = item.revProductServices;
        this.revOther = item.revOther;
        this.revCombined = item.revCombined;
        this.purchaseGoodsMaterials = item.purchaseGoodsMaterials;
        this.insuranceCost = item.insuranceCost;
        this.expPayment = item.expPayment;
        this.expOther = item.expOther;
        this.expCombined = item.expCombined;
    }

    public void Summ(KpirSummTableItem item) {
        this.revProductServices = this.revProductServices.add(item.revProductServices);
        this.revOther =  this.revOther.add(item.revOther);
        this.revCombined = this.revCombined.add(item.revCombined);
        this.purchaseGoodsMaterials = this.purchaseGoodsMaterials.add(item.purchaseGoodsMaterials);
        this.insuranceCost = this.insuranceCost.add(item.insuranceCost);
        this.expPayment = this.expPayment.add(item.expPayment);
        this.expOther = this.expOther.add(item.expOther);
        this.expCombined = this.expCombined.add(item.expCombined);
    }

    public void Summ(KpirRegistryTableItem item) {
        this.revProductServices = this.revProductServices.add(item.getRevProductServices());
        this.revOther =  this.revOther.add(item.getRevOther());
        this.revCombined = this.revCombined.add(item.getRevCombined());
        this.purchaseGoodsMaterials = this.purchaseGoodsMaterials.add(item.getPurchaseGoodsMaterials());
        this.insuranceCost = this.insuranceCost.add(item.getInsuranceCost());
        this.expPayment = this.expPayment.add(item.getExpPayment());
        this.expOther = this.expOther.add(item.getExpOther());
        this.expCombined = this.expCombined.add(item.getExpCombined());
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
}
