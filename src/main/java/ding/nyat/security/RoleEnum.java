package ding.nyat.security;

public enum RoleEnum {
    ADMIN(1, "ADMIN"),
    AUTHOR(2, "AUTHOR");

    private int value;
    private String name;

    RoleEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return "ROLE_" + name;
    }
}
