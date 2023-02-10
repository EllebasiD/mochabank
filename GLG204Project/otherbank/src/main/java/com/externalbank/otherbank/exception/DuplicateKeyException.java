package com.externalbank.otherbank.exception;

/**
 * This exception is throw when an object already exists in the persistent layer
 * and we want to add another one with the same identifier.
 * 
 * @author Teachers of GLG203 Unit
 */

@SuppressWarnings("serial")
public class DuplicateKeyException extends CreateException {
}
