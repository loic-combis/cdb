package com.excilys.cdb.model.computer;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.cdb.model.company.Company;

/**
 * Class responsible for managing the Computer entity within the application.
 *
 * @author excilys
 *
 */
@Entity
@Table(name = "computer")
public class Computer {
    /**
     * id Long - Unique id of the computer in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * name String - Name of the computer.
     */
    @Column(name = "name")
    private String name;

    /**
     * introduced LocalDate - Date the computer was introduced.
     */
    @Column(name = "introduced")
    private LocalDate introduced;

    /**
     * discontinued LocalDate - Date the computer was discontinued.
     */
    @Column(name = "discontinued")
    private LocalDate discontinued;

    /**
     * company Company - Company manufacturing the computer.
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * Constructor.
     */
    public Computer() {

    }

    /**
     * Constructor.
     *
     * @param name String - Name to be set.
     */
    public Computer(String name) {
        this.setName(name);
    }

    /**
     * Getter.
     *
     * {@link Computer#name}
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * {@link Computer#name}
     *
     * @param name String - Name to be set.
     */
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * Getter.
     *
     * {@link Computer#introduced}
     *
     * @return LocalDate
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * Setter.
     *
     * {@link Computer#introduced}
     *
     * @param introduced Date - Introduction date of the computer.
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    /**
     * Getter.
     *
     * {@link Computer#discontinued}
     *
     * @return Date
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * Setter.
     *
     * {@link Computer#discontinuationDate}
     *
     * @param discontinued LocalDate - Discontinuation date of the computer.
     */
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * Getter.
     *
     * {@link Computer#company}
     *
     * @return Company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Setter.
     *
     * {@link Computer#company}
     *
     * @param company Company - Company to be set.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Getter.
     *
     * {@link Computer#id}
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * {@link Computer#id}
     *
     * @param id Long - The id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Describe the computer object in a string.
     *
     * @return String
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(100);
        sb.append(id).append("\t|\t").append(name).append("\t|\t")
                .append(introduced != null ? introduced.toString() : "null").append("\t|\t")
                .append(discontinued != null ? discontinued.toString() : "null").append("\t|\t")
                .append(company != null ? company.getId() + " : " + company.getName() : "null");
        return sb.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Computer)) {
            return false;
        }
        return getName().equals(((Computer) o).getName());
    }

    @Override
    public int hashCode() {
        int prime = 17;
        return 31 * prime + getName().hashCode();
    }
}