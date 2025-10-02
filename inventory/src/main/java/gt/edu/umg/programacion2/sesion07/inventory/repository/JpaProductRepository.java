/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.repository;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;
import gt.edu.umg.programacion2.sesion07.inventory.persistence.ProductEntity;
import gt.edu.umg.programacion2.sesion07.inventory.persistence.SpringDataProductJpa;
import java.util.Optional;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 *
 * @author miguelcatalan
 */
@Repository
public class JpaProductRepository implements IProductRepository {
    private final SpringDataProductJpa jpa;

    public JpaProductRepository(SpringDataProductJpa jpa) {
        this.jpa = jpa; 
    }

    @Override
    public Product save(Product p) {
        ProductEntity e = toEntity(p);
        ProductEntity saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override public boolean deleteById(Long id) {
        if (!jpa.existsById(id)) return false;
        jpa.deleteById(id);
        return true;
    }

    private Product toDomain(ProductEntity e) {
        return new Product(e.id, e.name, e.price, e.category, e.createdAt);
    }

    private ProductEntity toEntity(Product d) {
        ProductEntity e = new ProductEntity();
        e.id = d.id; e.name = d.name; e.price = d.price; e.category = d.category; e.createdAt = d.createdAt;
        return e;
    }
}
