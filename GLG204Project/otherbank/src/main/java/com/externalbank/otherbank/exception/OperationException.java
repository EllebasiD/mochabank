package com.externalbank.otherbank.exception;

/**
* This exception is thrown when an error occurs during an operation.
* 
* @author Isabelle Deligniere 
*/

@SuppressWarnings("serial")
public class OperationException extends ApplicationException {

    public OperationException() {
    }

    public OperationException(final String message) {
        super(message);
    }
}
