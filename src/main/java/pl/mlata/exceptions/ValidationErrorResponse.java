package pl.mlata.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mateusz on 24.05.2017.
 */
public class ValidationErrorResponse extends ErrorResponse {
    private List<ValidationError> validationErrors;

    public ValidationErrorResponse(HttpStatus status, String message, String devMessage, ErrorCode errorCode, List<ValidationError> validationErrors) {
        super(status, message, devMessage, errorCode);
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
