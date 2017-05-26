package pl.mlata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mlata.exceptions.ForbiddenResourceException;
import pl.mlata.exceptions.ResourceNotFoundException;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.Operation;
import pl.mlata.persistance.model.VatRegistry;
import pl.mlata.persistance.model.VatRegistryEntry;
import pl.mlata.persistance.repository.VatRegistryEntryRepository;
import pl.mlata.persistance.repository.VatRegistryRepository;
import pl.mlata.reports.VatRegistryReport;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VatRegistryService {

    private CompanyService companyService;
    private VatRegistryRepository vatRegistryRepository;
    private VatRegistryEntryRepository vatRegistryEntryRepository;

    @Autowired
    public VatRegistryService(CompanyService companyService, VatRegistryRepository vatRegistryRepository, VatRegistryEntryRepository vatRegistryEntryRepository) {
        this.companyService = companyService;
        this.vatRegistryRepository = vatRegistryRepository;
        this.vatRegistryEntryRepository = vatRegistryEntryRepository;
    }

    public VatRegistry getRegistry(Long registryId) {
        return vatRegistryRepository.findOne(registryId);
    }

    public List<VatRegistryEntry> getRegistryEntries(int month, int year) {
        VatRegistry vatRegistry = getRegistryForExport(month, year);

        if(vatRegistry == null || vatRegistry.getEntries() == null) {
            return new ArrayList<>();
        }

        return vatRegistry.getEntries();
    }


    public VatRegistry createRegistry(Integer period, Integer year) {
        VatRegistry vatRegistry = new VatRegistry();
        Company company = companyService.getUserCompany();
        vatRegistry.setCompany(company);
        vatRegistry.setPeriod(period);
        vatRegistry.setYear(year);

        return vatRegistry;
    }

    private VatRegistry getRegistryForExport(Integer period, Integer year) {
        Company company = companyService.getUserCompany();
        Optional<VatRegistry> vatRegistry = Optional.ofNullable(vatRegistryRepository.findByCompanyAndPeriodAndYear(company, period, year));

        return vatRegistry.orElseThrow(() -> new ResourceNotFoundException("VAT registry for period " + period + "/" + year + " was not found.", "VatRegistry"));
    }

    private VatRegistry getRegistry(Integer period, Integer year) {
        Company company = companyService.getUserCompany();
        VatRegistry vatRegistry = vatRegistryRepository.findByCompanyAndPeriodAndYear(company, period, year);

        if (vatRegistry == null) {
            vatRegistry = createRegistry(period, year);
            vatRegistry = vatRegistryRepository.save(vatRegistry);
        }

        return vatRegistry;
    }

    @Transactional
    public VatRegistryEntry createEntryFromOperation(Operation operation) {
        VatRegistryEntry vatRegistryEntry = new VatRegistryEntry();

        vatRegistryEntry.setDate(operation.getDate());
        vatRegistryEntry.setPurchase(operation.getPurchase());
        vatRegistryEntry.setDocNumber(operation.getDocNumber());
        vatRegistryEntry.setContractor(operation.getContractor());
        vatRegistryEntry.setDescription(operation.getDescription());
        vatRegistryEntry.setBid(operation.getVatBid());
        vatRegistryEntry.setNettoValue(operation.getNettoValue());
        BigDecimal vatBid = new BigDecimal(operation.getVatBid());

        BigDecimal rawVatBid = vatBid.divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
        BigDecimal rawVatValue = operation.getNettoValue().multiply(rawVatBid);

        vatRegistryEntry.setVatValue(rawVatValue);

        vatRegistryEntry = saveRegistryEntry(vatRegistryEntry);
        return vatRegistryEntry;
    }

    @Transactional
    public VatRegistryEntry saveRegistryEntry(VatRegistryEntry vatRegistryEntry) {
        LocalDate entryDate = vatRegistryEntry.getDate();
        Integer month = entryDate.getMonthValue();
        Integer year = entryDate.getYear();
        VatRegistry registry = getRegistry(month, year);

        Integer position = 1;
        if (registry.getId() != null)
            position = registry.getEntries().size() + 1;
        vatRegistryEntry.setPosition(position);
        vatRegistryEntry.setRegistry(registry);
        registry.getEntries().add(vatRegistryEntry);

        vatRegistryEntry = vatRegistryEntryRepository.save(vatRegistryEntry);
        return vatRegistryEntry;
    }

    public byte[] generateReportForMonthAndYear(Integer month, Integer year) {
        Company company = companyService.getUserCompany();
        VatRegistry vatRegistry = vatRegistryRepository.findByCompanyAndPeriodAndYear(company, month, year);


        byte[] pdfReport = generateReport(vatRegistry);
        return pdfReport;
    }

    public byte[] generateReportForId(Long registryId) {
        VatRegistry vatRegistry = vatRegistryRepository.findOne(registryId);

        if (!companyService.belongsToUser(vatRegistry.getCompany().getId())) {
            throw new ForbiddenResourceException();
        }

        byte[] pdfReport = generateReport(vatRegistry);
        return pdfReport;
    }

    private byte[] generateReport(VatRegistry vatRegistry) {
        VatRegistryReport report = new VatRegistryReport();
        byte[] pdfReport = report.createReport(vatRegistry);
        return pdfReport;
    }
}
