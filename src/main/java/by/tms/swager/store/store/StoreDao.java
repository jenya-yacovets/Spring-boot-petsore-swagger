package by.tms.swager.store.store;

import by.tms.swager.entity.Order;
import by.tms.swager.exception.dao.DataNotFoundDaoException;

public interface StoreDao {
    long saveOrder(Order order);
    Order getById(long id) throws DataNotFoundDaoException;
    void delete(long id) throws DataNotFoundDaoException;
}
