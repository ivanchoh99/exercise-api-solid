package org.examplesolid.domain.exception;

import org.examplesolid.domain.exception.custom.ErrorClientException;
import org.examplesolid.domain.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.NameNotFoundException;

@ControllerAdvice
public class GloabalHandle {

    @ExceptionHandler(ErrorClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(final ErrorClientException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("Can't get data from client: " + e.getMessage()));
    }

    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNameNotFoundException(final NameNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Can't find the name of the character"));
    }
}
