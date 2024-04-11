package com.eurderproject.customer;

import com.eurderproject.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
    }

    public CustomerDto addCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = customerDtoMapper.mapFromDto(createCustomerDto);
        customerRepository.save(customer);
        return customerDtoMapper.mapToDto(customer);
    }

    // ONLY ADMINS
    public List<CustomerDto> getAllCustomers() {
        return customerDtoMapper.mapToDtoList(customerRepository.getAllCustomers());
    }

    public CustomerDto getCustomerById(UUID customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerById(customerId);
        return optionalCustomer.map(customerDtoMapper::mapToDto)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
    }
}
