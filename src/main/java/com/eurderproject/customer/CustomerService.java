package com.eurderproject.customer;

import com.eurderproject.exception.CustomerNotFoundException;
import com.eurderproject.exception.CustomerValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        validateCreateCustomerDto(createCustomerDto);
        Customer customer = customerDtoMapper.mapFromDto(createCustomerDto);
        customerRepository.save(customer);
        return customerDtoMapper.mapToDto(customer);
    }

    private void validateCreateCustomerDto(CreateCustomerDto createCustomerDto) {
        if (StringUtils.isEmpty(createCustomerDto.firstName())) {
            throw new CustomerValidationException("First name is required");
        }
        if (StringUtils.isEmpty(createCustomerDto.lastName())) {
            throw new CustomerValidationException("Last name is required");
        }
        if (StringUtils.isEmpty(createCustomerDto.address())) {
            throw new CustomerValidationException("Address is required");
        }
        if (StringUtils.isEmpty(createCustomerDto.phoneNumber())) {
            throw new CustomerValidationException("Phone number is required");
        }
        if (StringUtils.isEmpty(createCustomerDto.emailAddress())) {
            throw new CustomerValidationException("Email address is required");
        }
        if (StringUtils.isEmpty(createCustomerDto.password())) {
            throw new CustomerValidationException("Password is required");
        }
    }

    // ONLY ADMINS
    public List<CustomerDto> getAllCustomers() {
        return customerDtoMapper.mapToDtoList(customerRepository.getAllCustomers());
    }

    public CustomerDto getCustomerById(UUID customerId) {
        Customer customer = customerRepository.findCustomerById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Item not found with ID: " + customerId));
        return customerDtoMapper.mapToDto(customer);
    }
}
