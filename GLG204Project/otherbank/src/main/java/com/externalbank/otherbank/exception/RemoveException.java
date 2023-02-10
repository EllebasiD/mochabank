package com.externalbank.otherbank.exception;

/**
* This exception is thrown when an object cannot be deleted.
* 
* @author Teachers of GLG203 Unit
*/

@SuppressWarnings("serial")
public final class RemoveException extends ApplicationException {

    public RemoveException(final String message) {
        super(message);
    }
}
