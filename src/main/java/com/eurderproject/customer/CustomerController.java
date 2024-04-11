package com.eurderproject.customer;


import com.eurderproject.exception.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CustomerDto> getAllCustomers(){
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
}