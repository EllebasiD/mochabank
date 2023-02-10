package com.anotherbank.mochabank.exception;

/**
* This abstract exception is the superclass of all application exception.
* It is a checked exception because it extends the Exception class.
* 
* @author Teachers of GLG203 Unit
*/

@SuppressWarnings("serial")
public abstract class ApplicationException extends Exception {

    protected ApplicationException() {
    }

    protected ApplicationException(final String message) {
        super(message);
    }
}