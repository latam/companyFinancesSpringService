package pl.mlata.reports;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pl.mlata.persistance.model.Company;
import pl.mlata.dto.VatRegistrySummaryItemDTO;
import pl.mlata.dto.VatRegistryItemDTO;

import java.util.*;

public class VatRegistryReport {
    public byte[] createReport(String registryPeriod, Company company,
                               List<VatRegistryItemDTO> tableItems,
                               List<VatRegistrySummaryItemDTO> summTableItems) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put(ReportUtils.VatRegistryParams.registryPeriod, registryPeriod);

        Map<String, Object> companyParameters = getCompanyParameters(company);
        parameters.putAll(companyParameters);

        JRBeanCollectionDataSource tableDataSource = new JRBeanCollectionDataSource(tableItems, false);
        JRBeanCollectionDataSource summTableDataSource = new JRBeanCollectionDataSource(summTableItems, false);

        parameters.put(ReportUtils.VatRegistryParams.tableDataSource, tableDataSource);
        parameters.put(ReportUtils.VatRegistryParams.summTableDataSource, summTableDataSource);

        byte[] pdfReport = ReportUtils.getVatRegistryReportAsBytes(parameters);

        return pdfReport;
    }

    private Map<String, Object> getCompanyParameters(Company company) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ReportUtils.CompanyParams.name, company.getName());
        parameters.put(ReportUtils.CompanyParams.address, company.getAddress());
        parameters.put(ReportUtils.CompanyParams.nip, company.getNip());

        return parameters;
    }
}
