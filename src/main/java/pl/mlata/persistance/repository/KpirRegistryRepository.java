package pl.mlata.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.KpirRegistry;

import java.util.List;

public interface KpirRegistryRepository extends CrudRepository<KpirRegistry, Long> {
    KpirRegistry findByCompanyAndPeriodAndYear(Company company, Integer period, Integer year);

    List<KpirRegistry> findByCompanyAndYear(Company company, Integer year);
}
