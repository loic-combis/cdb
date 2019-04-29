package com.excilys.cdb.persistence.exception;

public class UserAlreadyExistsException extends Exception {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = -416695200185130795L;

    public UserAlreadyExistsException() {
        super("User with the same login already exists in the db.");
    }
}
