package fruteria.tropical.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import fruteria.tropical.domain.Category;
import fruteria.tropical.domain.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> listar() {
        log.info("Frutería Tropical: Listando todas las categorías");
        return repository.findAll();
    }

    public List<Category> buscarPorTemporada(String season) {
        return repository.findBySeason(season);
    }

    public Category guardar(Category category) {
        log.info("Frutería Tropical: Registrando nueva categoría: {}", category.getName());
        return repository.save(category);
    }

    public Category actualizar(Long id, Category category) {
        return repository.findById(id).map(existing -> {
            existing.setName(category.getName());
            existing.setSeason(category.getSeason());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    public void eliminar(Long id) {
        log.warn("Frutería Tropical: Eliminando categoría con ID: {}", id);
        repository.deleteById(id);
    }
}
