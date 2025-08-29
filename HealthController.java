package gt.edu.umg.programacion2.sesion07.inventory.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @GetMapping("/api/health")
  public String ok() { return "OK - Sesión 7 (SOLID primero)"; }
}
