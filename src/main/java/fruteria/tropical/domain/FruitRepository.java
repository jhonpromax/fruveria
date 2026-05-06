package fruteria.tropical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de frutas y sus vínculos con los proveedores.
 */
@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long> {
    // Permite realizar operaciones CRUD automáticamente sobre la entidad Fruit
}
