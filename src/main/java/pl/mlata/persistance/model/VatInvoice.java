package pl.mlata.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vat_invoices")
public class VatInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    private Company company;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn
    private Company contractor;
    @Column(name = "sell_date")
    private LocalDate sellDate;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<VatInvoiceEntry> entries = new ArrayList<>();
    @Column
    private String number;
    @Column
    private Boolean purchase;
    @JsonIgnore
    @Column(name = "pdf_report", length = 200000)
    private byte[] pdfReport;

    public VatInvoice() {
    }

    public VatInvoice(VatInvoice vatInvoice) {
        this.id = vatInvoice.id;
        this.company = vatInvoice.company;
        this.contractor = vatInvoice.contractor;
        this.sellDate = vatInvoice.sellDate;
        this.releaseDate = vatInvoice.releaseDate;
        this.entries = vatInvoice.entries;
        this.number = vatInvoice.number;
        this.purchase = vatInvoice.purchase;
        this.pdfReport = vatInvoice.pdfReport;
    }
    /*
    public VatInvoice(VatInvoiceInbound vatInvoice) {
        this.id = vatInvoice.getId();
        this.sellDate = vatInvoice.getSellDate();
        this.releaseDate = vatInvoice.getReleaseDate();
        this.number = vatInvoice.getNumber();
        this.purchase = vatInvoice.getPurchase();
    }*/

    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getContractor() {
        return contractor;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }

    public LocalDate getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<VatInvoiceEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<VatInvoiceEntry> entries) {
        this.entries = entries;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getPurchase() {
        return purchase;
    }

    public void setPurchase(Boolean purchase) {
        this.purchase = purchase;
    }

    public byte[] getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(byte[] pdfReport) {
        this.pdfReport = pdfReport;
    }
}
