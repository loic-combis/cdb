package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;

/**
 * Interface responsible for defining the contract a DAO managing Computer
 * objects must respect.
 *
 * @author excilys
 *
 */
public interface ComputerDAO extends DAO<Computer> {

    /**
     * Deletes the computer with the specified id.
     *
     * @param id long
     * @return Boolean - True if the deletion is successful, false otherwise.
     */
    Boolean delete(long id);

    /**
     * Updates the specified computer.
     *
     * @param c Computer
     * @return Boolean - True if the update is successful, false otherwise.
     */
    Boolean update(Computer c);

    /**
     * Saves the specified computer.
     *
     * @param c Computer
     * @return Computer - Newly created computer with its unique id in the database.
     */
    Computer create(Computer c);

}
