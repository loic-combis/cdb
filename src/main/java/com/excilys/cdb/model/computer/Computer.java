package com.excilys.cdb.model.computer;

import java.time.LocalDate;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
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
     * introductionDate LocalDate - Date the computer was introduced.
     */
    private LocalDate introductionDate;

    /**
     * discontinuationDate LocalDate - Date the computer was discontinued.
     */
    private LocalDate discontinuationDate;

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
        if (name == null) {
            throw new EmptyNameException("Attempted to set name with a null value.");
        }
        String trimmedName = name.trim();
        if ("".equals(trimmedName)) {
            throw new EmptyNameException("Attempted to set name with an empty value.");
        }
        this.name = trimmedName;
    }

    /**
     * Getter.
     *
     * {@link Computer#introductionDate}
     *
     * @return LocalDate
     */
    public LocalDate getIntroductionDate() {
        return introductionDate;
    }

    /**
     * Setter.
     *
     * {@link Computer#introductionDate}
     *
     * @param introduction Date - Introduction date of the computer.
     * @throws UnconsistentDatesException ude
     */
    public void setIntroductionDate(LocalDate introduction) throws UnconsistentDatesException {
        if (getDiscontinuationDate() == null || introduction == null
                || getDiscontinuationDate().isAfter(introduction)) {
            introductionDate = introduction;
        } else {
            throw new UnconsistentDatesException(
                    "Attempted to set introduction date with more recent date than discontinuation date.");
        }
    }

    /**
     * Getter.
     *
     * {@link Computer#discontinuationDate}
     *
     * @return Date
     */
    public LocalDate getDiscontinuationDate() {
        return discontinuationDate;
    }

    /**
     * Setter.
     *
     * {@link Computer#discontinuationDate}
     *
     * @param discontinued LocalDate - Discontinuation date of the computer.
     * @throws UnconsistentDatesException ude
     */
    public void setDiscontinuationDate(LocalDate discontinued) throws UnconsistentDatesException {
        if (getIntroductionDate() == null || discontinued == null || discontinued.isAfter(getIntroductionDate())) {
            discontinuationDate = discontinued;
        } else {
            throw new UnconsistentDatesException(
                    "Attempted to set a discontinuation date older than the introduction date.");
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