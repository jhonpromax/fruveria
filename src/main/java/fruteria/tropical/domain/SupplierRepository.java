package fruteria.tropical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de proveedores de frutas.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
