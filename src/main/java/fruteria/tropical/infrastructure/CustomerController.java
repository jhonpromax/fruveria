package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Customer;
import fruteria.tropical.domain.CustomerRepository;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Operation(summary = "LISTAR CLIENTES")
    @GetMapping
    public List<Customer> listar() {
        return customerRepository.findAll();
    }

    @Operation(summary = "REGISTRAR CLIENTE")
    @PostMapping
    public Customer crear(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @Operation(summary = "BORRAR CLIENTE")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
