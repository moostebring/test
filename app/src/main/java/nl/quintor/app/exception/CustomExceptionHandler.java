package nl.quintor.app.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument (IllegalArgumentException ex, WebRequest webRequest){
        String message = "Something went wrong: " + ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationException(ConstraintViolationException ex, WebRequest webRequest){
        String message = "Entry is not valid: " + ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, webRequest);
    }

    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest webRequest){
        String message = "Error: " + ex.getMessage();
        return baseHandler(ex, message, HttpStatus.OK, webRequest);
    }

    @ExceptionHandler(value = UserNotEnabledException.class)
    public ResponseEntity<Object> handleUserNotEnabledException(UserNotEnabledException ex, WebRequest webRequest){
        String message = "Error: " + ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException ex, WebRequest webRequest){
        String message = "Error: " + ex.getMessage();
        return baseHandler(ex, message, HttpStatus.BAD_REQUEST, webRequest);
    }

    @Deprecated
    @ExceptionHandler(value = SortingArrayTooLargeException.class)
    public ResponseEntity<Object> handleSortingArrayTooLargeException(SortingArrayTooLargeException ex, WebRequest webRequest) {
        return baseHandler(ex, ex.getMessage(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @Deprecated
    @ExceptionHandler(value = SortingMethodNotValidException.class)
    public ResponseEntity<Object> handleSortingMethodNotValidException(SortingMethodNotValidException ex, WebRequest webRequest) {
        return baseHandler(ex, ex.getMessage(), HttpStatus.BAD_REQUEST, webRequest);
    }

    private ResponseEntity<Object> baseHandler(Exception ex, String message, HttpStatus status, WebRequest webRequest){
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, webRequest);
    }
}