package com.paypal.hiera.exceptions;

/**
 * User: pderoxas
 * Date: 2/25/13
 * Time: 11:43 AM
 * Implementation of Exception specific to ssh-related classes
 */
public class InvalidStateException extends ApplicationException {
    public InvalidStateException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

    public InvalidStateException(String message, ExceptionCode exceptionCode, Throwable cause) {
        super(message, exceptionCode, cause);
    }
}
