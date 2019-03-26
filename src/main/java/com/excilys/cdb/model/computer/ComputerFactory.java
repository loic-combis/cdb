package com.excilys.cdb.model.computer;

import java.util.Date;

import com.excilys.cdb.model.company.Company;

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
     * @throws EmptyNameException ene.
     */
    public Computer createWithName(String name) throws EmptyNameException {
        return new Computer(name);
    }

    /**
     * Creates a new Computer object with all its fields. All the fields except name
     * might be null.
     *
     * @param id              Long
     * @param name            String
     * @param introduction    Date
     * @param discontinuation Date
     * @param company         Company
     * @return Computer
     */
    public Computer createWithAll(Long id, String name, Date introduction, Date discontinuation, Company company) {
        Computer c = this.createWithName(name);
        if (c == null) {
            return c;
        }
        c.setId(id);
        c.setIntroductionDate(introduction);
        c.setDiscontinuationDate(discontinuation);
        c.setCompany(company);
        return c;
    }
}
