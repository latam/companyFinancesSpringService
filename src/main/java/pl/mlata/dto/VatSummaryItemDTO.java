package pl.mlata.dto;

import java.math.BigDecimal;

public class VatSummaryItemDTO {
    private BigDecimal nettoValue;
    private String caption;
    private BigDecimal taxValue;
    private BigDecimal bruttoValue;

    public VatSummaryItemDTO() {
        nettoValue = new BigDecimal(0);
        taxValue = new BigDecimal(0);
        bruttoValue = new BigDecimal(0);
        caption = "";
    }

    public void Summ(VatSummaryItemDTO vatSummaryItemDTO) {
        this.nettoValue = this.nettoValue.add(vatSummaryItemDTO.getNettoValue());
        this.taxValue = this.taxValue.add(vatSummaryItemDTO.getTaxValue());
        this.bruttoValue = this.bruttoValue.add(vatSummaryItemDTO.getBruttoValue());
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
