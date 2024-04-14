package com.eurderproject.security;

import java.util.UUID;

public abstract class User {

    private UUID userId;
    private String username;
    private String password;
    private Role role;

    public User() {
    }
    public User(String username, String password, Role role) {
        userId = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean hasPermission(Permission permission) {
        return role.containsPermission(permission);
    }

}
