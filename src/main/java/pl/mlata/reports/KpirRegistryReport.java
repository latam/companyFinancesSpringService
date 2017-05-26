package pl.mlata.reports;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.KpirRegistry;
import pl.mlata.persistance.model.KpirRegistryEntry;
import pl.mlata.reports.dto.KpirRegistryTableItem;
import pl.mlata.reports.dto.KpirSummTableItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KpirRegistryReport {
    private final Integer entriesPerPage = 6;

    public byte[] createReport(List<KpirRegistry> registryData) {
        Map<Integer, Map<String, Object>> parameters = getReportParameters(registryData);
        byte[] pdfReport = ReportUtils.getKpirRegistryReportAsBytes(parameters);

        return pdfReport;
    }

    private Map<Integer, Map<String, Object>> getReportParameters(List<KpirRegistry> kpirRegistries) {
        Map<Integer, Map<String, Object>> allParameters = new HashMap<>();
        Integer parameterMapCounter = 0;

        KpirSummTableItem totalSumm = new KpirSummTableItem();
        totalSumm.setCaption("Razem od poczatku roku");

        for (KpirRegistry kpirRegistry : kpirRegistries) {
            Integer entriesCount = kpirRegistry.getEntries().size();
            Integer pagesCount = (int) Math.ceil(entriesCount.doubleValue() / entriesPerPage.doubleValue());

            KpirSummTableItem prevPageSumm = new KpirSummTableItem();
            prevPageSumm.setCaption("Przeniesienie z poprzedniej strony");
            for (int page = 1; page <= pagesCount; page++) {
                Map<String, Object> parameters = new HashMap<>();

                Company company = kpirRegistry.getCompany();

                parameters.put(ReportUtils.CompanyParams.name, company.getName());
                parameters.put(ReportUtils.CompanyParams.address, company.getAddress());
                parameters.put(ReportUtils.CompanyParams.nip, company.getNip());

                parameters.put("page_number", page);
                parameters.put("page_count", pagesCount);

                parameters.put("month", kpirRegistry.getPeriod());
                parameters.put("year", kpirRegistry.getYear());

                List<KpirRegistryTableItem> tableItems = new ArrayList<>();
                List<KpirSummTableItem> summTableItems = new ArrayList<>();

                KpirSummTableItem pageSumm = new KpirSummTableItem();
                pageSumm.setCaption("Suma strony");
                Integer startIndex = ((page - 1) * entriesPerPage);
                Integer endIndex = startIndex + entriesPerPage;

                if (endIndex > entriesCount)
                    endIndex = entriesCount;

                for (Integer entryCounter = startIndex; entryCounter < endIndex; entryCounter++) {
                    KpirRegistryEntry kpirRegistryEntry = kpirRegistry.getEntries().get(entryCounter);
                    KpirRegistryTableItem item = new KpirRegistryTableItem(kpirRegistryEntry);
                    pageSumm.Summ(item);
                    tableItems.add(item);
                }

                summTableItems.add(pageSumm);
                summTableItems.add(prevPageSumm);
                totalSumm.Summ(pageSumm);
                summTableItems.add(totalSumm);

                JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableItems, false);
                JRBeanCollectionDataSource summTableDataSource = new JRBeanCollectionDataSource(summTableItems, false);

                parameters.put(ReportUtils.VatInvoiceParams.tableDataSource, tableDataSource);
                parameters.put(ReportUtils.VatInvoiceParams.summTableDataSource, summTableDataSource);

                totalSumm = new KpirSummTableItem(totalSumm.getCaption(), totalSumm);
                prevPageSumm = new KpirSummTableItem(prevPageSumm.getCaption(), pageSumm);

                allParameters.put(parameterMapCounter, parameters);
                parameterMapCounter++;
            }
        }
        return allParameters;
    }
}
