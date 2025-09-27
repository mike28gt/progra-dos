/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.service;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;
import gt.edu.umg.programacion2.sesion07.inventory.persistence.SpringDataProductJpa;
import gt.edu.umg.programacion2.sesion07.inventory.repository.IProductRepository;
import gt.edu.umg.programacion2.sesion07.inventory.repository.InMemoryProductRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/**
 *
 * @author miguelcatalan
 */

@Service
public class ProductService {
    private final IProductRepository repo;
    
    public ProductService(IProductRepository repo) {
        this.repo = repo;
    }
    
    public Product create(String name, BigDecimal price, String category) {
        Product p = new Product(null, name.trim(), price, category.trim(), Instant.now());
        return repo.save(p);
    }
    
    public Product get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }
    
    public List<Product> list() {
        return repo.findAll();
    }
    
    public Product update(Long id, String name, BigDecimal price, String category) {
        Product existing = get(id);
        existing.name = name.trim();
        existing.price = price;
        existing.category = category.trim();
        
        return repo.save(existing);
    }
    
    public void delete(Long id) {
        if (!repo.deleteById(id)) {
            throw new NoSuchElementException("Product not found.");
        }
    }
}
