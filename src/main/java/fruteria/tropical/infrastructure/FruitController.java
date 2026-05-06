package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Fruit;
import fruteria.tropical.domain.FruitRepository;
import java.util.List;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {
    private static final Logger log = LoggerFactory.getLogger(FruitController.class);
    private final FruitRepository fruitRepository;

    public FruitController(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Operation(summary = "LISTAR TODAS LAS FRUTAS")
    @GetMapping
    public List<Fruit> listar() {
        return fruitRepository.findAll();
    }

    @Operation(summary = "BUSCAR FRUTA POR ID")
    @GetMapping("/{id}")
    public ResponseEntity<Fruit> buscarPorId(@PathVariable Long id) {
        return fruitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "REGISTRAR FRUTA - SOLO ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Fruit crear(@RequestBody Fruit fruit) {
        log.info("Frutería Tropical: Registrando nueva fruta: {}", fruit.getName());
        return fruitRepository.save(fruit);
    }

    @Operation(summary = "EDITAR/ACTUALIZAR FRUTA")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Fruit> actualizar(@PathVariable Long id, @RequestBody Fruit fruitDetalles) {
        return fruitRepository.findById(id).map(fruit -> {
            fruit.setName(fruitDetalles.getName());
            fruit.setOrigin(fruitDetalles.getOrigin());
            fruit.setCategory(fruitDetalles.getCategory());
            fruit.setPricePerKg(fruitDetalles.getPricePerKg());
            fruit.setSupplier(fruitDetalles.getSupplier());
            return ResponseEntity.ok(fruitRepository.save(fruit));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR FRUTA")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (fruitRepository.existsById(id)) {
            fruitRepository.deleteById(id);
            log.info("Frutería Tropical: Fruta eliminada ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
