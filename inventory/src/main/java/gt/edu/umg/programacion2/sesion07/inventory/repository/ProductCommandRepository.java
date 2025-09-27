/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.repository;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;

/**
 *
 * @author miguelcatalan
 */
public interface ProductCommandRepository {
    public Product save(Product p);
    public boolean deleteById(Long id);
}
