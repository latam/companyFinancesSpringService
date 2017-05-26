package pl.mlata.reports;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.VatRegistry;
import pl.mlata.persistance.model.VatRegistryEntry;
import pl.mlata.reports.dto.VatRegistrySummTableItem;
import pl.mlata.reports.dto.VatRegistryTableItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class VatRegistryReport {
    public byte[] createReport(VatRegistry registryData) {
        Map<String, Object> parameters = getReportParameters(registryData);
        byte[] pdfReport = ReportUtils.getVatRegistryReportAsBytes(parameters);

        return pdfReport;
    }

    private Map<String, Object> getReportParameters(VatRegistry registryData) {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put(ReportUtils.VatRegistryParams.registryPeriod, registryData.getPeriod() + "/" + registryData.getYear());

        Map<String, Object> companyParameters = getCompanyParameters(registryData);
        parameters.putAll(companyParameters);

        List<VatRegistryTableItem> tableItems = getTableItems(registryData);
        List<VatRegistrySummTableItem> summTableItems = getSummary(registryData);

        JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableItems, false);
        JRBeanCollectionDataSource summTableDataSource = new JRBeanCollectionDataSource(summTableItems, false);

        parameters.put(ReportUtils.VatRegistryParams.tableDataSource, tableDataSource);
        parameters.put(ReportUtils.VatRegistryParams.summTableDataSource, summTableDataSource);

        return parameters;
    }

    private Map<String, Object> getCompanyParameters(VatRegistry registryData) {
        Company company = registryData.getCompany();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ReportUtils.CompanyParams.name, company.getName());
        parameters.put(ReportUtils.CompanyParams.address, company.getAddress());
        parameters.put(ReportUtils.CompanyParams.nip, company.getNip());

        return parameters;
    }

    private List<VatRegistryTableItem> getTableItems(VatRegistry registryData) {
        List<VatRegistryTableItem> tableItems = new ArrayList<>();

        for (VatRegistryEntry registryEntry : registryData.getEntries()) {
            VatRegistryTableItem item = getVatRegistryTableItem(registryEntry);
            tableItems.add(item);
        }

        return tableItems;
    }

    private VatRegistryTableItem getVatRegistryTableItem(VatRegistryEntry registryEntry) {
        VatRegistryTableItem item = new VatRegistryTableItem();

        Company contractor = registryEntry.getContractor();

        item.setPosition(registryEntry.getPosition());
        item.setDate(java.sql.Date.valueOf(registryEntry.getDate()));
        item.setRegistry(getRegistryTypeText(registryEntry.isPurchase()));
        item.setNumber(registryEntry.getDocNumber());

        String contractorData = getContractorText(contractor);
        item.setContractor(contractorData);

        item.setDescription(registryEntry.getDescription());
        item.setNettoValue(registryEntry.getNettoValue());

        String vatBid = getVatBidText(registryEntry.getBid());
        item.setVatBid(vatBid);

        item.setVatValue(registryEntry.getVatValue());
        return item;
    }

    private String getRegistryTypeText(boolean purchase) {
        if (purchase)
            return "Zakup";
        return "Sprzedaż";
    }

    private String getContractorText(Company contractor) {
        String contractorData = String.format("%s, %s, %s %s, NIP: %s",
                contractor.getName(),
                contractor.getStreet(),
                contractor.getPostalCode(),
                contractor.getCity(),
                contractor.getNip());
        return contractorData;
    }

    private String getVatBidText(String vatBid) {
        if (vatBid.equals(-1)) {
            return "zw.";
        }
        return vatBid.toString();
    }

    private List<VatRegistrySummTableItem> getSummary(VatRegistry registryData) {
        Map<String, VatRegistrySummTableItem> vatSummMap = new HashMap<>();

        for (VatRegistryEntry registryEntry : registryData.getEntries()) {
            String vatBid = registryEntry.getBid();
            VatRegistrySummTableItem item;

            if (vatSummMap.containsKey(vatBid)) {
                item = vatSummMap.get(vatBid);
            } else {
                item = new VatRegistrySummTableItem();
                item.setVatBid(vatBid);
            }

            if (registryEntry.isPurchase()) {
                BigDecimal nettoValue = item.getExpNettoValue();
                nettoValue = nettoValue.add(registryEntry.getNettoValue());

                BigDecimal rawVatBid = new BigDecimal(vatBid).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
                BigDecimal vatValue = nettoValue.multiply(rawVatBid);

                BigDecimal bruttoValue = nettoValue.add(vatValue);
                item.setExpNettoValue(nettoValue);
                item.setExpBruttoValue(bruttoValue);
                item.setExpTaxValue(vatValue);
            } else {
                BigDecimal nettoValue = item.getRevNettoValue();
                nettoValue = nettoValue.add(registryEntry.getNettoValue());

                BigDecimal rawVatBid = new BigDecimal(vatBid).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
                BigDecimal vatValue = nettoValue.multiply(rawVatBid);

                BigDecimal bruttoValue = nettoValue.add(vatValue);
                item.setRevNettoValue(nettoValue);
                item.setRevBruttoValue(bruttoValue);
                item.setRevTaxValue(vatValue);
            }

            vatSummMap.put(vatBid, item);
        }

        List<VatRegistrySummTableItem> summTableItems = new ArrayList<>(vatSummMap.values());
        VatRegistrySummTableItem totalSummary = new VatRegistrySummTableItem();
        totalSummary.setVatBid("Razem");

        for(VatRegistrySummTableItem item : summTableItems) {
            totalSummary.Summ(item);
        }
        summTableItems.add(totalSummary);

        return summTableItems;
    }
}
