package com.eurderproject.customer;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Repository
public class CustomerRepository {
    private Map<UUID, Customer> customers;

    public CustomerRepository(Map<UUID, Customer> customers) {
        this.customers = customers;
    }

    public void save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public Optional<Customer> findCustomerById(UUID customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }
}
