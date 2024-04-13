package com.eurderproject.security;


import com.eurderproject.customer.CustomerRepository;
import com.eurderproject.exception.UnauthorizedException;
import com.eurderproject.exception.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static java.lang.String.format;

//@Service
public class SecurityService {
/*
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = userRepository;
    }

    public void validateAuthorization(String authorization, Permission permission) {
        UsernamePassword usernamePassword = getUsernamePassword(authorization);
        User user = customerRepository.getUser(usernamePassword.getUsername());

        if (user == null) {
            logger.error(format("Unknown user %s", usernamePassword.getUsername()));
            throw new UnknownUserException();
        }
        if (!user.doesPasswordMatch(usernamePassword.getPassword())) {
            logger.error(format("Password does not match for user %s", usernamePassword.getUsername()));
            throw new WrongPasswordException();
        }
        if (!user.canHaveAccessTo(feature)) {
            logger.error(format("User %s does not have access to %s", usernamePassword.getUsername(), feature));
            throw new UnauthorizedException("");
        }

    }


    private UsernamePassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new UsernamePassword(username, password);
    }*/

}
