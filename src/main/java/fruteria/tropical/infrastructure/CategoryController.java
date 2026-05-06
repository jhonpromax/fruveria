package fruteria.tropical.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fruteria.tropical.domain.Category;
import fruteria.tropical.domain.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Operation(summary = "LISTAR TODAS LAS CATEGORÍAS")
    @GetMapping
    public List<Category> listar() {
        return categoryRepository.findAll();
    }

    @Operation(summary = "BUSCAR CATEGORÍAS POR TEMPORADA")
    @GetMapping("/season/{season}")
    public List<Category> listarPorTemporada(@PathVariable String season) {
        return categoryRepository.findBySeason(season);
    }

    @Operation(summary = "BUSCAR CATEGORÍA POR ID")
    @GetMapping("/{id}")
    public ResponseEntity<Category> buscarPorId(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "REGISTRAR CATEGORÍA - SOLO ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Category crear(@RequestBody Category category) {
        log.info("Frutería Tropical: Registrando nueva categoría: {}", category.getName());
        return categoryRepository.save(category);
    }

    @Operation(summary = "EDITAR/ACTUALIZAR CATEGORÍA")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> actualizar(@PathVariable Long id, @RequestBody Category categoryDetalles) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryDetalles.getName());
            category.setSeason(categoryDetalles.getSeason());
            return ResponseEntity.ok(categoryRepository.save(category));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "BORRAR CATEGORÍA")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            log.info("Frutería Tropical: Categoría eliminada ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
