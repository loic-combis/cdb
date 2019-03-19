package com.excilys.cdb.model;

import java.util.Date;

/**
 * Class responsible for managing the Computer entity within the application.
 *
 * @author excilys
 *
 */
public class Computer {
    /**
     * id long - Unique id of the computer in the database.
     */
    private long id;

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
     * Constructor.
     *
     * @param id              long - Id to be set.
     * @param name            String - Name to be set.
     * @param introduction    Date - Introduction date to be set.
     * @param discontinuation Date - Discontinuation date to be set.
     * @param company         Company - Company to be set.
     * @throws EmptyNameException ene.
     */
    public Computer(long id, String name, Date introduction, Date discontinuation, Company company)
            throws EmptyNameException {
        this.id = id;
        this.setName(name);
        this.setIntroductionDate(introduction);
        this.setDiscontinuationDate(discontinuation);
        this.setCompany(company);
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
        if (name == null || name.equals("")) {
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
        if (this.getDiscontinuationDate() == null || introduction == null
                || (this.getDiscontinuationDate().getTime() - introduction.getTime()) > 0) {
            this.introductionDate = introduction;
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
        if (this.getIntroductionDate() == null || discontinued == null
                || (this.getIntroductionDate().getTime() - discontinued.getTime()) < 0) {
            this.discontinuationDate = discontinued;
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
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * {@link Computer#id}
     *
     * @param id long - The id to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Describe the computer object in a string.
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
}

/**
 * Responsible for defining a custom exception when a computer is created with an invalid name.
 *
 * @author excilys
 *
 */
class EmptyNameException extends IllegalArgumentException {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    EmptyNameException() {
        super();
    }

}
