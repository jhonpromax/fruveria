package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Order;
import fruteria.tropical.domain.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Operation(summary = "LISTAR TODOS LOS PEDIDOS")
    @GetMapping
    public List<Order> listar() {
        return orderRepository.findAll();
    }

    @Operation(summary = "BUSCAR PEDIDOS POR ID DE FRUTA")
    @GetMapping("/fruit/{fruitId}")
    public List<Order> listarPorFruta(@PathVariable Long fruitId) {
        return orderRepository.findByFruitId(fruitId);
    }

    @Operation(summary = "BUSCAR PEDIDO POR ID")
    @GetMapping("/{id}")
    public ResponseEntity<Order> buscarPorId(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "REGISTRAR PEDIDO - SOLO ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Order crear(@RequestBody Order order) {
        log.info("Frutería Tropical: Registrando nuevo pedido: {}", order.getDescription());
        return orderRepository.save(order);
    }

    @Operation(summary = "EDITAR/ACTUALIZAR PEDIDO")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Order> actualizar(@PathVariable Long id, @RequestBody Order orderDetalles) {
        return orderRepository.findById(id).map(order -> {
            order.setDescription(orderDetalles.getDescription());
            order.setOrderDate(orderDetalles.getOrderDate());
            order.setCustomerName(orderDetalles.getCustomerName());
            order.setFruit(orderDetalles.getFruit());
            return ResponseEntity.ok(orderRepository.save(order));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR PEDIDO")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("Frutería Tropical: Pedido eliminado ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
