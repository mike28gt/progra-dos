/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.repository;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 *
 * @author miguelcatalan
 */
@Repository
@Primary
public class InMemoryProductRepository implements ProductCommandRepository, ProductQueryRepository {
    private static final Map<Long, Product> STORE = new HashMap<>();
    private static long SEQ = 0;
    
    public Product save(Product p) {
        
        if (p.id == null) {
            p.id = ++SEQ;
        }
        
        STORE.put(p.id, p);
        
        return p;
    }
    
    public List<Product> findAll() {
        return new ArrayList<>(STORE.values());
    }
    
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(STORE.get(id));
    }
    
    public boolean deleteById(Long id) {
        return STORE.remove(id) != null;
    }
}
