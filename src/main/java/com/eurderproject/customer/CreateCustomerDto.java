package com.eurderproject.customer;

public record CreateCustomerDto(String firstName,
                                String lastName,
                                String emailAddress,
                                String address,
                                String phoneNumber,
                                String username,
                                String password) {
}
