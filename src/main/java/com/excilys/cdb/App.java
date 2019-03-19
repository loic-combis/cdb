package com.excilys.cdb;

import com.excilys.cdb.ui.cli.CLIController;

/**
 * Entry point meant to instantiate and start the application.
 *
 */
public class App {

    /**
     * Main method.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        CLIController cli = new CLIController();
        cli.start();
    }
}
