package fruteria.tropical.services;

import fruteria.tropical.domain.Fruit;
import fruteria.tropical.domain.FruitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FruitService {
    private static final Logger log = LoggerFactory.getLogger(FruitService.class);
    private final FruitRepository repository;

    public FruitService(FruitRepository repository) {
        this.repository = repository;
    }

    public List<Fruit> listar() {
        log.info("Frutería Tropical: Listando todas las frutas");
        return repository.findAll();
    }

    public Fruit guardar(Fruit fruit) {
        log.info("Frutería Tropical: Registrando nueva fruta: {}", fruit.getName());
        return repository.save(fruit);
    }

    public Fruit actualizar(Long id, Fruit fruit) {
        return repository.findById(id).map(existing -> {
            existing.setName(fruit.getName());
            existing.setOrigin(fruit.getOrigin());
            existing.setPricePerKg(fruit.getPricePerKg());
            existing.setSupplier(fruit.getSupplier());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Fruta no encontrada con ID: " + id));
    }

    public void eliminar(Long id) {
        log.warn("Frutería Tropical: Eliminando fruta con ID: {}", id);
        repository.deleteById(id);
    }
}
