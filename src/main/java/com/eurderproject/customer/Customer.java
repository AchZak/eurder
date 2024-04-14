package com.eurderproject.customer;

import com.eurderproject.security.Role;
import com.eurderproject.security.User;

import java.util.Objects;

public class Customer extends User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String address;
    private String phoneNumber;

    public Customer(String firstName, String lastName, String emailAddress, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String username, String password, String firstName, String lastName, String emailAddress, String address, String phoneNumber) {
        super(username, password, Role.CUSTOMER);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getUserId(), customer.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + getUserId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
