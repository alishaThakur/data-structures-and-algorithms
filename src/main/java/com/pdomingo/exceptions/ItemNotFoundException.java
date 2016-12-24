package com.pdomingo.exceptions;

/**
 * Created by Pablo on 20/12/16.
 */
public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException() { super(); }
	public ItemNotFoundException(String message) { super(message); }
	public ItemNotFoundException(String message, Throwable cause) { super(message, cause); }
	public ItemNotFoundException(Throwable cause) { super(cause); }
}