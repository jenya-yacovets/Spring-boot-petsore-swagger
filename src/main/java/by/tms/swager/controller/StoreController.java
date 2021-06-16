package by.tms.swager.controller;

import by.tms.swager.entity.Order;
import by.tms.swager.exception.OrderNotFoundException;
import by.tms.swager.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order newOrder = storeService.createOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/inventory")
    public ResponseEntity<?> getInventory() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable @Min(1) long orderId) {
        try {
            Order order = storeService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable @Min(1) long orderId) {
        try {
            storeService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
