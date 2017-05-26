package pl.mlata.global;

public enum UserRoles {
    Admin("ROLE_ADMIN"),
    User("ROLE_USER");

    private final String value;
    UserRoles(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
