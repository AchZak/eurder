package com.eurderproject.customer;

import com.eurderproject.exception.*;
import com.eurderproject.security.Permission;
import com.eurderproject.security.SecurityService;
import com.eurderproject.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;
    private final SecurityService securityService;
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
        this.securityService = securityService;
    }

    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        validateCreateCustomerDto(createCustomerDto);
        checkUniqueUsername(createCustomerDto.username());
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

    private void checkUniqueUsername(String username) {
        if (customerRepository.findCustomerByUsername(username).isPresent()) {
            logger.info("Username must be unique");
            throw new DuplicateUsernameException("Username must be unique");
        }
    }

    public List<CustomerDto> getAllCustomers(String authorizationHeader) {
        User authenticatedUser = securityService.authenticate(authorizationHeader);
        securityService.authorize(authenticatedUser, Permission.VIEW_ALL_CUSTOMERS);
        return customerDtoMapper.mapToDtoList(customerRepository.getAllCustomers());
    }

    public CustomerDto getCustomerById(String authorizationHeader, UUID customerId) {
        User authenticatedUser = securityService.authenticate(authorizationHeader);
        securityService.authorize(authenticatedUser, Permission.VIEW_CUSTOMER_BY_ID);
        Customer customer = customerRepository.findCustomerById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Item not found with ID: " + customerId));
        return customerDtoMapper.mapToDto(customer);
    }

}
