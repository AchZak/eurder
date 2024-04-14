package com.eurderproject.security;

import com.eurderproject.exception.UnauthorizedException;
import com.eurderproject.exception.UserNotFoundException;
import com.eurderproject.exception.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;


@Service
public class SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String authorizationHeader) {
        UserCredentials credentials = extractCredentials(authorizationHeader);
        User user = findUserByUsername(credentials.username());
        validatePassword(credentials.password(), user.getPassword());
        logger.info("User authenticated: {}", user.getUsername());
        return user;
    }

    public void authorize(User user, Permission permission) {
        if (!user.hasPermission(permission)) {
            String errorMessage = String.format("User '%s' is not authorized to '%s' operation", user.getUsername(), permission);
            logger.error(errorMessage);
            throw new UnauthorizedException(errorMessage);
        }
        logger.info("User authorized for '{}' operation: {}", permission, user.getUsername());
    }

    private UserCredentials extractCredentials(String authorizationHeader) {
        String decodedUserCredentials = new String(Base64.getDecoder().decode(authorizationHeader.substring("Basic ".length())));
        String username = decodedUserCredentials.substring(0, decodedUserCredentials.indexOf(":"));
        String password = decodedUserCredentials.substring(decodedUserCredentials.indexOf(":") + 1);
        return new UserCredentials(username, password);
    }

    private User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for username: " + username);
                    return new UserNotFoundException("User not found");
                });
    }

    private void validatePassword(String providedPassword, String actualPassword) {
        if (!providedPassword.equals(actualPassword)) {
            logger.error("Incorrect password for user");
            throw new WrongPasswordException("Incorrect password");
        }
        logger.info("Password validated successfully");
    }
}

