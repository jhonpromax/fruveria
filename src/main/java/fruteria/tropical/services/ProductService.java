package fruteria.tropical.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import fruteria.tropical.domain.Product;
import fruteria.tropical.domain.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> listar() {
        log.info("Frutería Tropical: Listando todos los productos");
        return repository.findAll();
    }

    public Product guardar(Product product) {
        log.info("Frutería Tropical: Registrando nuevo producto: {}", product.getName());
        return repository.save(product);
    }

    public Product actualizar(Long id, Product product) {
        return repository.findById(id).map(existing -> {
            existing.setName(product.getName());
            existing.setBrand(product.getBrand());
            existing.setPrice(product.getPrice());
            existing.setStock(product.getStock());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        log.warn("Frutería Tropical: Eliminando producto con ID: {}", id);
        repository.deleteById(id);
    }
}
