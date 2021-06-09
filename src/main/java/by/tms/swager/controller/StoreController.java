package by.tms.swager.controller;

import by.tms.swager.entity.Order;
import by.tms.swager.exception.OrderNotFoundException;
import by.tms.swager.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = storeService.createOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/inventory")
    public ResponseEntity<Void> getInventory() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
        if(orderId == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            Order order = storeService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long orderId) {
        if(orderId == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            storeService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
