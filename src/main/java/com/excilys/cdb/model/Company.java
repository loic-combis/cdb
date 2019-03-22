package com.excilys.cdb.model;

/**
 * Class managing the company entity within the application.
 *
 * @author excilys
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
     * Setter.
     *
     * {@link Company#id}
     *
     * @param id long - The id to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Describes the company object as a String.
     *
     * @return String
     */
    @Override
    public String toString() {
        return id + "\t|\t" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Company)) {
            return false;
        }
        return getName().equals(((Company) o).getName());
    }

    @Override
    public int hashCode() {
        int prime = 17;
        return 31 * prime + getName().hashCode();
    }

}
