package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Product;
import fruteria.tropical.domain.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Operation(summary = "LISTAR TODOS LOS PRODUCTOS")
    @GetMapping
    public List<Product> listar() {
        return productRepository.findAll();
    }

    @Operation(summary = "BUSCAR PRODUCTO POR ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> buscarPorId(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "REGISTRAR PRODUCTO - SOLO ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product crear(@RequestBody Product product) {
        log.info("Frutería Tropical: Registrando nuevo producto: {}", product.getName());
        return productRepository.save(product);
    }

    @Operation(summary = "EDITAR/ACTUALIZAR PRODUCTO")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizar(@PathVariable Long id, @RequestBody Product productDetalles) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetalles.getName());
            product.setBrand(productDetalles.getBrand());
            product.setPrice(productDetalles.getPrice());
            product.setStock(productDetalles.getStock());
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR PRODUCTO")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            log.info("Frutería Tropical: Producto eliminado ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
