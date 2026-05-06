package fruteria.tropical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickUpRepository extends JpaRepository<PickUp, Long> {
    List<PickUp> findByOrderId(Long orderId);
    List<PickUp> findByCustomerId(Long customerId);
    List<PickUp> findByEstado(String estado);
    List<PickUp> findByEstadoPago(String estadoPago);
}
