package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.PickUp;
import fruteria.tropical.domain.PickUpRepository;

import java.util.List;

@RestController
@RequestMapping("/api/pickups")
public class PickUpController {
    private static final Logger log = LoggerFactory.getLogger(PickUpController.class);
    private final PickUpRepository pickUpRepository;

    public PickUpController(PickUpRepository pickUpRepository) {
        this.pickUpRepository = pickUpRepository;
    }

    @Operation(summary = "LISTAR TODAS LAS RECOGIDAS EN TIENDA")
    @GetMapping
    public List<PickUp> listar() {
        return pickUpRepository.findAll();
    }

    @Operation(summary = "BUSCAR RECOGIDAS POR ID DE PEDIDO")
    @GetMapping("/order/{orderId}")
    public List<PickUp> listarPorPedido(@PathVariable Long orderId) {
        return pickUpRepository.findByOrderId(orderId);
    }

    @Operation(summary = "BUSCAR RECOGIDAS POR ID DE CLIENTE")
    @GetMapping("/customer/{customerId}")
    public List<PickUp> listarPorCliente(@PathVariable Long customerId) {
        return pickUpRepository.findByCustomerId(customerId);
    }

    @Operation(summary = "BUSCAR RECOGIDAS POR ESTADO")
    @GetMapping("/estado/{estado}")
    public List<PickUp> listarPorEstado(@PathVariable String estado) {
        return pickUpRepository.findByEstado(estado);
    }

    @Operation(summary = "BUSCAR RECOGIDA POR ID")
    @GetMapping("/{id}")
    public ResponseEntity<PickUp> buscarPorId(@PathVariable Long id) {
        return pickUpRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "REGISTRAR RECOGIDA (PIDE Y PASA) - SOLO ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PickUp crear(@RequestBody PickUp pickUp) {
        log.info("Frutería Tropical: Registrando nueva recogida en tienda");
        return pickUpRepository.save(pickUp);
    }

    @Operation(summary = "EDITAR/ACTUALIZAR RECOGIDA")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PickUp> actualizar(@PathVariable Long id, @RequestBody PickUp pickUpDetalles) {
        return pickUpRepository.findById(id).map(pickUp -> {
            pickUp.setEstado(pickUpDetalles.getEstado());
            pickUp.setMetodoPago(pickUpDetalles.getMetodoPago());
            pickUp.setEstadoPago(pickUpDetalles.getEstadoPago());
            pickUp.setHoraEstimadaRecogida(pickUpDetalles.getHoraEstimadaRecogida());
            pickUp.setNombreCaja(pickUpDetalles.getNombreCaja());
            pickUp.setOrder(pickUpDetalles.getOrder());
            pickUp.setCustomer(pickUpDetalles.getCustomer());
            return ResponseEntity.ok(pickUpRepository.save(pickUp));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR RECOGIDA")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (pickUpRepository.existsById(id)) {
            pickUpRepository.deleteById(id);
            log.info("Frutería Tropical: Recogida eliminada ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
