package pl.mlata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mlata.exceptions.ResourceNotFoundException;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.User;
import pl.mlata.persistance.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private UserService userService;
    private CompanyRepository companyRepository;


    @Autowired
    public CompanyService(UserService userService, CompanyRepository companyRepository) {
        this.userService = userService;
        this.companyRepository = companyRepository;
    }

    public Company getCompany(Long companyId) {
        return companyRepository.findOne(companyId);
    }

    public Company getUserCompany() {
        Long userId = userService.getCurrentUser().getId();
        Optional<Company> userCompany = Optional.of(companyRepository.findByUserIdAndContractorAndActive(userId, false, true).get(0));
        return userCompany.orElseThrow(() -> new ResourceNotFoundException("User company was not found", "userCompany"));
    }

    public Company getContractor(Long contractorId) {
        Long userId = userService.getCurrentUser().getId();
        Optional<Company> contractor = Optional.of(companyRepository.findByIdAndUserIdAndContractorAndActive(contractorId, userId, true, true));
        return contractor.orElseThrow(() -> new ResourceNotFoundException("Company was not found", "contractor"));
    }

    public List<Company> getContractors() {
        Long userId = userService.getCurrentUser().getId();
        return companyRepository.findByUserIdAndContractorAndActive(userId, true, true);
    }

    @Transactional
    public Company saveContractor(Company company) {
        Company tmpCompany = new Company(company);
        tmpCompany.setContractor(true);
        tmpCompany.setActive(true);
        tmpCompany.setUser(userService.getCurrentUser());

        return companyRepository.save(tmpCompany);
    }

    @Transactional
    public Company saveCompany(Company company) {
        Company tmpCompany = new Company(company);
        tmpCompany.setUser(userService.getCurrentUser());

        return companyRepository.save(tmpCompany);
    }

    @Transactional
    public Company updateCompany(Company company) {
        company.setActive(false);
        companyRepository.save(company);
        Company tmpCompany = new Company(company);
        tmpCompany.setActive(true);
        tmpCompany.setUser(userService.getCurrentUser());

        return companyRepository.save(tmpCompany);
    }

    public void deleteContractor(Long contractorId) {
        Company companyToDelete = getContractor(contractorId);
        companyToDelete.setActive(false);

        companyRepository.save(companyToDelete);
    }

    public boolean belongsToUser(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        User user = userService.getCurrentUser();

        return company.getUser().equals(user);
    }
}
