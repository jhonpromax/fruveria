package fruteria.tropical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de productos procesados (jugos, mermeladas, etc.).
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
