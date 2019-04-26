package com.excilys.cdb.core.computer;

import java.time.LocalDate;

import com.excilys.cdb.core.company.Company;

/**
 * Singleton Responsible for instantiating Computer objects within the
 * application.
 *
 * @author excilys
 *
 */
public class ComputerFactory {

    /**
     * instance ComputerFactory - Unique instance of ComputerFactory.
     */
    private static ComputerFactory instance;

    /**
     * Constructor Prevent from being instantiated outside the class.
     */
    private ComputerFactory() {
    }

    /**
     * Creates or return the only instance (Lazy instantiation).
     * {@link ComputerFactory#instance}
     *
     * @return CompputerFactory
     */
    public static ComputerFactory getInstance() {
        if (instance == null) {
            instance = new ComputerFactory();
        }
        return instance;
    }

    /**
     * Creates a new Computer object with only its name.
     *
     * @param name String - Name to be set.
     * @return Computer.
     */
    public Computer createWithName(String name) {
        return new Computer(name);
    }

    /**
     * Creates a new Computer object with all its fields. All the fields except name
     * might be null.
     *
     * @param id              Long
     * @param name            String
     * @param introduction    LocalDate
     * @param discontinuation LocalDate
     * @param company         Company
     * @return Computer
     */
    public Computer createWithAll(Long id, String name, LocalDate introduction, LocalDate discontinuation,
            Company company) {
        ComputerBuilder computerBuilder = new ComputerBuilder(name);
        return computerBuilder.setId(id).setIntroduction(introduction).setDiscontinuation(discontinuation)
                .setCompany(company).get();
    }
}
