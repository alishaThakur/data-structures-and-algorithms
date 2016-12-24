package com.pdomingo.exceptions;

/**
 * Created by Pablo on 13/11/16.
 */
public class IndexOutOfBoundsException extends RuntimeException {
	public IndexOutOfBoundsException() { super(); }
	public IndexOutOfBoundsException(String message) { super(message); }
	public IndexOutOfBoundsException(String message, Throwable cause) { super(message, cause); }
	public IndexOutOfBoundsException(Throwable cause) { super(cause); }
}
