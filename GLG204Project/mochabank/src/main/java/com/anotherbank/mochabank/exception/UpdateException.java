package com.anotherbank.mochabank.exception;

/**
* This exception is thrown when an object cannot be updated.
* 
* @author Teachers of GLG203 Unit
*/

@SuppressWarnings("serial")
public final class UpdateException extends ApplicationException {

    public UpdateException(final String message) {
        super(message);
    }
}
