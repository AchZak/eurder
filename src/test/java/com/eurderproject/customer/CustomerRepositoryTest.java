package com.eurderproject.customer;

import com.eurderproject.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerRepositoryTest {

    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepository(new Map<UUID,Customer>);
    }

    @Test
    void givenCustomer_whenSave_thenCustomerIsSavedInRepository() {
        // Given
        CustomerRepository repository = new CustomerRepository(new MockUserRepository());
        Customer customer = new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789");

        // When
        repository.save(customer);

        // Then
        Collection<User> users = repository.getAllCustomers();
        assertTrue(users.contains(customer));
    }

    @Test
    void givenCustomerRepository_whenGetAllCustomers_thenReturnsAllCustomers() {
        // Given
        CustomerRepository repository = new CustomerRepository(new MockUserRepository());

        // When
        Collection<Customer> customers = repository.getAllCustomers();

        // Then
        assertEquals(3, customers.size()); // Assuming we have 3 customers in the mock repository
    }

    @Test
    void givenExistingCustomerId_whenFindCustomerById_thenReturnsCustomer() {
        // Given
        CustomerRepository repository = new CustomerRepository(new MockUserRepository());
        UUID customerId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        // When
        Optional<Customer> foundCustomer = repository.findCustomerById(customerId);

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals(customerId, foundCustomer.get().getUserId());
    }

    @Test
    void givenNonExistingCustomerId_whenFindCustomerById_thenReturnsEmptyOptional() {
        // Given
        CustomerRepository repository = new CustomerRepository(new MockUserRepository());
        UUID nonExistingCustomerId = UUID.randomUUID();

        // When
        Optional<Customer> foundCustomer = repository.findCustomerById(nonExistingCustomerId);

        // Then
        assertTrue(foundCustomer.isEmpty());
    }

    @Test
    void givenExistingUsername_whenFindCustomerByUsername_thenReturnsCustomer() {
        // Given
        CustomerRepository repository = new CustomerRepository(new MockUserRepository());
        String username = "johndoe";

        // When
        Optional<Customer> foundCustomer = repository.findCustomerByUsername(username);

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals(username, foundCustomer.get().getUsername());
    }

    @Test
    void givenNonExistingUsername_whenFindCustomerByUsername_thenReturnsEmptyOptional() {
        // Given
        CustomerRepository repository = new CustomerRepository(new MockUserRepository());
        String nonExistingUsername = "nonexisting";

        // When
        Optional<Customer> foundCustomer = repository.findCustomerByUsername(nonExistingUsername);

        // Then
        assertTrue(foundCustomer.isEmpty());
    }
}
