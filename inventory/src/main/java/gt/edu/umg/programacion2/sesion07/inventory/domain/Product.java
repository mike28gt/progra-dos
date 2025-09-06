/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.programacion2.sesion07.inventory.domain;

import java.math.BigDecimal;
import java.time.Instant;

/**
 *
 * @author miguelcatalan
 */
public class Product {
    public Long id;
    public String name;
    public BigDecimal price;
    public String category;
    public Instant createdAt;

    public Product(Long id, String name, BigDecimal price, String category, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
    }
}
