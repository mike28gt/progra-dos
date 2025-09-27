/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.service;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;
import gt.edu.umg.programacion2.sesion07.inventory.repository.IProductRepository;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author miguelcatalan
 */

@Component
public class ProductSearch {
    
    private final IProductRepository repo;
    
    public ProductSearch(IProductRepository repo) {
        this.repo = repo;
    }
    
    public List<Product> searchByName(String productName) {
    
        String s = productName == null ? "" : productName.toLowerCase();
        
        return repo.findAll().stream()
                    .filter(p -> p.name != null && 
                            p.name.toLowerCase().contains(s))
                    .toList();
    }
    
}
