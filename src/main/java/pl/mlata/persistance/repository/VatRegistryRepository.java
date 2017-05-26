package pl.mlata.persistance.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.VatRegistry;

import java.util.List;

@Repository
public interface VatRegistryRepository extends CrudRepository<VatRegistry, Long> {
    List<VatRegistry> findByCompanyId(Long companyId);
    VatRegistry findByCompanyAndPeriodAndYear(Company company, Integer period, Integer year);
}
