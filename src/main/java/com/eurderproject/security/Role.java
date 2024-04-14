package com.eurderproject.security;

import java.util.List;

public enum Role {

    ADMIN(Permission.CREATE_ITEM, Permission.UPDATE_ITEM, Permission.VIEW_ALL_CUSTOMERS, Permission.VIEW_CUSTOMER_BY_ID),
    CUSTOMER(Permission.CREATE_ORDER);

    private final List<Permission> permissions;

    Role(Permission... permissions) {
        this.permissions = List.of(permissions);
    }

    public boolean containsPermission(Permission permission) {
        return permissions.contains(permission);
    }

}
