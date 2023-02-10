package com.anotherbank.mochabank.exception;

/**
* This exception is thrown when some checking validation error is found.
* 
* @author Teachers of GLG203 Unit  
*/

@SuppressWarnings("serial")
public final class CheckException extends ApplicationException {

    public CheckException(final String message) {
        super(message);
    }
}

