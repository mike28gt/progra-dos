package gt.edu.umg.programacion2.sesion07.inventory.web;

import gt.edu.umg.programacion2.sesion07.inventory.domain.Product;
import gt.edu.umg.programacion2.sesion07.inventory.repository.IProductRepository;
import gt.edu.umg.programacion2.sesion07.inventory.repository.InMemoryProductRepository;
import gt.edu.umg.programacion2.sesion07.inventory.repository.ProductQueryRepository;
import gt.edu.umg.programacion2.sesion07.inventory.repository.ProductCommandRepository;
import gt.edu.umg.programacion2.sesion07.inventory.repository.JpaProductRepository;
import gt.edu.umg.programacion2.sesion07.inventory.service.ProductSearch;
import gt.edu.umg.programacion2.sesion07.inventory.service.ProductService;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductGodController {

    private final ProductService service;
    private final ProductSearch search;
    
    public ProductGodController(ProductService service, ProductSearch search) {
        this.service = service;
        this.search = search;
    }
    
    // ==== POST: Crear ====
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> body) {
        String name = String.valueOf(body.get("name"));
        BigDecimal price = new BigDecimal(String.valueOf(body.get("price")));
        String category = String.valueOf(body.get("category"));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(name, price, category));
    }

    // ==== GET: Listar todos ====
    @GetMapping
    public List<Product> listAll() {
        return service.list();
    }

    // ==== GET: Obtener uno ====
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    // ==== PUT: Actualizar ====
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        String name = String.valueOf(body.get("name"));
        BigDecimal price = new BigDecimal(String.valueOf(body.get("price")));
        String category = String.valueOf(body.get("category"));

        return ResponseEntity.ok(service.update(id, name, price, category));
    }

    // ==== DELETE: Eliminar ====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public List<Product> search(@RequestParam String productName) {
        return search.searchByName(productName);
    }
    
}