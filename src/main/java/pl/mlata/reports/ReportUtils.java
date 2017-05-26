package pl.mlata.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportUtils {
    private static String vatInvoiceTemplatePath = "templates/reports/vat_invoice.jasper";
    private static String vatRegistryTemplatePath = "templates/reports/vat_registry.jasper";
    private static String kpirRegistryTemplatePath = "templates/reports/kpir_registry.jasper";

    public class CompanyParams {
        public static final String name = "company_name";
        public static final String address = "company_address";
        public static final String street = "company_street";
        public static final String city = "company_city";
        public static final String nip = "company_nip";
        public static final String regon = "company_regon";
    }

    public class ContractorParams {
        public static final String name = "contractor_name";
        public static final String address = "contractor_address";
        public static final String street = "contractor_street";
        public static final String city = "contractor_city";
        public static final String nip = "contractor_nip";
        public static final String regon = "contractor_regon";
    }

    public class VatInvoiceParams {
        public static final String number = "invoice_number";
        public static final String releaseDate = "release_date";
        public static final String sellDate = "sell_date";
        public static final String sellPlace = "sell_place";
        public static final String summValue = "summValue";
        public static final String summValueText = "value_text";
        public static final String accountNumber = "account_number";
        public static final String bankName = "bank_name";
        public static final String tableDataSource = "table_data_source";
        public static final String summTableDataSource = "summ_table_data_source";
    }

    public class VatRegistryParams {
        public static final String registryPeriod = "registry_period";
        public static final String tableDataSource = "table_data_source";
        public static final String summTableDataSource = "summ_table_data_source";
    }

    public static byte[] getKpirRegistryReportAsBytes(Map<Integer, Map<String, Object>> parameters) {
        return getReportsAsBytes(parameters, kpirRegistryTemplatePath);
    }

    public static byte[] getVatInvoiceReportAsBytes(Map<String, Object> parameters) {
        return getReportAsBytes(parameters, vatInvoiceTemplatePath);
    }

    public static byte[] getVatRegistryReportAsBytes(Map<String, Object> parameters) {
        return getReportAsBytes(parameters, vatRegistryTemplatePath);
    }

    public static byte[] getReportsAsBytes(Map<Integer, Map<String, Object>> parameters, String reportTemplatePath) {
        byte[] pdfReport = null;
        List<JasperPrint> reportPrints = new ArrayList<>();
        JasperReport report = loadTemplateFromResources(reportTemplatePath);

        try {
            Integer reportsCount = parameters.size();
            for (int reportCounter = 0; reportCounter < reportsCount; reportCounter++) {
                JasperPrint print = JasperFillManager.fillReport(report, parameters.get(reportCounter), new JREmptyDataSource());
                reportPrints.add(print);
            }

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(reportPrints));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setCreatingBatchModeBookmarks(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();

            pdfReport = byteArrayOutputStream.toByteArray();
        } catch (JRException e) {
            e.getStackTrace();
        }

        return pdfReport;
    }

    public static byte[] getReportAsBytes(Map<String, Object> parameters, String reportTemplatePath) {
        byte[] pdfReport = null;
        JasperPrint print;
        JasperReport report = loadTemplateFromResources(reportTemplatePath);

        try {
            print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            pdfReport = JasperExportManager.exportReportToPdf(print);

        } catch (JRException e) {
            e.getStackTrace();
        }

        return pdfReport;
    }

    private static JasperReport loadTemplateFromResources(String resourcePath) {
        Resource resource = new ClassPathResource(resourcePath);

        JasperReport report = null;
        try {
            report = (JasperReport) JRLoader.loadObject(resource.getInputStream());
        } catch (Exception e) {
            e.getStackTrace();
        }

        return report;
    }

    public static byte[] compileReportToBytes(String rawTemplate) {
        InputStream templateStream = new ByteArrayInputStream(rawTemplate.getBytes());
        ByteArrayOutputStream compiledReport = new ByteArrayOutputStream();

        try {
            JasperCompileManager.compileReportToStream(templateStream, compiledReport);
        } catch (JRException e) {
            e.getStackTrace();
        }

        return compiledReport.toByteArray();
    }
}
