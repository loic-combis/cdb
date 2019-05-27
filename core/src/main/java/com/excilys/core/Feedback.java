package com.excilys.core;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Feedback {

    private String status;
    private String message;

    public Feedback() {

    }

    /**
     * Constructor.
     *
     * @param status  String
     * @param message String
     */
    public Feedback(String status, String message) {
        setStatus(status);
        setMessage(message);
    }

    /**
     * Getter.
     *
     * @return String
     */
    @JsonGetter("message")
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
    @JsonGetter("status")
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
