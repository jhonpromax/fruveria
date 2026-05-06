package fruteria.tropical.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import fruteria.tropical.domain.Domicilio;
import fruteria.tropical.domain.DomicilioRepository;

import java.util.List;

@Service
public class DomicilioService {
    private static final Logger log = LoggerFactory.getLogger(DomicilioService.class);
    private final DomicilioRepository repository;

    public DomicilioService(DomicilioRepository repository) {
        this.repository = repository;
    }

    public List<Domicilio> listar() {
        log.info("Frutería Tropical: Listando todos los domicilios");
        return repository.findAll();
    }

    public List<Domicilio> buscarPorPedido(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    public List<Domicilio> buscarPorCliente(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<Domicilio> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public Domicilio guardar(Domicilio domicilio) {
        log.info("Frutería Tropical: Registrando nuevo domicilio a: {} - Barrio: {}", domicilio.getDireccion(), domicilio.getBarrio());
        return repository.save(domicilio);
    }

    public Domicilio actualizar(Long id, Domicilio domicilio) {
        return repository.findById(id).map(existing -> {
            existing.setDireccion(domicilio.getDireccion());
            existing.setBarrio(domicilio.getBarrio());
            existing.setTelefono(domicilio.getTelefono());
            existing.setEstado(domicilio.getEstado());
            existing.setFechaEntrega(domicilio.getFechaEntrega());
            existing.setCostoEnvio(domicilio.getCostoEnvio());
            existing.setOrder(domicilio.getOrder());
            existing.setCustomer(domicilio.getCustomer());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Domicilio no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        log.warn("Frutería Tropical: Eliminando domicilio con ID: {}", id);
        repository.deleteById(id);
    }
}
