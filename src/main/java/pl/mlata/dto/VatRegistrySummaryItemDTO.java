package pl.mlata.dto;

import java.math.BigDecimal;

public class VatRegistrySummaryItemDTO {
    private String vatBid;
    private BigDecimal expNettoValue;
    private BigDecimal expTaxValue;
    private BigDecimal expBruttoValue;

    private BigDecimal revNettoValue;
    private BigDecimal revTaxValue;
    private BigDecimal revBruttoValue;

    public VatRegistrySummaryItemDTO() {
        vatBid = "";
        this.expNettoValue = new BigDecimal(0);
        this.expTaxValue = new BigDecimal(0);
        this.expBruttoValue = new BigDecimal(0);
        this.revNettoValue = new BigDecimal(0);
        this.revTaxValue = new BigDecimal(0);
        this.revBruttoValue = new BigDecimal(0);
    }

    public void Summ(VatRegistrySummaryItemDTO item) {
        this.expNettoValue = this.expNettoValue.add(item.getExpNettoValue());
        this.expTaxValue = this.expTaxValue.add(item.getExpTaxValue());
        this.expBruttoValue = this.expBruttoValue.add(item.getExpBruttoValue());
        this.revNettoValue = this.revNettoValue.add(item.getRevNettoValue());
        this.revTaxValue = this.revTaxValue.add(item.getRevTaxValue());
        this.revBruttoValue = this.revBruttoValue.add(item.getRevBruttoValue());
    }

    public String getVatBid() {
        return vatBid;
    }

    public void setVatBid(String vatBid) {
        this.vatBid = vatBid;
    }

    public BigDecimal getExpNettoValue() {
        return expNettoValue;
    }

    public void setExpNettoValue(BigDecimal expNettoValue) {
        this.expNettoValue = expNettoValue;
    }

    public BigDecimal getExpTaxValue() {
        return expTaxValue;
    }

    public void setExpTaxValue(BigDecimal expTaxValue) {
        this.expTaxValue = expTaxValue;
    }

    public BigDecimal getExpBruttoValue() {
        return expBruttoValue;
    }

    public void setExpBruttoValue(BigDecimal expBruttoValue) {
        this.expBruttoValue = expBruttoValue;
    }

    public BigDecimal getRevNettoValue() {
        return revNettoValue;
    }

    public void setRevNettoValue(BigDecimal revNettoValue) {
        this.revNettoValue = revNettoValue;
    }

    public BigDecimal getRevTaxValue() {
        return revTaxValue;
    }

    public void setRevTaxValue(BigDecimal revTaxValue) {
        this.revTaxValue = revTaxValue;
    }

    public BigDecimal getRevBruttoValue() {
        return revBruttoValue;
    }

    public void setRevBruttoValue(BigDecimal revBruttoValue) {
        this.revBruttoValue = revBruttoValue;
    }
}
