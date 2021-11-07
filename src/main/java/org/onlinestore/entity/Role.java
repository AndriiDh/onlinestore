package org.onlinestore.entity;

public enum Role {
    CUSTOMER("customer"), MANAGER("manager"), ADMIN("admin");

    private final String value;
    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
