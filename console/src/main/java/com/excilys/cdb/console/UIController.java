package com.excilys.cdb.console;

/**
 * Interface Responsible for defining the contract a UIController must respect
 * within the application.
 *
 * @author excilys
 */
public interface UIController {

    /**
     * Starts the client.
     */
    void start();

    /**
     * Stops the client.
     */
    void stop();

}
