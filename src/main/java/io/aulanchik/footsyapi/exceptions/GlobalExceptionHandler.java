package io.aulanchik.footsyapi.exceptions;

import io.aulanchik.footsyapi.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        err -> ((FieldError) err).getField(),
                        err -> Optional.ofNullable(err.getDefaultMessage()).orElse("Invalid value")
                ));
        logger.warn("Validation error(s): {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        logger.warn("User with given credentials already exists: {} ", exception.getMessage());
        ApiResponse response = new ApiResponse(exception.getMessage(), HttpStatus.NOT_FOUND, new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException exception) {
        logger.warn("User with given credentials wasn't found: {}", exception.getMessage());
        ApiResponse response = new ApiResponse(exception.getMessage(), HttpStatus.NOT_FOUND, new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse> handleUserExistException(UserExistException exception) {
        logger.error("Unexpected error: {}", exception.getMessage());
        ApiResponse response = new ApiResponse(exception.getMessage(), HttpStatus.CONFLICT, new Date());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        logger.error("HTTP method is not supported: {}", exception.getMethod());
        ApiResponse response = new ApiResponse(exception.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, new Date());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
}

