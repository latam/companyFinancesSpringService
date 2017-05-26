package pl.mlata.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mlata.persistance.model.VatInvoice;

public interface VatInvoiceRepository extends CrudRepository<VatInvoice, Long> {
}
