package com.excilys.cdb.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class managing the company entity within the application.
 *
 * @author excilys
 */
@Entity
@Table(name = "company")
public class Company {

    /**
     * id Long - The unique id of a company in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * name String - The name of the company.
     */
    @Column(name = "name")
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
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * {@link Company#id}
     *
     * @param id Long - The id to be set.
     */
    public void setId(Long id) {
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
