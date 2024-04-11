package com.eurderproject.customer;


import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerDtoMapper {

    public CustomerDto mapToDto(Customer customer) {
        return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(), customer.getAddress(), customer.getPhoneNumber());
    }

    public List<CustomerDto> mapToDtoList(Collection<Customer> customerList) {
        return customerList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
