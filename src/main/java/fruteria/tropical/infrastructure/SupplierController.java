package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Supplier;
import fruteria.tropical.domain.SupplierRepository;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Operation(summary = "LISTAR PROVEEDORES")
    @GetMapping
    public List<Supplier> listar() {
        return supplierRepository.findAll();
    }

    @Operation(summary = "REGISTRAR PROVEEDOR")
    @PostMapping
    public Supplier crear(@RequestBody Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Operation(summary = "EDITAR PROVEEDOR")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> actualizar(@PathVariable Long id, @RequestBody Supplier detalles) {
        return supplierRepository.findById(id).map(supplier -> {
            supplier.setFullName(detalles.getFullName());
            supplier.setRegion(detalles.getRegion());
            supplier.setPhone(detalles.getPhone());
            return ResponseEntity.ok(supplierRepository.save(supplier));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR PROVEEDOR")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
