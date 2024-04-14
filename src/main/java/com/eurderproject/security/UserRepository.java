package com.eurderproject.security;

import com.eurderproject.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    private Map<UUID, User> users;
    private Admin admin;
    private Customer customer;

    public UserRepository(Map<UUID, User> users) {
        this.users = users;
        admin = new Admin("admin", "admin");
        users.put(admin.getUserId(), admin);
        customer = new Customer("mariokart",
                "trophy",
                "Mario",
                "Kart",
                "mario.kart@example.com",
                "Mushroom",
                "044444");
        users.put(customer.getUserId(), customer);
    }

    public void save(User user) {
        users.put(user.getUserId(), user);

    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public Optional<User> findUserById(UUID userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<User> findUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }
}
