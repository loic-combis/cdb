package com.excilys.cdb.ui;

/**
 * Interface Responsible for defining the contract a UIController must respect
 * within the application.
 * 
 * @author excilys
 *
 */
public interface UIController {

	/**
	 * Starts the client.
	 */
	public abstract void start();

	/**
	 * Stops the client.
	 */
	public abstract void stop();

}
