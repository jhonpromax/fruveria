package fruteria.tropical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
    List<Domicilio> findByOrderId(Long orderId);
    List<Domicilio> findByCustomerId(Long customerId);
    List<Domicilio> findByEstado(String estado);
}
