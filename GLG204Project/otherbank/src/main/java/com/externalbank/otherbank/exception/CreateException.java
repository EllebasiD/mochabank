package com.externalbank.otherbank.exception;

/**
* This exception is thrown when an object cannot be created.
* 
* @author Teachers of GLG203 Unit 
*/

@SuppressWarnings("serial")
public class CreateException extends ApplicationException {

    public CreateException() {
    }

    public CreateException(final String message) {
        super(message);
    }
}