package pl.mlata.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mlata.persistance.model.Company;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findByUserIdAndContractorAndActive(Long userId, Boolean contractor, Boolean active);
}