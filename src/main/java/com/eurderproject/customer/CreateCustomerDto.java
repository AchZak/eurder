package com.eurderproject.customer;

public record CreateCustomerDto(String username,
                                String password,
                                String firstName,
                                String lastName,
                                String emailAddress,
                                String address,
                                String phoneNumber) {
}
