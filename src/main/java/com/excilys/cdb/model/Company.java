package com.excilys.cdb.model;

/**
 * Class managing the company entity within the application.
 *
 * @author excilys
 *
 */
public class Company {

    /**
     * id long - The unique id of a company in the database.
     */
    private long id;

    /**
     * name String - The name of the company.
     */
    private String name;

    /**
     * Constructor.
     *
     * @param id long - id to be set.
     */
    public Company(long id) {
        this.id = id;
    }

    /**
     * Constructor.
     *
     * @param name String - name to be set.
     */
    public Company(String name) {
        this.setName(name);
    }

    /**
     * Constructor.
     *
     * @param id   long - id to be set.
     * @param name - name to be set.
     */
    public Company(long id, String name) {
        this.id = id;
        this.setName(name);
    }

    /**
     * Getter.
     *
     * {@link Company#name}
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * {@link Company#name}
     *
     * @param name String - The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter.
     *
     * {@link Company#id}
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Describes the company object as a String.
     * @return String
     */
    @Override
    public String toString() {
        return id + "\t|\t" + name;
    }
}
