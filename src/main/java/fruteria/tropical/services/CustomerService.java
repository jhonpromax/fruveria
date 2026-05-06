package fruteria.tropical.services;

import fruteria.tropical.domain.Customer;
import fruteria.tropical.domain.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> listar() {
        log.info("Frutería Tropical: Consultando lista de clientes");
        return repository.findAll();
    }

    public Customer guardar(Customer customer) {
        return repository.save(customer);
    }

    public Customer actualizar(Long id, Customer customer) {
        return repository.findById(id).map(existing -> {
            existing.setNombre(customer.getNombre());
            existing.setApellido(customer.getApellido());
            existing.setEmail(customer.getEmail());
            // No actualizamos el rol ni el ID por seguridad
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
