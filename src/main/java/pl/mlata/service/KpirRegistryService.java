package pl.mlata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.KpirRegistry;
import pl.mlata.persistance.model.KpirRegistryEntry;
import pl.mlata.persistance.model.Operation;
import pl.mlata.persistance.repository.KpirRegistryEntryRepository;
import pl.mlata.persistance.repository.KpirRegistryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class KpirRegistryService {
    @Autowired
    KpirRegistryRepository kpirRegistryRepository;
    @Autowired
    KpirRegistryEntryRepository kpirRegistryEntryRepository;
    @Autowired
    CompanyService companyService;

    @Transactional
    public KpirRegistry createRegistry(Integer month, Integer year) {
        KpirRegistry kpirRegistry = new KpirRegistry(month, year);
        Company company = companyService.getUserCompany();
        kpirRegistry.setCompany(company);

        kpirRegistry = kpirRegistryRepository.save(kpirRegistry);
        return kpirRegistry;
    }

    @Transactional
    public KpirRegistryEntry saveRegistryEntry(KpirRegistryEntry kpirRegistryEntry) {
        LocalDate entryDate = kpirRegistryEntry.getDate();
        Integer month = entryDate.getMonthValue();
        Integer year = entryDate.getYear();

        KpirRegistry kpirRegistry = getRegistry(month, year);

        Integer position = 1;
        if (kpirRegistry.getId() != null)
            position = kpirRegistry.getEntries().size() + 1;

        kpirRegistryEntry.setPosition(position);
        kpirRegistryEntry.recalculateExpCombined();
        kpirRegistryEntry.recalculateRevCombined();

        kpirRegistryEntry.setRegistry(kpirRegistry);
        kpirRegistryEntry = kpirRegistryEntryRepository.save(kpirRegistryEntry);

        return kpirRegistryEntry;
    }

    private KpirRegistry getRegistry(Integer month, Integer year) {
        Company company = companyService.getUserCompany();
        KpirRegistry kpirRegistry = kpirRegistryRepository.findByCompanyAndPeriodAndYear(company, month, year);

        if (kpirRegistry == null) {
            kpirRegistry = createRegistry(month, year);
        }

        return kpirRegistry;
    }


    public List<KpirRegistry> getRegistries(Integer year) {
        Company company = companyService.getUserCompany();
        List<KpirRegistry> kpirRegistries = kpirRegistryRepository.findByCompanyAndYear(company, year);

        return kpirRegistries;
    }

    private KpirRegistry getRegistryForExport(Integer month, Integer year) {
        Company company = companyService.getUserCompany();
        KpirRegistry vatRegistry = kpirRegistryRepository.findByCompanyAndPeriodAndYear(company, month, year);

        if (vatRegistry == null) {
            return null;
        }

        return vatRegistry;
    }

    public List<KpirRegistryEntry> getRegistryEntries(Integer month, Integer year) {
        KpirRegistry kpirRegistry = getRegistryForExport(month, year);

        if(kpirRegistry == null || kpirRegistry.getEntries() == null) {
            return new ArrayList<>();
        }

        return kpirRegistry.getEntries();
    }

    @Transactional
    public KpirRegistryEntry createEntryFromOperation(Operation operation) {
        KpirRegistryEntry kpirRegistryEntry = new KpirRegistryEntry();

        kpirRegistryEntry.setDate(operation.getDate());
        kpirRegistryEntry.setDocNumber(operation.getDocNumber());
        kpirRegistryEntry.setContractor(operation.getContractor());
        kpirRegistryEntry.setDescription(operation.getDescription());

        BigDecimal value = operation.getNettoValue();

        switch(operation.getKpirColumn()) {
            case 7:
                kpirRegistryEntry.setRevProductServices(value);
                break;
            case 8:
                kpirRegistryEntry.setRevOther(value);
                break;
            case 10:
                kpirRegistryEntry.setPurchaseGoodsMaterials(value);
                break;
            case 11:
                kpirRegistryEntry.setInsuranceCost(value);
            case 12:
                kpirRegistryEntry.setExpPayment(value);
                break;
            case 13:
                kpirRegistryEntry.setExpOther(value);
        }

        kpirRegistryEntry.setPoints("");

        kpirRegistryEntry = saveRegistryEntry(kpirRegistryEntry);
        return kpirRegistryEntry;
    }
}
