package com.excilys.cdb.core.computer;

import java.time.LocalDate;

import com.excilys.cdb.core.company.Company;

public class ComputerBuilder {

    private Computer computer;

    /**
     * Constructor.
     *
     * @param name String
     */
    public ComputerBuilder(String name) {
        computer = new Computer(name);
    }

    /**
     * Setter.
     *
     * @param name String
     * @return ComputerBuilder
     */
    public ComputerBuilder setName(String name) {
        computer.setName(name);
        return this;
    }

    /**
     * Setter.
     *
     * @param id Long
     * @return ComputerBuilder
     */
    public ComputerBuilder setId(Long id) {
        computer.setId(id);
        return this;
    }

    /**
     * Setter.
     *
     * @param date LocalDate
     * @return ComputerBuilder
     */
    public ComputerBuilder setIntroduction(LocalDate date) {
        computer.setIntroduced(date);
        return this;
    }

    /**
     * Setter.
     *
     * @param date LocalDate
     * @return ComputerBuilder
     */
    public ComputerBuilder setDiscontinuation(LocalDate date) {
        computer.setDiscontinued(date);
        return this;
    }

    /**
     * Setter.
     *
     * @param company Company
     * @return ComputerBuilder
     */
    public ComputerBuilder setCompany(Company company) {
        computer.setCompany(company);
        return this;
    }

    /**
     * Getter.
     *
     * @return Computer
     */
    public Computer get() {
        return computer;
    }
}
