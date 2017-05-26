package pl.mlata.reports.dto;

import java.math.BigDecimal;
import java.util.Date;

public class VatRegistryTableItem {
    private Integer position;
    private Date date;
    private String registry;
    private String number;
    private String contractor;
    private String description;
    private String vatBid;
    private BigDecimal nettoValue;
    private BigDecimal vatValue;

    public VatRegistryTableItem() {
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

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }
}
