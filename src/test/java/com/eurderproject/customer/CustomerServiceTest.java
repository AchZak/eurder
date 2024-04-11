package com.eurderproject.customer;

import com.eurderproject.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerDtoMapper customerDtoMapper;
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenCustomersInDatabase_whenGettingAllCustomers_thenGetAllCustomerDtos() {
        // Given
        Customer customer1 = new Customer("Mario", "Brothers", "mario@example.com", "123 Mushroom Kingdom", "04444");
        Customer customer2 = new Customer("Lara", "Croft", "lara@example.com", "456 Tomb Raider Street", "0455555");
        Customer customer3 = new Customer("Sonic", "Hedgehog", "sonic@example.com", "789 Green Hill Zone", "04777");
        List<Customer> customers = Arrays.asList(customer1, customer2, customer3);


        CustomerDto customerDto1 = new CustomerDto(customer1.getCustomerId(), customer1.getFirstName(), customer1.getLastName(), customer1.getEmailAddress(), customer1.getAddress(), customer1.getPhoneNumber());
        CustomerDto customerDto2 = new CustomerDto(customer2.getCustomerId(), customer2.getFirstName(), customer2.getLastName(), customer2.getEmailAddress(), customer2.getAddress(), customer2.getPhoneNumber());
        CustomerDto customerDto3 = new CustomerDto(customer3.getCustomerId(), customer3.getFirstName(), customer3.getLastName(), customer3.getEmailAddress(), customer3.getAddress(), customer3.getPhoneNumber());
        List<CustomerDto> expectedCustomerDtos = Arrays.asList(customerDto1, customerDto2, customerDto3);


        Mockito.when(customerRepository.getAllCustomers()).thenReturn(customers);
        Mockito.when(customerDtoMapper.mapToDtoList(customers)).thenReturn(expectedCustomerDtos);

        // When
        List<CustomerDto> actualCustomerDtos = customerService.getAllCustomers();

        // Then
        assertEquals(expectedCustomerDtos.size(), actualCustomerDtos.size());
        for (int i = 0; i < expectedCustomerDtos.size(); i++) {
            assertEquals(expectedCustomerDtos.get(i), actualCustomerDtos.get(i));
        }
    }

    @Test
    void givenCreateCustomerDto_whenAddingCustomer_thenCustomerAdded() {
        // Given
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Mario", "Brothers", "mario@example.com", "123 Mushroom Kingdom", "04444");

        Customer customer = new Customer(createCustomerDto.firstName(), createCustomerDto.lastName(), createCustomerDto.emailAddress(), createCustomerDto.address(), createCustomerDto.phoneNumber());
        CustomerDto expectedCustomerDto = new CustomerDto(customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(), customer.getAddress(), customer.getPhoneNumber());

        Mockito.when(customerDtoMapper.mapFromDto(createCustomerDto)).thenReturn(customer);
        Mockito.when(customerDtoMapper.mapToDto(customer)).thenReturn(expectedCustomerDto);

        // When
        CustomerDto actualCustomerDto = customerService.addCustomer(createCustomerDto);

        // Then
        customerRepository.findCustomerById(actualCustomerDto.customerId());
        assertEquals(expectedCustomerDto, actualCustomerDto);
        Mockito.verify(customerDtoMapper).mapFromDto(createCustomerDto);
        Mockito.verify(customerRepository).save(customer);
        Mockito.verify(customerDtoMapper).mapToDto(customer);
    }

    @Test
    void givenExistingCustomerId_whenGetCustomerById_thenGetCustomerDto(){
        // Given
        Customer customer = new Customer("Mario", "Brothers", "mario@example.com", "123 Mushroom Kingdom", "04444");
        CustomerDto expectedCustomerDto = new CustomerDto(customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(), customer.getAddress(), customer.getPhoneNumber());

        Mockito.when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(customerDtoMapper.mapToDto(customer)).thenReturn(expectedCustomerDto);

        // When
        CustomerDto actualCustomerDto = customerService.getCustomerById(customer.getCustomerId());

        // Then
        assertEquals(expectedCustomerDto, actualCustomerDto);
    }

    @Test
    void givenNonExistingCustomerId_whenGetCustomerById_thenThrowCustomerNotFoundException() {
        // Given
        Customer customer = new Customer("Mario", "Brothers", "mario@example.com", "123 Mushroom Kingdom", "04444");
        CustomerDto expectedCustomerDto = new CustomerDto(customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(), customer.getAddress(), customer.getPhoneNumber());

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(UUID.randomUUID()));
    }

}