package fruteria.tropical.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import fruteria.tropical.domain.PickUp;
import fruteria.tropical.domain.PickUpRepository;

import java.util.List;

@Service
public class PickUpService {
    private static final Logger log = LoggerFactory.getLogger(PickUpService.class);
    private final PickUpRepository repository;

    public PickUpService(PickUpRepository repository) {
        this.repository = repository;
    }

    public List<PickUp> listar() {
        log.info("Frutería Tropical: Listando todas las recogidas en tienda");
        return repository.findAll();
    }

    public List<PickUp> buscarPorPedido(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    public List<PickUp> buscarPorCliente(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<PickUp> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public PickUp guardar(PickUp pickUp) {
        log.info("Frutería Tropical: Registrando nueva recogida 'Pide y Pasa'. Caja asignada: {}", pickUp.getNombreCaja());
        return repository.save(pickUp);
    }

    public PickUp actualizar(Long id, PickUp pickUp) {
        return repository.findById(id).map(existing -> {
            existing.setEstado(pickUp.getEstado());
            existing.setMetodoPago(pickUp.getMetodoPago());
            existing.setEstadoPago(pickUp.getEstadoPago());
            existing.setHoraEstimadaRecogida(pickUp.getHoraEstimadaRecogida());
            existing.setNombreCaja(pickUp.getNombreCaja());
            existing.setOrder(pickUp.getOrder());
            existing.setCustomer(pickUp.getCustomer());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Recogida no encontrada con ID: " + id));
    }

    public void eliminar(Long id) {
        log.warn("Frutería Tropical: Eliminando recogida con ID: {}", id);
        repository.deleteById(id);
    }
}
