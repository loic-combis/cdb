package com.excilys.cdb.model.computer;

import java.util.Date;

import com.excilys.cdb.model.company.Company;

/**
 * Class responsible for managing the Computer entity within the application.
 *
 * @author excilys
 *
 */
public class Computer {
    /**
     * id Long - Unique id of the computer in the database.
     */
    private Long id;

    /**
     * name String - Name of the computer.
     */
    private String name;

    /**
     * introductionDate Date - Date the computer was introduced.
     */
    private Date introductionDate;

    /**
     * discontinuationDate Date - Date the computer was discontinued.
     */
    private Date discontinuationDate;

    /**
     * company Company - Company manufacturing the computer.
     */
    private Company company;

    /**
     * Constructor.
     *
     * @param name String - Name to be set.
     * @throws EmptyNameException ene.
     */
    public Computer(String name) throws EmptyNameException {
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
     * @throws EmptyNameException ene
     */
    public void setName(String name) throws EmptyNameException {
        if (name == null || "".equals(name)) {
            throw new EmptyNameException();
        }
        this.name = name;
    }

    /**
     * Getter.
     *
     * {@link Computer#introductionDate}
     *
     * @return Date
     */
    public Date getIntroductionDate() {
        return introductionDate;
    }

    /**
     * Setter.
     *
     * {@link Computer#introductionDate}
     *
     * @param introduction Date - Introduction date of the computer.
     */
    public void setIntroductionDate(Date introduction) {
        if (getDiscontinuationDate() == null || introduction == null
                || (getDiscontinuationDate().getTime() - introduction.getTime()) > 0) {
            introductionDate = introduction;
        }
    }

    /**
     * Getter.
     *
     * {@link Computer#discontinuationDate}
     *
     * @return Date
     */
    public Date getDiscontinuationDate() {
        return discontinuationDate;
    }

    /**
     * Setter.
     *
     * {@link Computer#discontinuationDate}
     *
     * @param discontinued Date - Discontinuation date of the computer.
     */
    public void setDiscontinuationDate(Date discontinued) {
        if (getIntroductionDate() == null || discontinued == null
                || (getIntroductionDate().getTime() - discontinued.getTime()) < 0) {
            discontinuationDate = discontinued;
        }
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
                .append(introductionDate != null ? introductionDate.toString() : "null").append("\t|\t")
                .append(discontinuationDate != null ? discontinuationDate.toString() : "null").append("\t|\t")
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