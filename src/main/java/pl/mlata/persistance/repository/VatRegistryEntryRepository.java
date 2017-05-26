package pl.mlata.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mlata.persistance.model.VatRegistryEntry;

@Repository
public interface VatRegistryEntryRepository extends CrudRepository<VatRegistryEntry, Long> {
    @Query("select max(e.position) from VatRegistryEntry e where e.id = ?1")
    Integer findMaxEntryPositionForRegistry(Long registryId);
}
