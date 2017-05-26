package pl.mlata.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GENERIC(0), RESOURCE_NOT_FOUND(1),
    AUTHENTICATION(10), TOKEN_EXPIRED(11), BAD_CREDENTIALS(12),
    VALIDATION(20);

    private final int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
