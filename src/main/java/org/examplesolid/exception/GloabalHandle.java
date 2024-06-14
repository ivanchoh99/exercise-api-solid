package org.examplesolid.exception;

import org.examplesolid.exception.custom.ErrorClientException;
import org.examplesolid.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloabalHandle {

    @ExceptionHandler(ErrorClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(final ErrorClientException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("Can't get data from client"));
    }
}
