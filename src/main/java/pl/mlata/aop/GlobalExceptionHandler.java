package pl.mlata.aop;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mlata.exceptions.ErrorCode;
import pl.mlata.exceptions.ErrorResponse;
import pl.mlata.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), "", ErrorCode.RESOURCE_NOT_FOUND);
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        return responseEntity;
    }


}
