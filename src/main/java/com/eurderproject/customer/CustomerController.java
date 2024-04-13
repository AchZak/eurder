package com.eurderproject.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        logger.info("Received request to get all customers.");
        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        logger.info("Returning " + customerDtos.size() + " customers");
        return customerDtos;
    }

    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable UUID customerId) {
        logger.info("Received request to get customer by ID: " + customerId);
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        logger.info("Returning customer: " + customerDto);
        return customerDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        logger.info("Received request to create a new customer.");
        CustomerDto createdCustomerDto = customerService.createCustomer(createCustomerDto);
        logger.info("Created customer: " + createdCustomerDto);
        return createdCustomerDto;
    }
}