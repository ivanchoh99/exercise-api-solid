package org.examplesolid.application.exception.custom;

public class ErrorClientException extends RuntimeException {
    public ErrorClientException(String statusMessage) {
        super(statusMessage);
    }
}
