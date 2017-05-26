package pl.mlata.exceptions;

import org.springframework.validation.BindingResult;

public class ValidationErrorsException extends RuntimeException {
    private BindingResult result;

    public ValidationErrorsException(BindingResult result) {
        this.result = result;
    }

    public BindingResult getResult() {
        return result;
    }

    public void setResult(BindingResult result) {
        this.result = result;
    }
}
