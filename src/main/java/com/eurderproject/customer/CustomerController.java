package com.eurderproject.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        List<CustomerDto> customers = customerService.getAllCustomers();
        logger.info("Returning " + customers.size() + " customers");
        return customers;
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
    public CustomerDto addCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        logger.info("Received request to add a new customer.");
        CustomerDto addedCustomer = customerService.addCustomer(createCustomerDto);
        logger.info("Added customer: " + addedCustomer);
        return addedCustomer;
    }
}