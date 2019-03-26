package com.excilys.cdb.model;

public class Feedback {

    private String status;
    private String message;


    public Feedback(String status, String message) {
        setStatus(status);
        setMessage(message);
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter.
     *
     * @param message String
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter.
     *
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
