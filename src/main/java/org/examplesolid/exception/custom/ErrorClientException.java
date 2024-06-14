package org.examplesolid.exception.custom;

public class ErrorClientException extends RuntimeException{
    public ErrorClientException(String message) {
        super(message);
    }
}
