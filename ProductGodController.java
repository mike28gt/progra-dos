package gt.edu.umg.programacion2.sesion07.inventory.web;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductGodController {
    
    public static class Product {
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

    private static final Map<Long, Product> STORE = new HashMap<>();
    private static long SEQ = 0;

    // ==== POST: Crear ====
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> body) {
        String name = String.valueOf(body.get("name"));
        BigDecimal price = new BigDecimal(String.valueOf(body.get("price")));
        String category = String.valueOf(body.get("category"));

        Product p = new Product(++SEQ, name, price, category, Instant.now());
        STORE.put(p.id, p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    // ==== GET: Listar todos ====
    @GetMapping
    public List<Product> listAll() {
        return new ArrayList<>(STORE.values());
    }

    // ==== GET: Obtener uno ====
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product p = STORE.get(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    // ==== PUT: Actualizar ====
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Product p = STORE.get(id);
        if (p == null) return ResponseEntity.notFound().build();

        if (body.containsKey("name")) p.name = String.valueOf(body.get("name"));
        if (body.containsKey("price")) p.price = new BigDecimal(String.valueOf(body.get("price")));
        if (body.containsKey("category")) p.category = String.valueOf(body.get("category"));

        return ResponseEntity.ok(p);
    }

    // ==== DELETE: Eliminar ====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Product removed = STORE.remove(id);
        if (removed == null) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
