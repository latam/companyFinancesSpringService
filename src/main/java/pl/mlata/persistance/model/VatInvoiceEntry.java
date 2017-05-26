package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vat_invoice_entries")
public class VatInvoiceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private VatInvoice invoice;
    @Column(nullable = false)
    private Integer position;
    @Column(nullable = false, length = 512)
    private String name;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "unit_netto_price", nullable = false)
    private BigDecimal unitNettoValue;
    @Column(name = "netto_value", nullable = false)
    private BigDecimal nettoValue;
    @Column(name = "vat_bid", nullable = false)
    private Integer vatBid;

    public VatInvoiceEntry() {
    }

    public VatInvoiceEntry(VatInvoiceEntry vatInvoiceEntry) {
        this.id = vatInvoiceEntry.id;
        this.invoice = vatInvoiceEntry.invoice;
        this.position = vatInvoiceEntry.position;
        this.name = vatInvoiceEntry.name;
        this.unit = vatInvoiceEntry.unit;
        this.quantity = vatInvoiceEntry.quantity;
        this.unitNettoValue = vatInvoiceEntry.unitNettoValue;
        this.nettoValue = vatInvoiceEntry.nettoValue;
        this.vatBid = vatInvoiceEntry.vatBid;
    }

    /*public VatInvoiceEntry(VatInvoiceEntryInbound vatInvoiceEntry) {
        this.position = vatInvoiceEntry.getPosition();
        this.name = vatInvoiceEntry.getName();
        this.unit = vatInvoiceEntry.getUnit();
        this.quantity = vatInvoiceEntry.getQuantity();
        Double unitNettoValue = vatInvoiceEntry.getUnitNettoValue() * 100.0;
        this.unitNettoValue = unitNettoValue.longValue();
        this.vatBid = vatInvoiceEntry.getCaption();
    }*/

    public VatInvoiceEntry(VatInvoice invoice, Integer position, String name, String unit, Integer quantity, BigDecimal unitNettoPrice, BigDecimal nettoValue, Integer vatBid) {
        this.invoice = invoice;
        this.position = position;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.unitNettoValue = unitNettoPrice;
        this.nettoValue = nettoValue;
        this.vatBid = vatBid;
    }

    public Long getId() {
        return id;
    }

    public VatInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(VatInvoice invoice) {
        this.invoice = invoice;
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

    public BigDecimal getUnitNettoPrice() {
        return unitNettoValue;
    }

    public void setUnitNettoPrice(BigDecimal unitNettoPrice) {
        this.unitNettoValue = unitNettoPrice;
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
}
