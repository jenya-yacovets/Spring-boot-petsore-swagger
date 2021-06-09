package by.tms.swager.store.store;

import by.tms.swager.entity.Order;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryStoreDao implements StoreDao{
    private final List<Order> list = new ArrayList<>();
    private long nextId = 1;

    @Override
    public long saveOrder(Order order) {
        order.setId(nextId++);
        list.add(order);
        return order.getId();
    }

    @Override
    public Order getById(long id) throws DataNotFoundDaoException {

        Order findOrder = list.stream().filter(order -> order.getId() == id).findAny().orElse(null);
        if(findOrder == null) throw new DataNotFoundDaoException("Order not found");
        return findOrder;
    }

    @Override
    public void delete(long id) throws DataNotFoundDaoException {
        Order order = getById(id);
        list.remove(order);
    }
}
