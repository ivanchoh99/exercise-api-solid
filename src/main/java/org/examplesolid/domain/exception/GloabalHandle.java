package org.examplesolid.domain.exception;

import org.examplesolid.domain.exception.custom.ErrorClientException;
import org.examplesolid.infrastructure.rest.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloabalHandle {

    @ExceptionHandler(ErrorClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(final ErrorClientException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("Can't get data from client: " + e.getMessage()));
    }
}
