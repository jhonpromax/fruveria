package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Domicilio;
import fruteria.tropical.domain.DomicilioRepository;

import java.util.List;

@RestController
@RequestMapping("/api/domicilios")
public class DomicilioController {
    private static final Logger log = LoggerFactory.getLogger(DomicilioController.class);
    private final DomicilioRepository domicilioRepository;

    public DomicilioController(DomicilioRepository domicilioRepository) {
        this.domicilioRepository = domicilioRepository;
    }

    @Operation(summary = "LISTAR TODOS LOS DOMICILIOS")
    @GetMapping
    public List<Domicilio> listar() {
        return domicilioRepository.findAll();
    }

    @Operation(summary = "BUSCAR DOMICILIOS POR ID DE PEDIDO")
    @GetMapping("/order/{orderId}")
    public List<Domicilio> listarPorPedido(@PathVariable Long orderId) {
        return domicilioRepository.findByOrderId(orderId);
    }

    @Operation(summary = "BUSCAR DOMICILIOS POR ID DE CLIENTE")
    @GetMapping("/customer/{customerId}")
    public List<Domicilio> listarPorCliente(@PathVariable Long customerId) {
        return domicilioRepository.findByCustomerId(customerId);
    }

    @Operation(summary = "BUSCAR DOMICILIOS POR ESTADO")
    @GetMapping("/estado/{estado}")
    public List<Domicilio> listarPorEstado(@PathVariable String estado) {
        return domicilioRepository.findByEstado(estado);
    }

    @Operation(summary = "BUSCAR DOMICILIO POR ID")
    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarPorId(@PathVariable Long id) {
        return domicilioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "REGISTRAR DOMICILIO - SOLO ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Domicilio crear(@RequestBody Domicilio domicilio) {
        log.info("Frutería Tropical: Registrando nuevo domicilio a: {}", domicilio.getDireccion());
        return domicilioRepository.save(domicilio);
    }

    @Operation(summary = "EDITAR/ACTUALIZAR DOMICILIO")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Domicilio> actualizar(@PathVariable Long id, @RequestBody Domicilio domicilioDetalles) {
        return domicilioRepository.findById(id).map(domicilio -> {
            domicilio.setDireccion(domicilioDetalles.getDireccion());
            domicilio.setBarrio(domicilioDetalles.getBarrio());
            domicilio.setTelefono(domicilioDetalles.getTelefono());
            domicilio.setEstado(domicilioDetalles.getEstado());
            domicilio.setFechaEntrega(domicilioDetalles.getFechaEntrega());
            domicilio.setCostoEnvio(domicilioDetalles.getCostoEnvio());
            domicilio.setOrder(domicilioDetalles.getOrder());
            domicilio.setCustomer(domicilioDetalles.getCustomer());
            return ResponseEntity.ok(domicilioRepository.save(domicilio));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR DOMICILIO")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (domicilioRepository.existsById(id)) {
            domicilioRepository.deleteById(id);
            log.info("Frutería Tropical: Domicilio eliminado ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
