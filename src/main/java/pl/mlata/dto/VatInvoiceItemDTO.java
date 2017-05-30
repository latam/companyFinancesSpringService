package pl.mlata.dto;

import pl.mlata.persistance.model.VatInvoiceEntry;

import java.math.BigDecimal;

public class VatInvoiceItemDTO {
    private Integer position;
    private String name;
    private String unit;
    private Integer quantity;
    private BigDecimal unitNettoValue;
    private BigDecimal nettoValue;
    private Integer vatBid;
    private BigDecimal taxValue;
    private BigDecimal bruttoValue;

    public VatInvoiceItemDTO() {
    }

    public VatInvoiceItemDTO(VatInvoiceEntry invoiceEntry) {
        name = invoiceEntry.getName();
        position = invoiceEntry.getPosition();
        unit = invoiceEntry.getUnit();
        quantity = invoiceEntry.getQuantity();
        unitNettoValue = invoiceEntry.getUnitNettoPrice();

        nettoValue = unitNettoValue.multiply(new BigDecimal(quantity));

        vatBid = invoiceEntry.getVatBid();
        taxValue = nettoValue.multiply(new BigDecimal(vatBid));

        bruttoValue = nettoValue.add(taxValue);
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitNettoValue() {
        return unitNettoValue;
    }

    public void setUnitNettoValue(BigDecimal unitNettoValue) {
        this.unitNettoValue = unitNettoValue;
    }

    public BigDecimal getNettoValue() {
        return nettoValue;
    }

    public void setNettoValue(BigDecimal nettoValue) {
        this.nettoValue = nettoValue;
    }

    public Integer getVatBid() {
        return vatBid;
    }

    public void setVatBid(Integer vatBid) {
        this.vatBid = vatBid;
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
