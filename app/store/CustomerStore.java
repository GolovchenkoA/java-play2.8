package store;

import models.Customer;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class CustomerStore {

    private static final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private static final AtomicLong idGen = new AtomicLong(1);


    public Customer get(long id) {
        return customers.get(id);
    }

    public void updateOrCreate(Customer customer) {

        if (customer.getId() == 0) {
            customer.setId(idGen.getAndIncrement());
        }

        customers.put(customer.getId(), customer);
    }

    public Collection<Customer> getAll() {
        return customers.values();
    }
}
