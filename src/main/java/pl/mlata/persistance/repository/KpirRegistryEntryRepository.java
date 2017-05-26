package pl.mlata.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.mlata.persistance.model.KpirRegistryEntry;

public interface KpirRegistryEntryRepository extends CrudRepository<KpirRegistryEntry, Long> {
    @Query("select max(e.position) from KpirRegistryEntry e where e.id = ?1")
    Integer findMaxEntryPositionForRegistry(Long registryId);
}
