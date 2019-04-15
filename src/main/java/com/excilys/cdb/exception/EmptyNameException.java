package com.excilys.cdb.exception;

/**
 * Responsible for defining a custom exception when a computer is created with
 * an invalid name.
 *
 * @author excilys
 *
 */
public class EmptyNameException extends IllegalArgumentException {

	/**
	 * serialVersionUID long.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 * @param message String
	 */
	public EmptyNameException(String message) {
		super(message);
	}

}
