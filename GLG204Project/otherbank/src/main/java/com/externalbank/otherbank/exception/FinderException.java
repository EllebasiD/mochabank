package com.externalbank.otherbank.exception;

/**
* This exception is thrown when an object cannot be found.
* 
* @author Teachers of GLG203 Unit 
*/

@SuppressWarnings("serial")
public class FinderException extends ApplicationException {

    public FinderException() {
    }

    public FinderException(final String message) {
        super(message);
    }
}
