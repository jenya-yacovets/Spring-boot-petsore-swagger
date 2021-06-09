package by.tms.swager.service;

import by.tms.swager.entity.Order;
import by.tms.swager.exception.OrderNotFoundException;
import by.tms.swager.exception.dao.DataNotFoundDaoException;
import by.tms.swager.store.store.StoreDao;
import org.springframework.stereotype.Component;

@Component
public class StoreService {

    private final StoreDao storeDao;

    public StoreService(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public Order createOrder(Order order) {
        long id = storeDao.saveOrder(order);
        order.setId(id);
        return order;
    }

    public Order getOrder(long id) throws OrderNotFoundException {
        try {
            Order order = storeDao.getById(id);
            return order;
        } catch (DataNotFoundDaoException e) {
            throw new OrderNotFoundException();
        }
    }

    public void deleteOrder(long id) throws OrderNotFoundException {
        try {
            storeDao.delete(id);
        } catch (DataNotFoundDaoException e) {
            throw new OrderNotFoundException();
        }
    }
}
