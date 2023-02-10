package com.externalbank.otherbank.exception;

/**
* This exception is thrown when an error occurs during transfer operation.
* 
* @author Isabelle Deligniere 
*/

@SuppressWarnings("serial")
public class TransferException extends ApplicationException {

    public TransferException() {
    }

    public TransferException(final String message) {
        super(message);
    }
}
