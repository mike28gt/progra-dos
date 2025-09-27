/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author miguelcatalan
 */
public interface SpringDataProductJpa extends JpaRepository<ProductEntity, Long>  {
    
}
