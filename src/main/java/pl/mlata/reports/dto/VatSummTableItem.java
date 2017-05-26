package pl.mlata.reports.dto;

import java.math.BigDecimal;

public class VatSummTableItem {
    private BigDecimal nettoValue;
    private String caption;
    private BigDecimal taxValue;
    private BigDecimal bruttoValue;

    public VatSummTableItem() {
        nettoValue = new BigDecimal(0);
        taxValue = new BigDecimal(0);
        bruttoValue = new BigDecimal(0);
        caption = "";
    }

    public void Summ(VatSummTableItem vatSummTableItem) {
        this.nettoValue = this.nettoValue.add(vatSummTableItem.getNettoValue());
        this.taxValue = this.taxValue.add(vatSummTableItem.getTaxValue());
        this.bruttoValue = this.bruttoValue.add(vatSummTableItem.getBruttoValue());
    }

    public BigDecimal getNettoValue() {
        return nettoValue;
    }

    public void setNettoValue(BigDecimal nettoValue) {
        this.nettoValue = nettoValue;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getBruttoValue() {
        return bruttoValue;
    }

    public void setBruttoValue(BigDecimal bruttoValue) {
        this.bruttoValue = bruttoValue;
    }
}
