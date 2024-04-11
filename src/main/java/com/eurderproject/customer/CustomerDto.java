package com.eurderproject.customer;

import java.util.UUID;

public record CustomerDto(UUID customerId, String firstName, String lastName, String emailAddress, String address, String phoneNumber) {
}
