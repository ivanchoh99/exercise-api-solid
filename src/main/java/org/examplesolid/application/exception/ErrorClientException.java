package org.examplesolid.application.exception;

public class ErrorClientException extends RuntimeException {
    public ErrorClientException(String statusMessage) {
        super(statusMessage);
    }
}
