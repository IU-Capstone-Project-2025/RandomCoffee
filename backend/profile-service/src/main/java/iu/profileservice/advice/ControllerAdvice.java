package iu.profileservice.advice;

import iu.profileservice.exception.ResourceNotFoundException;
import iu.profileservice.exception.ValidationException;
import iu.profileservice.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            DataIntegrityViolationException.class,
            InvalidDataAccessApiUsageException.class,
            ValidationException.class
    })
    public ResponseEntity<ErrorResponse> handleDataException(RuntimeException exception,
                                                             HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse()
                        .timestamp(OffsetDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(exception.getMessage())
                        .path(request.getRequestURI())
                );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                         HttpServletRequest request) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse()
                        .timestamp(OffsetDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(exception.getMessage())
                        .path(request.getRequestURI())
                );
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception,
                                                                           HttpServletRequest request) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorResponse()
                        .timestamp(OffsetDateTime.now())
                        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .error(exception.getMessage())
                        .path(request.getRequestURI())
                );
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception,
                                                                HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorResponse()
                        .timestamp(OffsetDateTime.now())
                        .status(HttpStatus.I_AM_A_TEAPOT.value())
                        .error(exception.getMessage())
                        .path(request.getRequestURI())
                );
    }
}
