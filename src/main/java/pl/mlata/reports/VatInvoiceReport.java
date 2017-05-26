package pl.mlata.reports;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.VatInvoice;
import pl.mlata.persistance.model.VatInvoiceEntry;
import pl.mlata.reports.dto.VatInvoiceTableItem;
import pl.mlata.reports.dto.VatSummTableItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VatInvoiceReport {
    public byte[] createReport(VatInvoice invoiceData) {
        Map<String, Object> parameters = getReportParameters(invoiceData);
        byte[] pdfReport = ReportUtils.getVatInvoiceReportAsBytes(parameters);

        return pdfReport;
    }

    private Map<String, Object> getReportParameters(VatInvoice invoiceData) {
        Map<String, Object> parameters = new HashMap<>();

        Map<String, Object> companiesParameters = getHeaderParameters(invoiceData);
        parameters.putAll(companiesParameters);

        // FIXME: Add bank accounts for user
        parameters.put(ReportUtils.VatInvoiceParams.bankName, "mBank");
        parameters.put(ReportUtils.VatInvoiceParams.accountNumber, "21 1140 2004 0000 3102 7586 9391");

        List<VatInvoiceTableItem> tableItems = getTableItems(invoiceData);
        List<VatSummTableItem> summTableItems = getSummTableItems(tableItems);
        VatSummTableItem summTableItem = summTableItems.get(summTableItems.size()-1);

        Map<String, Object> bruttoValueParameters = getBruttoValueParameters(summTableItem);
        parameters.putAll(bruttoValueParameters);

        JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableItems, false);
        JRBeanCollectionDataSource summTableDataSource = new JRBeanCollectionDataSource(summTableItems, false);

        parameters.put(ReportUtils.VatInvoiceParams.tableDataSource, tableDataSource);
        parameters.put(ReportUtils.VatInvoiceParams.summTableDataSource, summTableDataSource);

        return parameters;
    }

    private Map<String, Object> getHeaderParameters(VatInvoice invoiceData) {
        Map<String, Object> parameters = new HashMap<>();

        Company company = invoiceData.getCompany();
        Company contractor = invoiceData.getContractor();

        parameters.put(ReportUtils.VatInvoiceParams.number, invoiceData.getNumber());
        parameters.put(ReportUtils.VatInvoiceParams.releaseDate, invoiceData.getReleaseDate());
        parameters.put(ReportUtils.VatInvoiceParams.sellDate, invoiceData.getSellDate());
        parameters.put(ReportUtils.VatInvoiceParams.sellPlace, company.getCity());

        parameters.put(ReportUtils.CompanyParams.name, company.getName());
        parameters.put(ReportUtils.CompanyParams.street, company.getStreet());
        parameters.put(ReportUtils.CompanyParams.city, company.getPostalCode() + " " + company.getCity());
        parameters.put(ReportUtils.CompanyParams.nip, company.getNip());
        parameters.put(ReportUtils.CompanyParams.regon, company.getRegon());

        parameters.put(ReportUtils.ContractorParams.name, contractor.getName());
        parameters.put(ReportUtils.ContractorParams.street, contractor.getStreet());
        parameters.put(ReportUtils.ContractorParams.city, contractor.getPostalCode() + " " + contractor.getCity());
        parameters.put(ReportUtils.ContractorParams.nip, contractor.getNip());
        parameters.put(ReportUtils.ContractorParams.regon, contractor.getRegon());

        return parameters;
    }

    private List<VatInvoiceTableItem> getTableItems(VatInvoice invoiceData) {
        List<VatInvoiceTableItem> tableItems = new ArrayList<>();
        VatInvoiceTableItem item;
        for (VatInvoiceEntry invoiceEntry : invoiceData.getEntries()) {
            item = new VatInvoiceTableItem(invoiceEntry);
            tableItems.add(item);
        }
        return tableItems;
    }

    private List<VatSummTableItem> getSummTableItems(List<VatInvoiceTableItem> tableItems) {
        List<VatSummTableItem> summTableItems = new ArrayList<>();
        Map<Integer, BigDecimal> vatSummMap = getVatSummMap(tableItems);

        VatSummTableItem total = new VatSummTableItem();
        total.setCaption("Razem");

        for (Integer vatBid : vatSummMap.keySet()) {
            VatSummTableItem item = new VatSummTableItem();

            BigDecimal nettoValue = vatSummMap.get(vatBid);
            BigDecimal taxValue = nettoValue.multiply(new BigDecimal(vatBid));
            BigDecimal bruttoValue = nettoValue.add(taxValue);

            item.setNettoValue(nettoValue);
            item.setTaxValue(taxValue);
            item.setBruttoValue(bruttoValue);

            String vatBidText = getVatBidText(vatBid.toString());
            item.setCaption(vatBidText);

            total.Summ(item);
            summTableItems.add(item);
        }

        summTableItems.add(total);
        return summTableItems;
    }

    private Map<Integer, BigDecimal> getVatSummMap(List<VatInvoiceTableItem> tableItems) {
        Map<Integer, BigDecimal> vatSummMap = new HashMap<>();

        for(VatInvoiceTableItem item : tableItems) {
            Integer vatBid = item.getVatBid();
            BigDecimal nettoValue = item.getNettoValue();

            if (vatSummMap.containsKey(vatBid)) {
                BigDecimal currentVatTotal = vatSummMap.get(vatBid);
                currentVatTotal = currentVatTotal.add(nettoValue);
                vatSummMap.put(vatBid, currentVatTotal);
            } else {
                vatSummMap.put(vatBid, nettoValue);
            }
        }
        return vatSummMap;
    }

    private String getVatBidText(String vatBid) {
        if (vatBid.equals(-1)) {
            return "zw.";
        }
        return vatBid.toString();
    }

    private Map<String, Object> getBruttoValueParameters(VatSummTableItem vatSummTableItem) {
        Map<String, Object> parameters = new HashMap<>();
        BigDecimal summBruttoValue = vatSummTableItem.getBruttoValue();

        parameters.put(ReportUtils.VatInvoiceParams.summValue, summBruttoValue);

        Double summBruttoValueDouble = summBruttoValue.doubleValue();
        String summBruttoValueText = DoubleToTextConverter.convert(summBruttoValueDouble);
        parameters.put(ReportUtils.VatInvoiceParams.summValueText, summBruttoValueText);

        return parameters;
    }
}