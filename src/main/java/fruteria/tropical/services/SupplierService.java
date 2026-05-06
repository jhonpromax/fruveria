package fruteria.tropical.services;

import fruteria.tropical.domain.Supplier;
import fruteria.tropical.domain.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierService {
    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public List<Supplier> listar() {
        log.info("Frutería Tropical: Consultando proveedores");
        return repository.findAll();
    }

    public Supplier guardar(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier actualizar(Long id, Supplier supplier) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(supplier.getFullName());
            existing.setRegion(supplier.getRegion());
            existing.setPhone(supplier.getPhone());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
