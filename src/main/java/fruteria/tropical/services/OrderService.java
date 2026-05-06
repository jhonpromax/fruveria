package fruteria.tropical.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import fruteria.tropical.domain.Order;
import fruteria.tropical.domain.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> listar() {
        log.info("Frutería Tropical: Listando todos los pedidos");
        return repository.findAll();
    }

    public List<Order> buscarPorFruta(Long fruitId) {
        return repository.findByFruitId(fruitId);
    }

    public Order guardar(Order order) {
        log.info("Frutería Tropical: Registrando nuevo pedido: {} para el cliente: {}", order.getDescription(), order.getCustomerName());
        return repository.save(order);
    }

    public Order actualizar(Long id, Order order) {
        return repository.findById(id).map(existing -> {
            existing.setDescription(order.getDescription());
            existing.setOrderDate(order.getOrderDate());
            existing.setCustomerName(order.getCustomerName());
            existing.setFruit(order.getFruit());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        log.warn("Frutería Tropical: Eliminando pedido con ID: {}", id);
        repository.deleteById(id);
    }
}
