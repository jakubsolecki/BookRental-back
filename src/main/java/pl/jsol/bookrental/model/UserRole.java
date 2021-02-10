package pl.jsol.bookrental.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    USER, ADMIN;

    @JsonCreator
    public static UserRole toEnum(String userRole) {
        return UserRole.valueOf(userRole.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.toString();
    }
}
