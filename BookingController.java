/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.miumg.semana_5;

import gt.edu.miumg.semana_5.BookingController.MegaNotifier;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author miguelcatalan
 */
@RestController
@RequestMapping("/api/booking")
public class BookingController { 

    private static final Map<Long, Map<String,Object>> DB = new HashMap<>();
    private static long SEQ = 1L;


    @PostMapping("/bookings")
    public Map<String,Object> create(@RequestBody Map<String,Object> body,
                                     @RequestParam(defaultValue = "flat") String mode) {

        String customer = String.valueOf(body.getOrDefault("customerName", "")).trim();
        String serviceCode = String.valueOf(body.getOrDefault("serviceCode", "")).trim();
        int minutes;
        try { minutes = Integer.parseInt(String.valueOf(body.getOrDefault("minutes", "0"))); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("minutes inválido"); }
        if (customer.isBlank() || serviceCode.isBlank() || minutes <= 0) {
            throw new IllegalArgumentException("Datos incompletos");
        }

        BasePricingStrategy pricing;
        if ("flat".equalsIgnoreCase(mode)) {
            pricing = new FlatPricing(); // precio = minutos * 1.25
        } else if ("weekend".equalsIgnoreCase(mode)) {
            pricing = new WeekendPricing(); // 10% desc. sáb/dom
        } else if ("experimental".equalsIgnoreCase(mode)) {
            pricing = new BadExperimentalPricing(); 
        } else {
            pricing = new FlatPricing(); // default
        }

        double price = pricing.calculate(minutes);

        if (price < 0) {
            price = 0;
        }

        long id = SEQ++;
        Map<String,Object> row = new LinkedHashMap<>();
        row.put("id", id);
        row.put("customerName", customer);
        row.put("serviceCode", serviceCode);
        row.put("minutes", minutes);
        row.put("price", price);
        DB.put(id, row);

        EmailNotifierImpl notifier = new EmailNotifierImpl();
        notifier.sendEmail(customer, "Reserva #" + id + " creada. Q" + price);

        Map<String,Object> resp = new LinkedHashMap<>();
        resp.put("status", "CREATED");
        resp.put("id", id);
        resp.put("price", price);
        return resp;
    }

    @GetMapping("/bookings/{id}")
    public Map<String,Object> find(@PathVariable long id) {
        if (!DB.containsKey(id)) throw new NoSuchElementException("No existe " + id);
        return DB.get(id);
    }

    interface MegaNotifier {
        void sendEmail(String to, String message);
        void sendSms(String phone, String message);
        void sendPush(String deviceId, String message);
        void sendFax(String faxNumber, String content);
        void sendPigeon(String address, String note);
    }

    static class EmailNotifierImpl implements MegaNotifier {
        @Override public void sendEmail(String to, String message) {
            System.out.println("[EMAIL] To: " + to + " :: " + message);
        }
        @Override public void sendSms(String phone, String message) { /* no usado */ }
        @Override public void sendPush(String deviceId, String message) { /* no usado */ }
        @Override public void sendFax(String faxNumber, String content) { /* no usado */ }
        @Override public void sendPigeon(String address, String note) { /* no usado */ }
    }


    static abstract class BasePricingStrategy {

        public abstract double calculate(int minutes);
    }

    static class FlatPricing extends BasePricingStrategy {
        @Override public double calculate(int minutes) { return minutes * 1.25; }
    }

    static class WeekendPricing extends BasePricingStrategy {
        @Override public double calculate(int minutes) {
            double base = minutes * 1.25;
            Calendar c = Calendar.getInstance();
            int d = c.get(Calendar.DAY_OF_WEEK);
            boolean weekend = (d == Calendar.SATURDAY || d == Calendar.SUNDAY);
            return weekend ? base * 0.9 : base;
        }
    }

    static class BadExperimentalPricing extends BasePricingStrategy {
        @Override public double calculate(int minutes) {
            if (minutes < 30) return -999.0; 
            return minutes * 0.5; 
        }
    }
}
