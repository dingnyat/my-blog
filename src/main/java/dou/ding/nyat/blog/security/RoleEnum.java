package dou.ding.nyat.blog.security;

public enum RoleEnum {
    ADMIN(1, "ADMIN", "ROLE_ADMIN"),
    AUTHOR(1, "AUTHOR", "ROLE_AUTHOR");

    private int value;
    private String name;
    private String fullName;

    RoleEnum(int value, String name, String fullName){
        this.value = value;
        this.name = name;
        this.fullName = fullName;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }
}
