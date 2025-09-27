/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.repository;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author miguelcatalan
 */
public interface ProductQueryRepository {
    public List<Product> findAll();
    public Optional<Product> findById(Long id);
}
