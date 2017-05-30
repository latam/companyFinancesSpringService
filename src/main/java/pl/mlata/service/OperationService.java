package pl.mlata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mlata.exceptions.ForbiddenResourceException;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.KpirRegistryEntry;
import pl.mlata.persistance.model.Operation;

import pl.mlata.persistance.model.VatRegistryEntry;
import pl.mlata.persistance.repository.OperationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private VatRegistryService vatRegistryService;

    @Autowired
    private KpirRegistryService kpirRegistryService;

    @Transactional
    public Operation addOperation(Operation operation) {
        Long contractorId = operation.getContractor().getId();
        Company contractor = companyService.getCompany(contractorId);
        operation.setContractor(contractor);

        Company userCompany = companyService.getUserCompany();
        operation.setCompany(userCompany);

        VatRegistryEntry vatRegistryEntry = vatRegistryService.createEntryFromOperation(operation);
        KpirRegistryEntry kpirRegistryEntry = kpirRegistryService.createEntryFromOperation(operation);

        operation.setVatRegistryEntry(vatRegistryEntry);
        operation.setKpirRegistryEntry(kpirRegistryEntry);

        operation = operationRepository.save(operation);

        return operation;
    }

    public List<Operation> getAllOperations() {
        Company userCompany = companyService.getUserCompany();
        List<Operation> operations = operationRepository.findAllByCompany(userCompany);
        return operations;
    }

    public List<Operation> getFilteredOperations(LocalDate fromDate, LocalDate toDate) {
        Company userCompany = companyService.getUserCompany();
        List<Operation> operations = operationRepository.findAllByCompanyAndDateBetween(userCompany, fromDate, toDate);
        return operations;
    }

    @Transactional
    public void deleteOperation(Long operationId) {
        Company userCompany = companyService.getUserCompany();
        Operation operation = operationRepository.findByCompanyAndId(userCompany, operationId);
        if(operation != null) {
            operationRepository.delete(operationId);
        }
    }
}
