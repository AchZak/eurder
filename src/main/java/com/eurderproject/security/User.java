package com.eurderproject.security;

public class User {

    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean canHaveAccessTo(Permission permission) {
        return role.containsPermission(permission);
    }

    public boolean doesPasswordMatch(String password) {
        return this.password.equals(password);
    }

}
