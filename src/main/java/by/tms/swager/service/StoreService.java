package by.tms.swager.service;

import by.tms.swager.entity.Order;
import by.tms.swager.exception.OrderNotFoundException;
import by.tms.swager.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoreService {

    private final OrderRepository orderRepository;

    public StoreService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrder(long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public void deleteOrder(long id) throws OrderNotFoundException {
        getOrder(id);
        orderRepository.deleteById(id);
    }
}
