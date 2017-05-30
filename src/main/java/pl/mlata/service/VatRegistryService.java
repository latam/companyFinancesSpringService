package pl.mlata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mlata.dto.VatRegistryItemDTO;
import pl.mlata.dto.VatRegistrySummaryItemDTO;
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
import java.util.*;
import java.util.stream.Collectors;

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

    public List<VatRegistryItemDTO> getRegistryEntries(int month, int year) {
        VatRegistry vatRegistry = getRegistryForExport(month, year);

        if(vatRegistry == null || vatRegistry.getEntries() == null) {
            return new ArrayList<>();
        }

        List<VatRegistryEntry> vatRegistryEntries = vatRegistry.getEntries();

        Comparator<VatRegistryItemDTO> byEntryPosition = Comparator.comparingInt(VatRegistryItemDTO::getPosition);
        List<VatRegistryItemDTO> vatRegistryItemDTOS = vatRegistryEntries.stream()
                .map(registryEntry -> new VatRegistryItemDTO(registryEntry))
                .sorted(byEntryPosition)
                .collect(Collectors.toList());
        return vatRegistryItemDTOS;
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
        Company company = vatRegistry.getCompany();

        List<VatRegistryItemDTO> tableItems = getTableItems(vatRegistry);
        List<VatRegistrySummaryItemDTO> summTableItems = getSummary(vatRegistry);

        String registryPeriod = vatRegistry.getPeriod() + "/" + vatRegistry.getYear();

        byte[] pdfReport = report.createReport(registryPeriod, company, tableItems, summTableItems);
        return pdfReport;
    }

    private List<VatRegistryItemDTO> getTableItems(VatRegistry vatRegistry) {
        List<VatRegistryItemDTO> tableItems = new ArrayList<>();

        for (VatRegistryEntry registryEntry : vatRegistry.getEntries()) {
            VatRegistryItemDTO item = getVatRegistryTableItem(registryEntry);
            tableItems.add(item);
        }

        return tableItems;
    }

    private VatRegistryItemDTO getVatRegistryTableItem(VatRegistryEntry registryEntry) {
        VatRegistryItemDTO item = new VatRegistryItemDTO();

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

    private List<VatRegistrySummaryItemDTO> getSummary(VatRegistry registryData) {
        Map<String, VatRegistrySummaryItemDTO> vatSummMap = new HashMap<>();

        for (VatRegistryEntry registryEntry : registryData.getEntries()) {
            String vatBid = registryEntry.getBid();
            VatRegistrySummaryItemDTO item;

            if (vatSummMap.containsKey(vatBid)) {
                item = vatSummMap.get(vatBid);
            } else {
                item = new VatRegistrySummaryItemDTO();
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

        List<VatRegistrySummaryItemDTO> summTableItems = new ArrayList<>(vatSummMap.values());
        VatRegistrySummaryItemDTO totalSummary = new VatRegistrySummaryItemDTO();
        totalSummary.setVatBid("Razem");

        for(VatRegistrySummaryItemDTO item : summTableItems) {
            totalSummary.Summ(item);
        }
        summTableItems.add(totalSummary);

        return summTableItems;
    }
}
