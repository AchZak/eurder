package com.eurderproject.security;

import java.util.UUID;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }
}
