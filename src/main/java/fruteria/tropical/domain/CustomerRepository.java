package fruteria.tropical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de clientes en Frutería Tropical.
 * Hereda de JpaRepository para proporcionar CRUD automático en MySQL.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Aquí puedes añadir búsquedas por email para el login más adelante
}
