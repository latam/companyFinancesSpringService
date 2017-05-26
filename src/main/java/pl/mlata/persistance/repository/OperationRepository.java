package pl.mlata.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mlata.persistance.model.Company;
import pl.mlata.persistance.model.Operation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByCompanyAndDateBetween(Company company, LocalDate startDate, LocalDate endDate);
    Operation findByCompanyAndId(Company company, Long id);
    List<Operation> findAllByCompany(Company company);
}
