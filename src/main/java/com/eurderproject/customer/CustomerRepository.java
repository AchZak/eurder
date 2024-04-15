package com.eurderproject.customer;

import com.eurderproject.security.Role;
import com.eurderproject.security.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {
    private final UserRepository userRepository;
    public CustomerRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(Customer customer) {
        userRepository.save(customer);
    }

    public Collection<Customer> getAllCustomers() {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .map(user -> (Customer) user)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findCustomerById(UUID customerId) {
        return userRepository.findUserById(customerId)
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .map(user -> (Customer) user);
    }

    public Optional<Customer> findCustomerByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .map(user -> (Customer) user);
    }
}
